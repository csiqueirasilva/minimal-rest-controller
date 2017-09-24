/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.exames;

import br.uva.tools.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author csiqueira
 */
@Entity
public class ExameRealizado implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date dataRealizacao;

	@ManyToOne
	private ExameMedico exame;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public ExameMedico getExame() {
		return exame;
	}

	public void setExame(ExameMedico exame) {
		this.exame = exame;
	}
	
}
