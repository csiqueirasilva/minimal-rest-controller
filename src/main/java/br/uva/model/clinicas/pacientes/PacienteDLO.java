/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.pacientes;

import br.uva.model.user.UsuarioDLO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author csiqueira
 */
@Service
public class PacienteDLO {
	
	@Autowired
	private PacienteDAO dao;
	
	@Autowired
	private UsuarioDLO usuarioDLO;
	
	public Paciente findByUsername(String username) {
		return dao.findByUsername(username);
	}
	
    public Paciente findById(Long id) {
        return dao.findOne(id);
    }
	
    public void updateUser(Paciente user){
        saveUser(user);
    }
	
    public void deleteUserById(Long id){
        dao.delete(id);
    }
	
    public void deleteAllUsers(){
        dao.deleteAll();
    }
	
    public Iterable<Paciente> findAllUsers(){
        return dao.findAll();
    }
	
    public boolean isUserExist(Paciente user) {
        return findByUsername(user.getUsername()) != null;
    }
	
	@Transactional
	public void saveUser(Paciente user) {
		try {
			usuarioDLO.addRoleToUserByName(user, "PACIENTE");
			dao.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
