package br.uva.model.clinicas.exames;

import br.uva.model.clinicas.ClinicaMedica;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;

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
public class ExameMedico implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column
	private String informacoesTecnicas;

	@ManyToMany(mappedBy = "exames")
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

	public String getInformacoesTecnicas() {
		return informacoesTecnicas;
	}

	public void setInformacoesTecnicas(String informacoesTecnicas) {
		this.informacoesTecnicas = informacoesTecnicas;
	}

}
