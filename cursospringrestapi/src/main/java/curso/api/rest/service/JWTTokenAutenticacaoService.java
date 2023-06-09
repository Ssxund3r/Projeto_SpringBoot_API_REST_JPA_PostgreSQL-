package curso.api.rest.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import curso.api.rest.application.ApplicationContexLoad;
import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {
	
	/*Tem a validade do Token 2 dias*/
	private static final long EXPRITATION_TIME = 172800000;
	
	//Uma senha unica para compor a autenticacao
	private static final String SECRET = "*--*/4265a3-*-3*/e9s+w9/3e*-";
	
	//Prefixo padrão de Token 
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	//Gerando token de autenticado e adicionando ao cabeçalho e resposta HTTP
	public void addAuthentication(HttpServletResponse response, String username) 
			throws IOException {
		
		//Montagem do token
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPRITATION_TIME))
				//Compactação e algoritmos de geração de senha
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		//Junta token com o prefixo
		//Bearer 98sdf7#*4asfg8as5**00
		String token = TOKEN_PREFIX + " " + JWT; 
		
		//Adiciona no cabeçalho http
		//Authorization: Bearer 98sdf7#*4asfg8as5**00
		response.addHeader(HEADER_STRING, token); 
		
		//Liberando resposta para portas diferentes que usa a API ou clientes WEB
		liberacaoCors(response);
		
		 ApplicationContexLoad
			.getApplicationContext()
			.getBean(UsuarioRepository.class)
			.atualizaTokenUser(JWT, username);
		
		//Escreve token como resposta no corpo http
		response.getWriter().write("{\"Authorization\": \""+token+"\"}");
	}
	
	//Retorna o usuário validado com token ou caso não seja válido retorna null
	public Authentication getAuthentication(HttpServletRequest  request,
									        HttpServletResponse response) {
		
		//Pega o token enviado no cabeçalho http
		String token = request.getHeader(HEADER_STRING);
		
		try {
		if (token != null) {
			
			String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
			
			//Faz a validação do token ao usuário na requisição 
			String user = Jwts.parser()
					//Bearer 98sdf7#*4asfg8as5**00
					.setSigningKey(SECRET)
					//98sdf7#*4asfg8as5**00
					.parseClaimsJws(tokenLimpo)
					//Gabriel Costa
					.getBody().getSubject();
			
			if (user != null) {
				Usuario usuario = ApplicationContexLoad
						.getApplicationContext()
						.getBean(UsuarioRepository.class)
						.findUserByLogin(user);
				
				//Retornar o usuário logado
				if (usuario != null) {
					
					if(tokenLimpo.equalsIgnoreCase(usuario.getToken())) {
					
					return new UsernamePasswordAuthenticationToken
							(usuario.getNome(), usuario.getSenha(), 
									usuario.getAuthorities());
					}
				} 
			 } 
			} // Fim condição token
		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			try {
				response.getOutputStream().println("Token expirado!! Tente novamente!");
			} catch (Exception e1) {}
		}
		liberacaoCors(response);
		
		//Não autorizado
		return null; 
	}

	private void liberacaoCors(HttpServletResponse response) {
		
		if(response.getHeader("Access-Control-Allow-Origin") == null) {
		   response.addHeader("Access-Control-Allow-Origin", "*");
		}
		
		if(response.getHeader("Access-Control-Allow-Headers") == null) {
		   response.addHeader("Access-Control-Allow-Headers", "*");
		}
		
		if(response.getHeader("Access-Control-Request-Headers") == null) {
		   response.addHeader("Access-Control-Request-Headers", "*");
		}
		
		if(response.getHeader("Access-Control-Allow-Methods") == null) {
		   response.addHeader("Access-Control-Allow-Methods", "*");
		}
		
	}
}