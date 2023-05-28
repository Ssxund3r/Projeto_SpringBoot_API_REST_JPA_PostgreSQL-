package curso.api.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController //Arquitetura REST
@Api(value = "Exemplo de API")
@RequestMapping(value = "/usuario")
public class IndexController {
	
	@Autowired //Se fosse CDI seria @Inject
	private UsuarioRepository usuarioRepository;
	
	//Serviço RESTful
		@ApiOperation("Obtém uma lista de objetos de exemplo")
		@GetMapping(value = "/{id}/codigovenda/{venda}", produces = "application/json")
		public ResponseEntity<Usuario> relatorio(@PathVariable (value = "id") Long id,
																@PathVariable (value="venda") Long venda){	
			
			Optional<Usuario> usuario = usuarioRepository.findById(id);
			
			//O retorno seria um relatorio
			return new ResponseEntity(usuario.get(), HttpStatus.OK);
		}
	
	//Serviço RESTful
	@ApiOperation("Obtém uma lista de objetos de exemplo")
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> init(@PathVariable (value = "id") Long id){	
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		return new ResponseEntity(usuario.get(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> usuario(){
		
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}
	
}
