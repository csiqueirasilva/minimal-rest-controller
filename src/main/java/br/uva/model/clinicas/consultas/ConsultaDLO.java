/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.consultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author csiqueira
 */
@Service
public class ConsultaDLO {
	
	@Autowired
	private ConsultaDAO dao;

	public Consulta findById(Long id) {
		return dao.findOne(id);
	}

	public void updateUser(Consulta user) {
		saveUser(user);
	}

	public void deleteUserById(Long id) {
		dao.delete(id);
	}

	public void deleteAllUsers() {
		dao.deleteAll();
	}

	public Iterable<Consulta> findAllUsers() {
		return dao.findAll();
	}

	@Transactional
	public void saveUser(Consulta user) {
		try {
			dao.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
