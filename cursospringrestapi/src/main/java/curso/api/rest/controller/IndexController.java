package curso.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController //Arquitetura REST
@Api(value = "Exemplo de API")
@RequestMapping(value = "/usuario")
public class IndexController {
	
	//Serviço RESTful
	@ApiOperation("Obtém uma lista de objetos de exemplo")
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity init(@RequestParam (value = "nome", required = true, 
	defaultValue = "Nome não Informado") String nome, @RequestParam("salario") Long salario)  {
		
		System.out.println("Parametro sendo recebido " + nome);
		return new ResponseEntity("Olá Usuário REST Spring Boot " + nome + " salario é: " + salario, HttpStatus.OK);
	}
	
	
	
	
}
