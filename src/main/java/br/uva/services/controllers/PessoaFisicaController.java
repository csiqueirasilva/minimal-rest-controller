package br.uva.services.controllers;

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

import br.uva.model.clinicas.pessoas.fisicas.PessoaFisica;
import br.uva.model.clinicas.pessoas.fisicas.PessoaFisicaDLO;
import br.uva.model.user.UsuarioDLO;

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
	
	@RequestMapping(value = "/{username:.+}", method = RequestMethod.GET)
	public ResponseEntity<PessoaFisica> getByUsername(@PathVariable("username") String username) {
		PessoaFisica p = dlo.findByUsername(username);
		if (p == null) {
			return new ResponseEntity<PessoaFisica>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PessoaFisica>(p, HttpStatus.OK);
	}

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
