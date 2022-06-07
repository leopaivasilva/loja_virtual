package jdev.mentoria.lojavirtual.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.model.Produto;
import jdev.mentoria.lojavirtual.repository.ProdutoRepository;
import jdev.mentoria.lojavirtual.service.ServiceSendEmail;

@Controller
@RestController
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/salvarProduto")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoriaJava, MessagingException, IOException { /*Recebe o JSON e converte para Objeto*/
		
		if (produto.getTipoUnidade() == null || produto.getTipoUnidade().trim().isEmpty()) {
			throw new ExceptionMentoriaJava("Tipo de unidade deve ser informada");
			
		}
		
		if (produto.getNome().length() < 10) {
			throw new ExceptionMentoriaJava("Nome do Produto deve ter mais de 10 letras");
			
		}		
		
		if (produto.getEmpresa() == null || produto.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("Empresa responsável deve ser informada");
			
		}
		
		if (produto.getId() == null) {
			
			List<Produto> produtos = produtoRepository.buscarProdutoNome(produto.getNome().toUpperCase(), produto.getEmpresa().getId());
			
			if (!produtos.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe cadastrado produto com a descrição: " + produto.getNome());
			}
		}		

		
		if (produto.getCategoriaProduto() == null || produto.getCategoriaProduto().getId() <= 0) {
			throw new ExceptionMentoriaJava("Categoria do Produto deve ser informada");
		}
		
		if (produto.getMarcaProduto() == null || produto.getMarcaProduto().getId() <= 0) {
			throw new ExceptionMentoriaJava("Marca do Produto deve ser informada");
		}
		
		if (produto.getQtdeEstoque() < 1) {
			throw new ExceptionMentoriaJava("Quantidade de estoque deve ter no mínimo 1 produto");
		}	
		
		if (produto.getImagens() == null || produto.getImagens().isEmpty() || produto.getImagens().size() == 0) {
			throw new ExceptionMentoriaJava("Deve ser informdo imagens para o produto");
		}
		
		if (produto.getImagens().size() < 3) {
			throw new ExceptionMentoriaJava("Deve ser informdo pelo menos 3 imagens para o produto");
		}
		
		if (produto.getImagens().size() > 6) {
			throw new ExceptionMentoriaJava("Deve ser informdo no máximo 6 imagens para o produto");
		}
		
		if (produto.getId() == null) {
		
			for (int x = 0; x < produto.getImagens().size(); x++) {
				produto.getImagens().get(x).setProduto(produto);
				produto.getImagens().get(x).setEmpresa(produto.getEmpresa());
				
				String base64Image = "";
								
				if (produto.getImagens().get(x).getImagemOriginal().contains("data:image")) {
					base64Image = produto.getImagens().get(x).getImagemOriginal().split(",")[1];	
				}else {
					base64Image = produto.getImagens().get(x).getImagemOriginal();
				}
				
				byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
				
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
				
				if (bufferedImage != null) {
					
					int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
					int largura = Integer.parseInt("800");
					int altura = Integer.parseInt("600");
					
					BufferedImage residImage = new BufferedImage(largura, altura, type);
					Graphics2D g = residImage.createGraphics();
					g.drawImage(bufferedImage, 0, 0, largura, altura, null);
					g.dispose();
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(residImage, "png", baos);
					
					String miniImgBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
					
					produto.getImagens().get(x).setImagemMinitura(miniImgBase64);
					
					bufferedImage.flush();
					residImage.flush();
					baos.flush();
					baos.close();
				
				}
				
			}
		}
		
		
		Produto produtoSalvo = produtoRepository.save(produto);
		
		
		if (produto.getAlertaQtdeEstoque() && produto.getQtdeEstoque() <= 1 ) {
			
			StringBuilder html = new StringBuilder();
			html.append("<h2>").append("Produto: " + produto.getNome()).append(" com estoque baixo: " + produto.getQtdeEstoque());
			html.append("<p> Id Produto: ").append(produto.getId()).append("</p>");
			
			if (produto.getEmpresa().getEmail() != null) {
				serviceSendEmail.enviarEmailHtml("Produto sem estoque", html.toString(), produto.getEmpresa().getEmail());				
			} 
		}		
		
		
		return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/deleteProduto")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<String> deleteProduto(@RequestBody Produto produto) { /*Recebe o JSON e converte para Objeto*/
		
		produtoRepository.deleteById(produto.getId());
		
		return new ResponseEntity<String>("Produto Removido", HttpStatus.OK);
	}	
	
	@ResponseBody /*POde dar um retorno da API*/
	@DeleteMapping(value = "**/deleteProdutoPorId/{id}")
	public ResponseEntity<String> deleteProdutoPorId(@PathVariable("id")  Long id ) {
		
		produtoRepository.deleteById(id);
		
		return new ResponseEntity<String>("Produto Removido", HttpStatus.OK);
	}	
	
	@ResponseBody /*POde dar um retorno da API*/
	@GetMapping(value = "**/consultarProduto/{id}")
	public ResponseEntity<Produto> consultarProduto(@PathVariable("id")  Long id ) throws ExceptionMentoriaJava {
		
		Produto produto = produtoRepository.findById(id).orElse(null);
		
		if (produto  == null) {
			throw new ExceptionMentoriaJava("Código informando "+ id +" não encontrado no sistema");
		}
		
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}	
	
	
	@ResponseBody /*POde dar um retorno da API*/
	@GetMapping(value = "**/consultarProdutoNome/{nome}")
	public ResponseEntity<List<Produto>> consultarProduto(@PathVariable("nome")  String nome ) {
		
		List<Produto> produto = produtoRepository.buscarProdutoNome(nome.toUpperCase());
		
		return new ResponseEntity<List<Produto>>(produto, HttpStatus.OK);
	}	
	
	
		

}
