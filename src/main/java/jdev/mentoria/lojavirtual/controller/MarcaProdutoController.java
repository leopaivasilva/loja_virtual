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
import jdev.mentoria.lojavirtual.model.MarcaProduto;
import jdev.mentoria.lojavirtual.repository.MarcaRepository;

@Controller
@RestController
public class MarcaProdutoController {
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/salvarMarca")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<MarcaProduto> salvarMarcaProduto(@RequestBody @Valid MarcaProduto marcaProduto) throws ExceptionMentoriaJava { /*Recebe o JSON e converte para Objeto*/
		
		if (marcaProduto.getId() == null) {			
			List<MarcaProduto> marcaProdutos = marcaRepository.buscarMarcaDesc(marcaProduto.getNomeDesc().toUpperCase());
			
			if (!marcaProdutos.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe cadastrado marca do produto com a descrição informada: " + marcaProduto.getNomeDesc());
			}
		}
		
		MarcaProduto marcaProdutoSalvo = marcaRepository.save(marcaProduto);
		
		return new ResponseEntity<MarcaProduto>(marcaProdutoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/deleteMarca")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<?> deleteMarca(@RequestBody MarcaProduto marcaProduto) { /*Recebe o JSON e converte para Objeto*/
		
		marcaRepository.deleteById(marcaProduto.getId());
		
		return new ResponseEntity("Marca do Produto Removida", HttpStatus.OK);
	}	
	
	@ResponseBody /*POde dar um retorno da API*/
	@DeleteMapping(value = "**/deleteMarcaPorId/{id}")
	public ResponseEntity<?> deleteMarcaPorId(@PathVariable("id")  Long id ) {
		
		marcaRepository.deleteById(id);
		
		return new ResponseEntity("Marca do Produto Removida", HttpStatus.OK);
	}	
	
	@ResponseBody /*POde dar um retorno da API*/
	@GetMapping(value = "**/consultarMarca/{id}")
	public ResponseEntity<MarcaProduto> consultarMarca(@PathVariable("id")  Long id ) throws ExceptionMentoriaJava {
		
		MarcaProduto marcaProduto = marcaRepository.findById(id).orElse(null);
		
		if (marcaProduto  == null) {
			throw new ExceptionMentoriaJava("Código informando "+ id +" não encontrado no sistema");
		}
		
		return new ResponseEntity<MarcaProduto>(marcaProduto, HttpStatus.OK);
	}	
	
	
	@ResponseBody /*POde dar um retorno da API*/
	@GetMapping(value = "**/consultarMarcaDes/{desc}")
	public ResponseEntity<List<MarcaProduto>> consultarMarca(@PathVariable("desc")  String desc ) {
		
		List<MarcaProduto> marcaProduto = marcaRepository.buscarMarcaDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<MarcaProduto>>(marcaProduto, HttpStatus.OK);
	}	
	
	
		

}
