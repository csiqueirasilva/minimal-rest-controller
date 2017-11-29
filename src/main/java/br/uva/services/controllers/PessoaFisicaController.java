/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.services.controllers;

import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.pessoas.fisicas.PessoaFisica;
import br.uva.model.clinicas.pessoas.fisicas.PessoaFisicaDLO;
import br.uva.model.user.UsuarioDLO;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
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
@RequestMapping("/fisica")
public class PessoaFisicaController {

	@Autowired
	private PessoaFisicaDLO dlo;
	
	@Autowired
	private UsuarioDLO usuarioDLO;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody PessoaFisica fisica, UriComponentsBuilder ucBuilder) {
		usuarioDLO.saveUser(fisica);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUser(@PathVariable long id, @RequestBody PessoaFisica user) {
		if (dlo.findById(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		usuarioDLO.saveUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
