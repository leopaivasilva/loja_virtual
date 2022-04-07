package jdev.mentoria.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import jdev.mentoria.lojavirtual.controller.PessoaController;
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
		
		pessoaController.salvarPJ(pessoaJuridica);
		
	}

}
