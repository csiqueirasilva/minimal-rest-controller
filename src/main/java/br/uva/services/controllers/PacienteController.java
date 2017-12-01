package br.uva.services.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.pacientes.Paciente;
import br.uva.model.clinicas.pacientes.PacienteDLO;

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

	@RequestMapping(value = "/{username:.+}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
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

	@RequestMapping(value = "/{username:.+}/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> createMinhaClinica(@PathVariable("username") String username,
			@PathVariable("id") Long clinicaId) {

		if (!dlo.addClinica(clinicaId, username)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Paciente> updateUser(@PathVariable long id, @RequestBody Paciente user) {
		if (dlo.findById(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		dlo.saveUser(user);
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

	@RequestMapping(value = "/{username:.+}/{clinicaid}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteMinhaClinica(@PathVariable("username") String username,
			@PathVariable("clinicaid") Long clinicaid) {
		dlo.deleteClinica(clinicaid, username);
	}

	@RequestMapping(value = { "/busca/{username:.+}/{query}/{pageNumber}", "/busca/{username:.+}/{query}",
			"/busca/{username:.+}" })
	public Iterable<ClinicaMedica> busca(@PathVariable("username") String username,
			@PathVariable(required = false) String query, @PathVariable(required = false) Integer pageNumber) {

		Iterable<ClinicaMedica> ret = null;

		Paciente p = dlo.findByUsername(username);

		if (query == null) {
			query = "";
		}

		if (pageNumber == null) {
			pageNumber = 1;
		}
		ret = dlo.getBusca(query, p.getId(), pageNumber);

		return ret;
	}

}
