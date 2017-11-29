/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.services.controllers;

import br.uva.model.clinica.especialidades.Especialidade;
import br.uva.model.clinica.especialidades.EspecialidadeDLO;
import br.uva.model.clinica.medicos.MedicoClinica;
import br.uva.model.clinica.medicos.MedicoClinicaDLO;
import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.ClinicaMedicaDLO;
import br.uva.model.clinicas.TipoAtendimento;
import br.uva.model.clinicas.exames.ExameMedico;
import br.uva.model.clinicas.exames.ExameMedicoDLO;
import br.uva.model.clinicas.pacientes.Paciente;
import br.uva.model.clinicas.pacientes.PacienteDLO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author csiqueira
 */
@RestController
@RequestMapping("/json")
public class APIController {

	@Autowired
	private ClinicaMedicaDLO clinicaDLO;

	@Autowired
	private ExameMedicoDLO exameMedicoDLO;

	@Autowired
	private EspecialidadeDLO especialidadeDLO;

	@Autowired
	private MedicoClinicaDLO medicoClinicaDLO;
	
	@Autowired
	private PacienteDLO pacienteDLO;

	@RequestMapping("/exames")
	public Iterable<ExameMedico> exames() {
		return exameMedicoDLO.findAll();
	}
	
	@RequestMapping("/medicos")
	public Iterable<MedicoClinica> medicos() {
		return medicoClinicaDLO.findAll();
	}
	
	@RequestMapping("/especialidades")
	public Iterable<Especialidade> especialidades() {
		return especialidadeDLO.findAll();
	}
	
	@RequestMapping("/pacientes")
	public Iterable<Paciente> pacientes() {
		return pacienteDLO.findAllUsers();
	}
	
	@RequestMapping("/clinica/{id}")
	public ClinicaMedica obterClinica(@PathVariable Long id) {
		return clinicaDLO.obterClinica(id);
	}
	
	@RequestMapping(value = {"/busca/{query}/{pageNumber}", "/busca/{query}", "/busca/{query}/{pageNumber}/{type}"})
	public Iterable<ClinicaMedica> busca(@PathVariable(required = false) Integer pageNumber, @PathVariable(required = false) String query, @PathVariable(required = false) String type) {
		
		Iterable<ClinicaMedica> ret = null;

		if (query == null) {
			query = "";
		}
		
		if (pageNumber == null) {
			pageNumber = 1;
		}
		
		TipoAtendimento tp = null;
		
		try {
			tp = TipoAtendimento.valueOf(type);
		}	catch (Exception e) {
		}
		
		if(tp == null) {
			ret = clinicaDLO.getBuscaClinica(query, pageNumber);
		} else {
			ret = clinicaDLO.getBuscaClinica(query, pageNumber, tp);
		}
		
		return ret;
	}
}
