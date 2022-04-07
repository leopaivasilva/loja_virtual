package jdev.mentoria.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import jdev.mentoria.lojavirtual.controller.PessoaController;
import jdev.mentoria.lojavirtual.enums.TipoEndereco;
import jdev.mentoria.lojavirtual.model.Endereco;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePessoaUsuario extends TestCase {
	
	@Autowired
	private PessoaController pessoaController;
	
	@Test
	public void testCadPessoaFisica() throws ExceptionMentoriaJava {

		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Leonardo Paiva");
		pessoaJuridica.setEmail("leopaivasilva@gmail.com");
		pessoaJuridica.setTelefone("83998309291");
		pessoaJuridica.setInscEstadual("123456");
		pessoaJuridica.setInscMunicipal("654321");
		pessoaJuridica.setNomeFantasia("DesvendandoJava LTDA");
		pessoaJuridica.setRazaoSocial("6498321876599");
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Tambau");
		endereco1.setCep("58039210");
		endereco1.setComplemento("Apto 503");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("200");
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setRuaLogradouro("Rua Severino Massa Spinelli");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUf("PB");
		endereco1.setCidade("João Pessoa");
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jardim Oceania");
		endereco2.setCep("58036123");
		endereco2.setComplemento("Apto 903");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("500");
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setRuaLogradouro("Rua Josemar Rodrigues de Carvalho");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUf("SP");
		endereco2.setCidade("São Paulo");
		
		pessoaJuridica.getEnderecos().add(endereco1);
		pessoaJuridica.getEnderecos().add(endereco2);
		
		pessoaController.salvarPJ(pessoaJuridica);
		
	}

}
