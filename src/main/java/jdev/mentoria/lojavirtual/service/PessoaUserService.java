package jdev.mentoria.lojavirtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jdev.mentoria.lojavirtual.model.PessoaFisica;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.PessoaRepository;
import jdev.mentoria.lojavirtual.repository.PesssoaFisicaRepository;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;

@Service
public class PessoaUserService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
    @Autowired
	private PesssoaFisicaRepository pessoaFisicaRepository;  
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {
		
		for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++ ) {
			pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
			pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
		}
		
		pessoaJuridica = pessoaRepository.save(pessoaJuridica);
		
		Usuario usuarioPJ = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());
		
		if (usuarioPJ == null ) {
			
			String constraint = usuarioRepository.consultaConstraintAcesso(); 
			
			if (constraint != null) {
				
				jdbcTemplate.execute("begin; alter table usuario_acesso drop CONSTRAINT " + constraint +"; commit; ");
			}
			
			usuarioPJ = new Usuario();
			usuarioPJ.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPJ.setEmpresa(pessoaJuridica);
			usuarioPJ.setPessoa(pessoaJuridica);
			usuarioPJ.setLogin(pessoaJuridica.getEmail());
			
			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCript = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPJ.setSenha(senhaCript);
			
			usuarioPJ = usuarioRepository.save(usuarioPJ);
			
			usuarioRepository.insereAcessoUser(usuarioPJ.getId());
			usuarioRepository.insereAcessoUserPJ(usuarioPJ.getId(), "ROLE_ADMIN");
			
			StringBuilder menssagemHtml = new StringBuilder();
			
			menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
			menssagemHtml.append("<b>Login: </b>"+pessoaJuridica.getEmail()+"<br/>");
			menssagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			menssagemHtml.append("Obrigado!");
			
			try{
			  serviceSendEmail.enviarEmailHtml("Acesso gerado para a loja virtual", menssagemHtml.toString(), pessoaJuridica.getEmail());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return pessoaJuridica;
		
	} 
	
	
	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {
		//juridica = pesssoaRepository.save(juridica);
			
			for (int i = 0; i< pessoaFisica.getEnderecos().size(); i++) {
				pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
				//pessoaFisica.getEnderecos().get(i).setEmpresa(pessoaFisica);
			}
			
			pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
			
			Usuario usuarioPf = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());
			
			if (usuarioPf == null) {
				
				String constraint = usuarioRepository.consultaConstraintAcesso();
				if (constraint != null) {
					jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint +"; commit;");
				}
				
				usuarioPf = new Usuario();
				usuarioPf.setDataAtualSenha(Calendar.getInstance().getTime());
				usuarioPf.setEmpresa(pessoaFisica.getEmpresa());
				usuarioPf.setPessoa(pessoaFisica);
				usuarioPf.setLogin(pessoaFisica.getEmail());
				
				String senha = "" + Calendar.getInstance().getTimeInMillis();
				String senhaCript = new BCryptPasswordEncoder().encode(senha);
				
				usuarioPf.setSenha(senhaCript);
				
				usuarioPf = usuarioRepository.save(usuarioPf);
				
				usuarioRepository.insereAcessoUser(usuarioPf.getId());
				
				StringBuilder menssagemHtml = new StringBuilder();
				
				menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
				menssagemHtml.append("<b>Login: </b>"+pessoaFisica.getEmail()+"<br/>");
				menssagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
				menssagemHtml.append("Obrigado!");
				
				try {
				  serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual", menssagemHtml.toString() , pessoaFisica.getEmail());
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			return pessoaFisica;
		}	

}
