package br.uva.services.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.uva.model.clinica.medicos.MedicoClinica;
import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.ClinicaMedicaDLO;

/**
 *
 * @author csiqueira
 */
@RestController
@RequestMapping("/clinica")
public class ClinicaController {

	@Autowired
	private ClinicaMedicaDLO dlo;

	@RequestMapping(value = "/{username:.+}", method = RequestMethod.GET)
	public ResponseEntity<ClinicaMedica> getByUsername(@PathVariable("username") String username) {
		ClinicaMedica c = dlo.findByUsername(username);
		if (c == null) {
			return new ResponseEntity<ClinicaMedica>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ClinicaMedica>(c, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClinicaMedica> getById(@PathVariable("id") Long id) {
		ClinicaMedica c = dlo.obterClinica(id);
		if (c == null) {
			return new ResponseEntity<ClinicaMedica>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ClinicaMedica>(c, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody ClinicaMedica consulta, UriComponentsBuilder ucBuilder) {
		dlo.saveClinica(consulta);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateClinica(@PathVariable long id, @RequestBody ClinicaMedica user) {
		if (dlo.obterClinica(id) == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		dlo.saveClinica(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/medicos/{username:.+}", method = RequestMethod.GET)
	public Iterable<MedicoClinica> getMedicos(@PathVariable("username") String username) {

		Iterable<MedicoClinica> ret = new ArrayList<>();

		try {
			ClinicaMedica cm = dlo.findByUsername(username);
			ret = cm.getMedicos();
		} catch (Exception e) {
		}

		return ret;
	}

}
