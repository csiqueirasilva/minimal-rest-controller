/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.services.controllers;

import br.uva.model.clinicas.consultas.Consulta;
import br.uva.model.clinicas.consultas.ConsultaDLO;
import br.uva.model.user.Usuario;
import br.uva.model.user.UsuarioDLO;
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
@RequestMapping("/consulta")
public class ConsultaController {

	@Autowired
	private ConsultaDLO dlo;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Void> createConsulta(@RequestBody Consulta consulta, UriComponentsBuilder ucBuilder) {
		dlo.saveUser(consulta);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

}
