package br.uva.model.clinica.medicos;

import br.uva.model.clinicas.pessoas.fisicas.PessoaFisica;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author csiqueira
 */
@Entity
public class MedicoClinica extends PessoaFisica {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private Long CRM;

	@Column
	private String titulo;

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