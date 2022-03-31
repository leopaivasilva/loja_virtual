package jdev.mentoria.lojavirtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jdev.mentoria.lojavirtual.controller.AcessoController;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;
import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests extends TestCase {
		
	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;

	@Test
	public void testCadastraAcesso() {
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ADMIN");
		
        /*Gravou no Banco de dados*/
		acesso = acessoController.salvarAcesso(acesso).getBody();
        
        assertEquals(true, acesso.getId() > 0);
		
        /*Valida dados salvos de forma correta*/
        assertEquals("ROLE_ADMIN", acesso.getDescricao());
        
        /*Teste de carregamento*/
        Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
        
        assertEquals(acesso.getId(), acesso2.getId());
        
        /*Teste de Delete da camada de perssitência*/
        acessoRepository.deleteById(acesso2.getId());
        
        acessoRepository.flush(); /*Roda esse sql de comando no banco de dados*/
        
        Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
        
        assertEquals(true, acesso3 == null);
        
        /*Teste de Query*/
        
        acesso = new Acesso();
        
        acesso.setDescricao("ROLE_ALUNO");
        
        acesso = acessoController.salvarAcesso(acesso).getBody();
        
        List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());
        
        assertEquals(1, acessos.size());
        
        acessoRepository.deleteById(acesso.getId());
	}

}
