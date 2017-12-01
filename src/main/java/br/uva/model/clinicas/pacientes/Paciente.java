package br.uva.model.clinicas.pacientes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.pessoas.fisicas.PessoaFisica;

/**
 *
 * @author csiqueira
 */
@Entity
public class Paciente extends PessoaFisica {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToMany
	private List<ClinicaMedica> clinicas;

	public Paciente() {
		this.clinicas = new ArrayList<ClinicaMedica>();
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
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
