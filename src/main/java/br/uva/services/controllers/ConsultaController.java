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

import br.uva.model.clinicas.consultas.Consulta;
import br.uva.model.clinicas.consultas.ConsultaDLO;
import br.uva.model.clinicas.pacientes.Paciente;
import br.uva.model.clinicas.pacientes.PacienteDLO;

/**
 *
 * @author csiqueira
 */
@RestController
@RequestMapping("/consulta")
public class ConsultaController {

	@Autowired
	private ConsultaDLO dlo;

	@Autowired
	private PacienteDLO PacienteDLO;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Void> createConsulta(@RequestBody Consulta consulta, UriComponentsBuilder ucBuilder) {
		dlo.saveUser(consulta);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = { "/busca/{username:.+}/{clinicaid}/{query}/{pageNumber}",
			"/busca/{username:.+}/{clinicaid}/{query}", "/busca/{username:.+}/{clinicaid}" })
	public Iterable<Consulta> busca(@PathVariable("username") String username, @PathVariable("clinicaid") Long id,
			@PathVariable(required = false) String query, @PathVariable(required = false) Integer pageNumber) {
		Iterable<Consulta> ret = null;

		Paciente p = PacienteDLO.findByUsername(username);

		if (query == null) {
			query = "";
		}

		if (pageNumber == null) {
			pageNumber = 1;
		}
		ret = dlo.getBusca(query, p.getId(), id, pageNumber);

		return ret;
	}

}
