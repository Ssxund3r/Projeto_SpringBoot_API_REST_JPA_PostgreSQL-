package curso.api.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController //Arquitetura REST
@Api(value = "Exemplo de API")
@RequestMapping(value = "/usuario")
public class IndexController {
	
	//Serviço RESTful
	@ApiOperation("Obtém uma lista de objetos de exemplo")
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> init()  {
		Usuario usuario = new  Usuario();
		usuario.setId(1L);
		usuario.setLogin("gabriel.fcosta@gmail.com");
		usuario.setSenha("123");
		usuario.setNome("Gabriel");
		
		Usuario usuario2 = new Usuario();
		usuario2.setId(2L);
		usuario2.setLogin("natalia.scosta@gmail.com");
		usuario2.setSenha("456");
		usuario2.setNome("Natália");
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuario);
		usuarios.add(usuario2);
		
		return new ResponseEntity(usuarios, HttpStatus.OK);
	}
	
	
	
	
}
