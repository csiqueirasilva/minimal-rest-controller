/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.services.controllers;

import br.uva.model.clinica.medicos.MedicoClinica;
import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.ClinicaMedicaDLO;
import br.uva.model.clinicas.pacientes.Paciente;
import br.uva.model.user.UsuarioDLO;
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

/**
 *
 * @author csiqueira
 */
@RestController
@RequestMapping("/clinica")
public class ClinicaController {

	@Autowired
	private ClinicaMedicaDLO dlo;
	
	@Autowired
	private UsuarioDLO usuarioDLO;


	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody ClinicaMedica consulta, UriComponentsBuilder ucBuilder) {
		dlo.saveClinica(consulta);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateClinica(@PathVariable long id, @RequestBody ClinicaMedica user) {
		if (dlo.obterClinica(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		dlo.saveClinica(user);
		return new ResponseEntity<>(HttpStatus.OK);
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
