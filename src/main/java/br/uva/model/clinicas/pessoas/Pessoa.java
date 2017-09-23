/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.pessoas;

import br.uva.model.user.Usuario;
import br.uva.tools.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author csiqueira
 */
@MappedSuperclass
public abstract class Pessoa extends Usuario {

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String telefone;
	
	@Column(nullable = false)
	private String logradouro;
	
	@Column
	private int numend;
	
	@Column(nullable = false)
	private String cep;
	
	@Column(nullable = false)
	private String cidade;
	
	@Column(nullable = false)
	private String estado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumend() {
		return numend;
	}

	public void setNumend(int numend) {
		this.numend = numend;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
