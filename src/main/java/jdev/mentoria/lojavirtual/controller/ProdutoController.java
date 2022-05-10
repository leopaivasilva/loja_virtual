package jdev.mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

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

@Controller
@RestController
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/salvarProduto")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoriaJava { /*Recebe o JSON e converte para Objeto*/
		
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
		
		if (produto.getNotaItemProduto() == null || produto.getNotaItemProduto().getId() <= 0) {
			throw new ExceptionMentoriaJava("Nota do Item do Produto deve ser informada");			
		}
		
		Produto produtoSalvo = produtoRepository.save(produto);
		
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
