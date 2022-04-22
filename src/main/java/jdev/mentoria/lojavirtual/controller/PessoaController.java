package jdev.mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.model.Endereco;
import jdev.mentoria.lojavirtual.model.PessoaFisica;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.model.dto.CepDTO;
import jdev.mentoria.lojavirtual.repository.EnderecoRepository;
import jdev.mentoria.lojavirtual.repository.PessoaRepository;
import jdev.mentoria.lojavirtual.repository.PesssoaFisicaRepository;
import jdev.mentoria.lojavirtual.service.PessoaUserService;
import jdev.mentoria.lojavirtual.util.ValidaCNPJ;
import jdev.mentoria.lojavirtual.util.ValidaCPF;

@RestController
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PesssoaFisicaRepository pessoaFisicaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	
	@ResponseBody
	@GetMapping(value = "**/consultaPfNome/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome) {
		
		List<PessoaFisica> fisicas = pessoaFisicaRepository.pesquisaPorNomePF(nome.trim().toUpperCase());
		
		jdbcTemplate.execute("begin; update tabela_acesso_end_potin set qtd_acesso_end_point = qtd_acesso_end_point + 1 where nome_end_point = 'END-POINT-NOME-PESSOA-FISICA'; commit;");
		
		return new ResponseEntity<List<PessoaFisica>>(fisicas, HttpStatus.OK);
	}
	
	
	
	@ResponseBody
	@GetMapping(value = "**/consultaPfCpf/{cpf}")
	public ResponseEntity<List<PessoaFisica>> consultaPfCpf(@PathVariable("cpf") String cpf) {
		
		List<PessoaFisica> fisicas = pessoaFisicaRepository.pesquisaPorCpfPF(cpf);
		
		return new ResponseEntity<List<PessoaFisica>>(fisicas, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping(value = "**/consultaNomePJ/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaNomePJ(@PathVariable("nome") String nome) {
		
		List<PessoaJuridica> fisicas = pessoaRepository.pesquisaPorNomePJ(nome.trim().toUpperCase());
		
		return new ResponseEntity<List<PessoaJuridica>>(fisicas, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping(value = "**/consultaCnpjPJ/{cnpj}")
	public ResponseEntity<List<PessoaJuridica>> consultaCnpjPJ(@PathVariable("cnpj") String cnpj) {
		
		List<PessoaJuridica> fisicas = pessoaRepository.existeCNPJCadastradoList(cnpj.trim().toUpperCase());
		
		return new ResponseEntity<List<PessoaJuridica>>(fisicas, HttpStatus.OK);
	}	
	
	@ResponseBody
	@GetMapping("**/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep){

		return new ResponseEntity<CepDTO>(pessoaUserService.consultaCEP(cep), HttpStatus.OK);
		
	}
	
	@ResponseBody
	@PostMapping(value = "**/salvarPJ")
	public ResponseEntity<PessoaJuridica> salvarPJ(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava{
		
		if (pessoaJuridica == null) {
			throw new ExceptionMentoriaJava("Pessoa Juridica não informada");
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existeCNPJCadastrado(pessoaJuridica.getCnpj()) != null) {
			throw new ExceptionMentoriaJava("Já existe CNPJ cadastrado no sistema com o número informado: " + pessoaJuridica.getCnpj());
			
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existeInscEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
			throw new ExceptionMentoriaJava("Já existe uma inscrição estadual cadastrada no sistema com o número informado: " + pessoaJuridica.getInscEstadual());
			
		}
		
		if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoriaJava("CNPJ informado está inválido: " + pessoaJuridica.getCnpj());
			
		}
		
		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			
			for(int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				
				CepDTO cepDTO = pessoaUserService.consultaCEP(pessoaJuridica.getEnderecos().get(p).getCep());
				
				pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
				pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
				pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
				pessoaJuridica.getEnderecos().get(p).setRuaLogradouro(cepDTO.getLogradouro());
				pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
				
			}
			
		}else {
			
			for(int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				
				Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();
				
				if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
				
					CepDTO cepDTO = pessoaUserService.consultaCEP(pessoaJuridica.getEnderecos().get(p).getCep());
					
					pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
					pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
					pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
					pessoaJuridica.getEnderecos().get(p).setRuaLogradouro(cepDTO.getLogradouro());
					pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
					
				}
			}
			
		}
		
		
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@PostMapping(value = "**/salvarPF")
	public ResponseEntity<PessoaFisica> salvarPF(@RequestBody PessoaFisica pessoaFisica) throws ExceptionMentoriaJava{
		
		if (pessoaFisica == null) {
			throw new ExceptionMentoriaJava("Pessoa Fisica não informada");
		}
		
		if (pessoaFisica.getId() == null && pessoaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
			throw new ExceptionMentoriaJava("Já existe CPF cadastrado no sistema com o número informado: " + pessoaFisica.getCpf());
			
		}
	
		if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("CPF informado está inválido: " + pessoaFisica.getCpf());
			
		}		
		
		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);

		
		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
		
	}	

}
