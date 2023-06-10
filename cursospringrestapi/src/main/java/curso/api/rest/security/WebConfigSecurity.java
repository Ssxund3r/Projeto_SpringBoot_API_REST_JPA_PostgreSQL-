package curso.api.rest.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import curso.api.rest.filter.JWTLoginFilter;
import curso.api.rest.filter.JwtApiAutenticacaoFilter;
import curso.api.rest.service.ImplementacaoUserDetailService;

//Mapeia URL, endereços, autoriza ou bloqueia acesso a URL
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter 
									   implements WebMvcConfigurer {
	
	@Autowired
	private ImplementacaoUserDetailService detailServices;
	
	//Configura as solicitações de acesso por Http
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Ativando a proteção contra usuário que não estão validados por token
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		//Ativando a permissão para acesso a página inicial do sistema EX: sistema.com.br/
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		
		//URL de Logout - Redireciona após o user deslogar do sistema 
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		//Mapeia URL de logout e inválida usuário
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		//Filtra requisições de login para autenticação
		.and().addFilterBefore(new JWTLoginFilter("/login", 
				authenticationManager()), UsernamePasswordAuthenticationFilter.class)
		
		//Filtra demais requisições para verificar a presença do TOKEN JWT no HEADER HTTP
		.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Service que irá consultar o usuário no banco de dados 
		auth.userDetailsService(detailServices)
		
		//Padrão de codificação de senha 
		.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/**")
         .addResourceLocations("classpath:/public/")
         .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
	}
	
}
