package br.uva.services.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.uva.model.clinicas.pessoas.fisicas.PessoaFisica;
import br.uva.model.user.Usuario;

import java.security.Principal;

@RestController
public class LoginController {
	
	@RequestMapping("/login")
	public Principal user(Principal user) {
		return user;
	}
}