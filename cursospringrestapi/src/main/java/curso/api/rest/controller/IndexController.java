package curso.api.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.dto.UsuarioDTO;
import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;

@CrossOrigin(origins = "*")
@RestController //Arquitetura REST
@RequestMapping(value = "/usuario")
public class IndexController {
	
	@Autowired //Se fosse CDI seria @Inject
	private UsuarioRepository usuarioRepository;
	
	//Serviço RESTful
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<UsuarioDTO> getusuarioporid(@PathVariable Long id) {
	    return usuarioRepository.findById(id)
	            .map(usuario -> ResponseEntity.ok(new UsuarioDTO(usuario)))
	            .orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> listarusuarios(){
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
		
		return ResponseEntity.ok(usuarios);
	}
	
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){	
		usuario.getTelefones().forEach(telefone -> telefone.setUsuario(usuario));		
		
		String senhaCriptografada = new BCryptPasswordEncoder()
			   .encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> atualizarcadastro(@RequestBody Usuario usuario){		
		if (usuario.getId() == null) {
	        return ResponseEntity.badRequest().build();
	    }
	    if (!usuarioRepository.existsById(usuario.getId())) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    for(int pos = 0; pos < usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
	    
	    Usuario usuarioTemp = usuarioRepository.findUserByLogin(usuario.getLogin());
	    
	    //Senha diferente 
	    if(!usuarioTemp.getSenha().equals(usuario.getSenha())) {
	    	String senhaCriptografada = new BCryptPasswordEncoder()
	 			   .encode(usuario.getSenha());
	 		usuario.setSenha(senhaCriptografada);
	    }
	    
	    Usuario usuarioSalvo = usuarioRepository.save(usuario);
	        
	    return ResponseEntity.ok(usuarioSalvo);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> atualizarcadastroid
				       (@PathVariable Long id, @RequestBody Usuario usuario) {
		Optional<Usuario> usuariosOptional = usuarioRepository.findById(id);
		Map<String, Object> response = new HashMap<String, Object>();

		if (!usuariosOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		if (usuario.getId() == null) {
			return ResponseEntity.badRequest().build();
		}
		if (!usuarioRepository.existsById(usuario.getId())) {
			return ResponseEntity.notFound().build();
		}

		for (int pos = 0; pos < usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		
		Usuario usuarioTemp = usuarioRepository.findUserByLogin(usuario.getLogin());
		
		//Senha diferente 
	    if(!usuarioTemp.getSenha().equals(usuario.getSenha())) {
	    	String senhaCriptografada = new BCryptPasswordEncoder()
	 			   .encode(usuario.getSenha());
	 		usuario.setSenha(senhaCriptografada);
	    }
		
		Usuario usuarioAtualizado = usuarioRepository.save
				(usuarioRepository.save(usuario));

		response.put("message", "Usuário atualizado com sucesso");
		response.put("usuario", usuarioAtualizado);

		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		try {
			usuarioRepository.deleteById(id);
			return ResponseEntity.ok("Exclusão realizada com sucesso!");
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
