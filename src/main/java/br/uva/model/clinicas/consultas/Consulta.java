/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.consultas;

import br.uva.model.clinica.medicos.MedicoClinica;
import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.exames.ExameRealizado;
import br.uva.model.clinicas.pacientes.Paciente;
import br.uva.tools.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author csiqueira
 */
@Entity
public class Consulta implements Serializable {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Paciente paciente;

	@ManyToOne
	private MedicoClinica medico;
	
	@ManyToOne
	private ClinicaMedica clinica;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<ExameRealizado> exames;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date dataConsulta;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public MedicoClinica getMedico() {
		return medico;
	}

	public void setMedico(MedicoClinica medico) {
		this.medico = medico;
	}

	public List<ExameRealizado> getExames() {
		return exames;
	}

	public void setExames(List<ExameRealizado> exames) {
		this.exames = exames;
	}

	public Date getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public ClinicaMedica getClinica() {
		return clinica;
	}

	public void setClinica(ClinicaMedica clinica) {
		this.clinica = clinica;
	}
	
}
