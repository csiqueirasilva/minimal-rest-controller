/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.services.controllers;

import br.uva.model.clinicas.pacientes.Paciente;
import br.uva.model.clinicas.pacientes.PacienteDLO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author csiqueira
 */
@RestController
@RequestMapping("/paciente")
public class PacienteController {

	@Autowired
	private PacienteDLO dlo;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<Paciente>> listAllUsers() {
		Iterable<Paciente> users = dlo.findAllUsers();
		List<Paciente> list = new ArrayList<>();
		users.forEach(list::add);

		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Paciente> getUser(@PathVariable("username") String username) {
		Paciente user = dlo.findByUsername(username);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody Paciente user, UriComponentsBuilder ucBuilder) {
		if (dlo.isUserExist(user)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		dlo.saveUser(user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/paciente/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Paciente> deleteUser(@PathVariable long id, @RequestBody Paciente user) {
		if (dlo.findById(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		dlo.updateUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Paciente> deleteUser(@PathVariable("id") long id) {
		Paciente user = dlo.findById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		dlo.deleteUserById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<Paciente> deleteAllUsers() {

		dlo.deleteAllUsers();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
