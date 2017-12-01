package br.uva.model.clinicas.pessoas.fisicas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.uva.model.clinicas.pessoas.Pessoa;
import br.uva.tools.CustomDateDeserializer;

/**
 *
 * @author csiqueira
 */
@Entity
public class PessoaFisica extends Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false)
	private String sexo;

	@Column(nullable = false)
	private String cpf;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = CustomDateDeserializer.class)

	private Date dataNascimento;

	@Column
	private String celular;

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

}
