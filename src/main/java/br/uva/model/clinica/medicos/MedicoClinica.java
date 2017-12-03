package br.uva.model.clinica.medicos;

import br.uva.model.clinicas.ClinicaMedica;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import br.uva.model.clinicas.pessoas.fisicas.PessoaFisica;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author csiqueira
 */
@Entity
public class MedicoClinica extends PessoaFisica {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private Long CRM;

	@Column
	private String titulo;

	@ManyToMany(mappedBy = "medicos")
	@JsonIgnore
	private List<ClinicaMedica> clinicas;

	public List<ClinicaMedica> getClinicas() {
		return clinicas;
	}

	public void setClinicas(List<ClinicaMedica> clinicas) {
		this.clinicas = clinicas;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getCRM() {
		return CRM;
	}

	public void setCRM(Long CRM) {
		this.CRM = CRM;
	}

}