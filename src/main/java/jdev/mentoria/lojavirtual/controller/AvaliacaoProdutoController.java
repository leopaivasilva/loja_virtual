package jdev.mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.model.AvaliacaoProduto;
import jdev.mentoria.lojavirtual.repository.AvaliacaoProdutoRepository;

@RestController
public class AvaliacaoProdutoController {
	
	@Autowired
	private AvaliacaoProdutoRepository avaliacaoProdutoRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarAvaliacaoProduto")
	public ResponseEntity<AvaliacaoProduto> salvarAvaliacaoProduto(@RequestBody @Valid AvaliacaoProduto avaliacaoProduto) throws ExceptionMentoriaJava {
		
		if(avaliacaoProduto.getEmpresa() == null || avaliacaoProduto.getEmpresa() != null && avaliacaoProduto.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("Informe a empresa dona do Registro");			
		}	
		
		if(avaliacaoProduto.getProduto() == null || avaliacaoProduto.getProduto() != null && avaliacaoProduto.getProduto().getId() <= 0) {
			throw new ExceptionMentoriaJava("Informe o produto para a avaliação do Registro");			
		}
		
		if(avaliacaoProduto.getPessoa() == null || avaliacaoProduto.getPessoa() != null && avaliacaoProduto.getPessoa().getId() <= 0) {
			throw new ExceptionMentoriaJava("Informe a pessoa para a avaliação do Registro");			
		}
		
		avaliacaoProduto = avaliacaoProdutoRepository.saveAndFlush(avaliacaoProduto);
			
		return new ResponseEntity<AvaliacaoProduto>(avaliacaoProduto, HttpStatus.OK);

	}
	
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteAvaliacaoPorId/{idAvaliacao}")
	public ResponseEntity<?> deleteAvaliacaoPorId(@PathVariable("idAvaliacao") Long idAvaliacao) {
		
		avaliacaoProdutoRepository.deleteById(idAvaliacao);
		
		return new ResponseEntity<String>("Avaliação do Produto Removida", HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping(value = "**/obterAvaliacaoPessoa/{idPessoa}")
	public ResponseEntity<List<AvaliacaoProduto>> obterAvaliacaoPessoa(@PathVariable("idPessoa") Long idPessoa){
		
		List<AvaliacaoProduto> avaliacaoPessoas = avaliacaoProdutoRepository.avaliacaoPessoa(idPessoa);
		
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoPessoas, HttpStatus.OK);
		
	}		
	
	@ResponseBody
	@GetMapping(value = "**/obterAvaliacaoProduto/{idProduto}")
	public ResponseEntity<List<AvaliacaoProduto>> obterAvaliacaoProduto(@PathVariable("idProduto") Long idProduto){
		
		List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.avaliacaoProduto(idProduto);
		
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoProdutos, HttpStatus.OK);
		
	}	
	
	@ResponseBody
	@GetMapping(value = "**/obterAvaliacaoProdutoPessoa/{idProduto}/{idPessoa}")
	public ResponseEntity<List<AvaliacaoProduto>> obterAvaliacaoProdutoPessoa(@PathVariable("idProduto") Long idProduto, @PathVariable("idPessoa") Long idPessoa) {
		
		List<AvaliacaoProduto> avaliacaoProdutoPessoas = avaliacaoProdutoRepository.avaliacaoProdutoPessoa(idProduto,idPessoa);
		
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoProdutoPessoas, HttpStatus.OK);
		
	}	

}
