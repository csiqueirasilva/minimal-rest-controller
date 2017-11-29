/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.pacientes;

import br.uva.model.clinica.especialidades.Especialidade;
import br.uva.model.clinica.medicos.MedicoClinica;
import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.exames.ExameMedico;
import br.uva.model.clinicas.pessoas.fisicas.PessoaFisica;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author csiqueira
 */
@Entity
public class Paciente extends PessoaFisica {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToMany
	private List<ClinicaMedica> clinicas;
	
	public Paciente() {
		this.clinicas = new ArrayList<ClinicaMedica>();
	}

	@Override	public Long getId() {
		return id;
	}
	

	public void setId(Long id) {
		this.id = id;
	}

	public List<ClinicaMedica> getClinicas() {
		return clinicas;
	}

	public void setClinicas(List<ClinicaMedica> clinicas) {
		this.clinicas = clinicas;
	}

}
