package br.uva.services.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import br.uva.model.user.Usuario;
import br.uva.model.user.UsuarioDLO;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
	private UsuarioDLO userService;
        
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> listAllUsers() {
        Iterable<Usuario> users = userService.findAllUsers();
        List<Usuario> list = new ArrayList<>();
        users.forEach(list::add);

        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Usuario> getUser(@PathVariable("id") Long id) {
        Usuario user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Usuario> getUser(@PathVariable("username") String username) {
        Usuario user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Usuario user, UriComponentsBuilder ucBuilder) {
        if (userService.isUserExist(user)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
  
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Usuario> deleteUser(@PathVariable("id") long id) {
        Usuario user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
  
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Usuario> deleteAllUsers() {
  
        userService.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
    
    @RequestMapping(value = {"/busca/{query}/{pageNumber}", "/busca/{query}"})
	public Iterable<Usuario> busca(@PathVariable(required = false) Integer pageNumber, @PathVariable(required = false) String query) {
		
		Iterable<Usuario> ret = null;

		if (query == null) {
			query = "";
		}
		
		if (pageNumber == null) {
			pageNumber = 1;
		}
		
                ret = userService.getBusca(query, pageNumber);
		
		return ret;
	}
    
}