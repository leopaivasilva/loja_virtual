package jdev.mentoria.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.model.CategoriaProduto;
import jdev.mentoria.lojavirtual.model.dto.CategoriaProdutoDto;
import jdev.mentoria.lojavirtual.repository.CategoriaProdutoRepository;

@RestController
public class CategoriaProdutoController {
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	
	@ResponseBody /*POde dar um retorno da API*/
	@GetMapping(value = "**/consultarCategoriaDes/{desc}")
	public ResponseEntity<List<CategoriaProduto>> consultarCategoriaProdutoDes(@PathVariable("desc")  String desc ) {
		
		List<CategoriaProduto> categoriaProduto = categoriaProdutoRepository.buscarCategoriaDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<CategoriaProduto>>(categoriaProduto, HttpStatus.OK);
	}		
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/deleteCategoria")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<?> deleteCategoria(@RequestBody CategoriaProduto categoriaProduto) { /*Recebe o JSON e converte para Objeto*/
		
		
		if(categoriaProdutoRepository.findById(categoriaProduto.getId()).isPresent() == false) {
			return new ResponseEntity("Categoria já removida", HttpStatus.OK);
		}
		
		categoriaProdutoRepository.deleteById(categoriaProduto.getId());
		
		return new ResponseEntity("Categoria Removido", HttpStatus.OK);
	}		
	
	@ResponseBody
	@PostMapping(value = "**/salvarCategoria")
	public ResponseEntity<CategoriaProdutoDto> salvarCategoria(@RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoriaJava {
		
		
		if(categoriaProduto.getEmpresa() == null || (categoriaProduto.getEmpresa().getId() == null)) {
			throw new ExceptionMentoriaJava("A empresa deve ser informada.");
		}
		
		if(categoriaProduto.getId() == null && categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc().toUpperCase())) {
			throw new ExceptionMentoriaJava("Já existe uma categoria cadastrada com esse nome. Favor informar uma nova categoria");
		}
		
		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);
		
		CategoriaProdutoDto categoriaProdutoDto = new CategoriaProdutoDto();
		categoriaProdutoDto.setId(categoriaSalva.getId());
		categoriaProdutoDto.setNomeDesc(categoriaSalva.getNomeDesc());
		categoriaProdutoDto.setEmpresa(categoriaSalva.getEmpresa().getId().toString());
		
		return new ResponseEntity<CategoriaProdutoDto>(categoriaProdutoDto, HttpStatus.OK);
	}

}
