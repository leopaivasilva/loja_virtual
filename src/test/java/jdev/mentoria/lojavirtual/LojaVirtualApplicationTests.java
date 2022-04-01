package jdev.mentoria.lojavirtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jdev.mentoria.lojavirtual.controller.AcessoController;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests extends TestCase {
		
	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	
	@Autowired
	private WebApplicationContext wac;
	
	/*Teste do end-point de salvar acesso*/
	@Test
	public void RestAPICadastroAcesso() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_COMPRADOR");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
				                   .perform(MockMvcRequestBuilders.post("/salvarAcesso")
				                   .content(objectMapper.writeValueAsString(acesso))
				                   .accept(MediaType.APPLICATION_JSON)
				                   .contentType(MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
		
		/*Converter o retorno da API para um objeto de acesso*/
		
		Acesso objetoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
		
	}
	
	/*Teste do end-point de deletar acesso*/
	@Test
	public void RestAPIDeleteAcesso() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_DELETANDO");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
				                   .perform(MockMvcRequestBuilders.post("/deleteAcesso")
				                   .content(objectMapper.writeValueAsString(acesso))
				                   .accept(MediaType.APPLICATION_JSON)
				                   .contentType(MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Retorno do Status da API: " + retornoApi.andReturn().getResponse().getStatus());
		
		assertEquals("Acesso Removido", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
	}	
	
	/*Teste do end-point de deletar acesso por ID*/
	@Test
	public void RestAPIDeletePorIDAcesso() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_DELETANDO_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
				                   .perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId())
				                   .content(objectMapper.writeValueAsString(acesso))
				                   .accept(MediaType.APPLICATION_JSON)
				                   .contentType(MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Retorno do Status da API: " + retornoApi.andReturn().getResponse().getStatus());
		
		assertEquals("Acesso Removido", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
	}
	
	/*Teste do end-point de consultar acesso id*/
	@Test
	public void RestAPIConsultarAcesso() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_CONSULTANDO_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
				                   .perform(MockMvcRequestBuilders.get("/consultarAcesso/" + acesso.getId())
				                   .content(objectMapper.writeValueAsString(acesso))
				                   .accept(MediaType.APPLICATION_JSON)
				                   .contentType(MediaType.APPLICATION_JSON));
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		Acesso acessoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
		
		assertEquals(acesso.getId(), acessoRetorno.getId());
		
	}	
	
	
	/*Teste do end-point de consultar acesso descrição*/
	@Test
	public void RestAPIConsultarAcessoDescricao() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_CONSULTANDO_DESCRICAO");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
				                   .perform(MockMvcRequestBuilders.get("/consultarAcessoDes/CONSULTANDO_DESCRICAO")
				                   .content(objectMapper.writeValueAsString(acesso))
				                   .accept(MediaType.APPLICATION_JSON)
				                   .contentType(MediaType.APPLICATION_JSON));
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		List<Acesso> retornoApiList = objectMapper.
				                         readValue(retornoApi.andReturn()
				                        		.getResponse().getContentAsString(), 
				                        		new TypeReference<List<Acesso>>() {});
		
		assertEquals(1, retornoApiList.size());
		
		assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());

        
		acessoRepository.deleteById(acesso.getId());
		
	}		
	

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
