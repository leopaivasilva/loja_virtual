package jdev.mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.model.NotaItemProduto;
import jdev.mentoria.lojavirtual.repository.NotaItemProdutoRepository;

@Controller
@RestController
public class NotaItemProdutoController {
	
	@Autowired
	private NotaItemProdutoRepository notaItemProdutoRepository;
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/salvarNotaItemProduto")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<NotaItemProduto> salvarNotaItemProduto(@RequestBody @Valid NotaItemProduto notaItemProduto) throws ExceptionMentoriaJava { /*Recebe o JSON e converte para Objeto*/
		
		if (notaItemProduto.getId() == null) {			
			
			if (notaItemProduto.getProduto() == null || notaItemProduto.getProduto().getId() <= 0) {
				throw new ExceptionMentoriaJava("O produto deve ser informado");				
			}
			
			if (notaItemProduto.getNotaFiscalCompra() == null || notaItemProduto.getNotaFiscalCompra().getId() <= 0) {
				throw new ExceptionMentoriaJava("A Nota Fiscal deve ser informada");				
			}
			
			if(notaItemProduto.getEmpresa() == null || notaItemProduto.getEmpresa().getId() <= 0) {
				throw new ExceptionMentoriaJava("A Empresa deve ser informada");			
			}			
			
			
			List<NotaItemProduto> notaExistente = notaItemProdutoRepository.buscaNotaItemPorProdutoNota(notaItemProduto.getProduto().getId(), notaItemProduto.getNotaFiscalCompra().getId());
			
			if (!notaExistente.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe este produto cadastrado para essa nota");
			}
		}
		
		if (notaItemProduto.getQuantidade() <= 0 ) {
			throw new ExceptionMentoriaJava("A quantidade do produto deve ser informada");
		}
		
		NotaItemProduto notaItemProdutoSalvo = notaItemProdutoRepository.save(notaItemProduto);
		
		notaItemProdutoSalvo = notaItemProdutoRepository.findById(notaItemProduto.getId()).get();
		
		return new ResponseEntity<NotaItemProduto>(notaItemProdutoSalvo, HttpStatus.OK);
	}
	

	@ResponseBody /*POde dar um retorno da API*/
	@DeleteMapping(value = "**/deleteNotaItemPorId/{id}")
	public ResponseEntity<?> deleteNotaItemPorId(@PathVariable("id")  Long id ) {
		
		notaItemProdutoRepository.deleteByIdNotaItem(id);
		
		return new ResponseEntity("Nota Item Produto Removida", HttpStatus.OK);
	}
	
	
}
