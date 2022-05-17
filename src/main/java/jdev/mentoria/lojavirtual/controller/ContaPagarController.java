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
import jdev.mentoria.lojavirtual.model.ContaPagar;
import jdev.mentoria.lojavirtual.repository.ContaPagarRepository;

@Controller
@RestController
public class ContaPagarController {
	
	@Autowired
	private ContaPagarRepository contaPagarRepository;
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/salvarContaPagar")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<ContaPagar> salvarProduto(@RequestBody @Valid ContaPagar contaPagar) throws ExceptionMentoriaJava { /*Recebe o JSON e converte para Objeto*/
		
		if (contaPagar.getEmpresa() == null || contaPagar.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("Empresa responsável deve ser informada");
		}
		

		if (contaPagar.getPessoa() == null || contaPagar.getPessoa().getId() <= 0) {
			throw new ExceptionMentoriaJava("Pessoa responsável deve ser informada");
		}

		
		if (contaPagar.getPessoa_fornecedor() == null || contaPagar.getPessoa_fornecedor().getId() <= 0) {
			throw new ExceptionMentoriaJava("Fornecedor responsável deve ser informado");
		}
		
		
		if (contaPagar.getId() == null) {
			List<ContaPagar> contaPagars = contaPagarRepository.buscarContaDesc(contaPagar.getDescricao().toUpperCase().trim());
		    
			if(!contaPagars.isEmpty()) {
		    	throw new ExceptionMentoriaJava("Já existe conta a pagar com a mesma descrição");
		    }
		}
		
	
		ContaPagar contaPagarSalvo = contaPagarRepository.save(contaPagar);
		
		return new ResponseEntity<ContaPagar>(contaPagarSalvo, HttpStatus.OK);
	}
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/deleteContaPagar")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<String> deleteContaPagar(@RequestBody ContaPagar contaPagar) { /*Recebe o JSON e converte para Objeto*/
		
		contaPagarRepository.deleteById(contaPagar.getId());
		
		return new ResponseEntity<String>("Conta a Pagar Removida", HttpStatus.OK);
	}	
	
	@ResponseBody /*POde dar um retorno da API*/
	@DeleteMapping(value = "**/deleteContaPagarPorId/{id}")
	public ResponseEntity<String> deleteContaPagarPorId(@PathVariable("id")  Long id ) {
		
		contaPagarRepository.deleteById(id);
		
		return new ResponseEntity<String>("Conta a Pagar Removida", HttpStatus.OK);
	}	
	
	@ResponseBody /*POde dar um retorno da API*/
	@GetMapping(value = "**/consultarContaPagar/{id}")
	public ResponseEntity<ContaPagar> consultarContaPagar(@PathVariable("id")  Long id ) throws ExceptionMentoriaJava {
		
		ContaPagar contaPagar = contaPagarRepository.findById(id).orElse(null);
		
		if (contaPagar  == null) {
			throw new ExceptionMentoriaJava("Código informando "+ id +" não encontrado no sistema");
		}
		
		return new ResponseEntity<ContaPagar>(contaPagar, HttpStatus.OK);
	}	
	
	
	@ResponseBody /*POde dar um retorno da API*/
	@GetMapping(value = "**/consultarContaPagar/{desc}")
	public ResponseEntity<List<ContaPagar>> consultarContaPagar(@PathVariable("desc")  String desc ) {
		
		List<ContaPagar> contapagar = contaPagarRepository.buscarContaDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<ContaPagar>>(contapagar, HttpStatus.OK);
	}	
	
	
		

}
