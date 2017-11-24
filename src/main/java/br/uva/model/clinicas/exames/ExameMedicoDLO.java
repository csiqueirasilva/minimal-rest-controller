/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.exames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class ExameMedicoDLO {
	@Autowired
	private ExameMedicoDAO dao;
	
	public Iterable<ExameMedico> findAll () {
		return dao.findAll();
	}
}
