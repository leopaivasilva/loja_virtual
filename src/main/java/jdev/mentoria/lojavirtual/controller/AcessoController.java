package jdev.mentoria.lojavirtual.controller;

import java.util.List;

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
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;

@Controller
@RestController
public class AcessoController {
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/salvarAcesso")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoriaJava { /*Recebe o JSON e converte para Objeto*/
		
		if (acesso.getId() == null) {
			
			List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
			
			if (!acessos.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe cadastrado acesso com a descrição: " + acesso.getDescricao());
			}
		}
		
		Acesso acessoSalvo = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody /*POde dar um retorno da API*/
	@PostMapping(value = "**/deleteAcesso")  /*Mapeando a url para receber o JSON*/
	public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) { /*Recebe o JSON e converte para Objeto*/
		
		acessoRepository.deleteById(acesso.getId());
		
		return new ResponseEntity("Acesso Removido", HttpStatus.OK);
	}	
	
	@ResponseBody /*POde dar um retorno da API*/
	@DeleteMapping(value = "**/deleteAcessoPorId/{id}")
	public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id")  Long id ) {
		
		acessoRepository.deleteById(id);
		
		return new ResponseEntity("Acesso Removido", HttpStatus.OK);
	}	
	
	@ResponseBody /*POde dar um retorno da API*/
	@GetMapping(value = "**/consultarAcesso/{id}")
	public ResponseEntity<Acesso> consultarAcesso(@PathVariable("id")  Long id ) throws ExceptionMentoriaJava {
		
		Acesso acesso = acessoRepository.findById(id).orElse(null);
		
		if (acesso  == null) {
			throw new ExceptionMentoriaJava("Código informando "+ id +" não encontrado no sistema");
		}
		
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}	
	
	
	@ResponseBody /*POde dar um retorno da API*/
	@GetMapping(value = "**/consultarAcessoDes/{desc}")
	public ResponseEntity<List<Acesso>> consultarAcesso(@PathVariable("desc")  String desc ) {
		
		List<Acesso> acesso = acessoRepository.buscarAcessoDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<Acesso>>(acesso, HttpStatus.OK);
	}	
	
	
		

}
