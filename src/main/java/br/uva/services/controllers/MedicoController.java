package br.uva.services.controllers;

import br.uva.model.clinica.medicos.MedicoClinica;
import br.uva.model.clinica.medicos.MedicoClinicaDLO;
import br.uva.model.user.UsuarioDLO;

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

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
	private MedicoClinicaDLO dlo;
    
	@Autowired
	private UsuarioDLO usuarioDLO;
        
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<MedicoClinica>> listAllUsers() {
        Iterable<MedicoClinica> users = dlo.findAllUsers();
        List<MedicoClinica> list = new ArrayList<>();
        users.forEach(list::add);

        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
        
    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<MedicoClinica> getUser(@PathVariable("username") String username) {
        MedicoClinica user = dlo.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody MedicoClinica user, UriComponentsBuilder ucBuilder) {
        if (dlo.isUserExist(user)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        dlo.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/medico/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
  
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MedicoClinica> deleteUser(@PathVariable("id") long id) {
        MedicoClinica user = dlo.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
  
        dlo.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<MedicoClinica> deleteAllUsers() {
        dlo.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUser(@PathVariable long id, @RequestBody MedicoClinica user) {
		if (dlo.findById(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		dlo.saveUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}