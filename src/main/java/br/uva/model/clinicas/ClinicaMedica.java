package br.uva.model.clinicas;

import br.uva.model.clinicas.exames.ExameMedico;
import br.uva.model.clinicas.pacientes.Paciente;
import br.uva.model.clinica.medicos.MedicoClinica;
import br.uva.model.clinica.especialidades.Especialidade;
import br.uva.model.clinicas.pessoas.juridicas.PessoaJuridica;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author csiqueira
 */
@Entity
public class ClinicaMedica extends PessoaJuridica {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToMany
	private Set<Especialidade> especialidades;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoAtendimento tipoAtendimento;

	@ManyToMany
	private List<ExameMedico> exames;

	@ManyToMany
	private List<MedicoClinica> medicos;

	@Column
	private String emailDeContato;
	
	@ElementCollection
	private Set<String> telefonesDeContato;

	public String getEmailDeContato() {
		return emailDeContato;
	}

	public void setEmailDeContato(String emailDeContato) {
		this.emailDeContato = emailDeContato;
	}

	public Set<String> getTelefonesDeContato() {
		return telefonesDeContato;
	}

	public void setTelefonesDeContato(Set<String> telefonesDeContato) {
		this.telefonesDeContato = telefonesDeContato;
	}

	public ClinicaMedica() {
		this.especialidades = new TreeSet<Especialidade>();
		this.telefonesDeContato = new TreeSet<String>();
		this.exames = new ArrayList<ExameMedico>();
		this.medicos = new ArrayList<MedicoClinica>();
	}
	
	public Set<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public TipoAtendimento getTipoAtendimento() {
		return tipoAtendimento;
	}

	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public List<ExameMedico> getExames() {
		return exames;
	}

	public void setExames(List<ExameMedico> exames) {
		this.exames = exames;
	}

	public List<MedicoClinica> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<MedicoClinica> medicos) {
		this.medicos = medicos;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
