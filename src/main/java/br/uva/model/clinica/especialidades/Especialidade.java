package br.uva.model.clinica.especialidades;

import br.uva.model.clinicas.ClinicaMedica;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author csiqueira
 */
@Entity
public class Especialidade implements Serializable, Comparable<Especialidade> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false)
	private String nome;

	@ManyToMany(mappedBy = "especialidades")
	@JsonIgnore
	private List<ClinicaMedica> clinicas;

	public List<ClinicaMedica> getClinicas() {
		return clinicas;
	}

	public void setClinicas(List<ClinicaMedica> clinicas) {
		this.clinicas = clinicas;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int compareTo(Especialidade o) {
		return Objects.equals(o.id, this.id) ? 0 : this.nome.compareTo(o.nome);
	}

}
