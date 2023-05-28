package curso.api.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController //Arquitetura REST
@Api(value = "Exemplo de API")
@RequestMapping(value = "/usuario")
public class IndexController {
	
	@Autowired //Se fosse CDI seria @Inject
	private UsuarioRepository usuarioRepository;
	
	// Serviço RESTful
	@ApiOperation("Obtém uma lista de objetos de exemplo")
	@GetMapping(value = "/{id}/codigovenda/{venda}", produces = "application/json")
	public ResponseEntity<Usuario> venda(@PathVariable(value = "id") Long id,
											 @PathVariable(value = "venda") Long venda) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

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
	
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	@PostMapping(value = "/vendausuario/idvenda/{idvenda}", produces = "application/json")
	public ResponseEntity<Usuario> cadastrarvenda(@PathVariable Long iduser, 
												  @PathVariable Long idvenda){
		
		/*Aqui seria o processo de venda
		 *Usuario usuarioSalvo = usuarioRepository.save(usuario);*/
		
		return new ResponseEntity("id user :" + iduser + " idvenda :" + idvenda, HttpStatus.OK);
	}
	
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> atualizarcadastro(@RequestBody Usuario usuario){
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	@PostMapping(value = "/vendausuario/idvenda/{idvenda}", produces = "application/json")
	public ResponseEntity<Usuario> updadevenda(@PathVariable Long iduser, 
											   @PathVariable Long idvenda){
		
		/*Aqui seria o processo de venda
		 *Usuario usuarioSalvo = usuarioRepository.save(usuario);*/
		
		return new ResponseEntity("Venda Atualizada", HttpStatus.OK);
	}
}
