package jdev.mentoria.lojavirtual.security;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*Criar a autenticação e retornar a autenticação JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {
	
	/*Token de validade de 11 dias*/
	private static final long EXPIRATION_TIME = 959990000;
	
	/*Chave de senha para juntar com o JWT*/
	private static final String SECRET = "ss/-*-*sfghj676ssd-s/^~Qxy**??://P";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	/*Gera o token e dar a resposta para o cliente com o JWT*/
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		
		/*montagem do token*/
		
		String JWT = Jwts.builder().   /*Chama o gerador de token*/
				     setSubject(username)  /*Adiciona o user*/
		             .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  /*Tempo de expiração*/
		             .signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		/*Ex: Bearer *-/a*jbmasdjsdha sgdhgahsdghsadjsagdjasjdjsahdgjajhgsahdjsahdahsgdh*/
		String token = TOKEN_PREFIX + " " + JWT;
		
		/*Dar a resposta para tela e para o cliente, pode ser em outra API, navegador, aplicativo, javascript, chamada java*/
		response.addHeader(HEADER_STRING, token);
		
		liberacaoCors(response);
		
		/*Usado para ver no postman para teste*/
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	
	}
	
	/*Fazendo liberação contra erro de Cors no navegador*/
	private void liberacaoCors(HttpServletResponse response) {
		
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");				
		}
		
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");				
		}
		
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");				
		}
		
		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");				
		}		
		
	}
	

}
