/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.pacientes;

import br.uva.model.clinica.buscas.BuscaDLO;
import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.ClinicaMedicaDLO;
import br.uva.model.user.Usuario;
import br.uva.model.user.UsuarioDLO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	BuscaDLO buscaDLO;
	
	@Autowired
	private UsuarioDLO usuarioDLO;

    @Autowired
	private ClinicaMedicaDLO clinicaDLO;
	
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
    public boolean addClinica(Long clinicaId, String pacienteUser){
    	ClinicaMedica clinica = null;
        Paciente paciente = this.findByUsername(pacienteUser);
        try {
        	clinica  = clinicaDLO.obterClinica(clinicaId);
        }
        catch(Exception e){
        	return false;
        }

      	List<ClinicaMedica> clinicas = paciente.getClinicas();
      	if(clinicas.contains(clinica)) {
      		return false;
      	}
        clinicas.add(clinica);
        return true;
    }
    
    @Transactional
    public boolean deleteClinica(Long clinicaId, String pacienteUser){
    	ClinicaMedica clinica = null;
        Paciente paciente = this.findByUsername(pacienteUser);
        try {
        	clinica  = clinicaDLO.obterClinica(clinicaId);
        }
        catch(Exception e){
        	return false;
        }
        List<ClinicaMedica> clinicas = paciente.getClinicas();
      	if(clinicas.contains(clinica)) {
      		clinicas.remove(clinica);
      		return true;
      	}
        return false;
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
	
    public Iterable<ClinicaMedica> getBusca(String query, Long pacienteId, Integer pageNumber) {

        PageRequest req = new PageRequest(pageNumber - 1, BuscaDLO.PAGE_SIZE, Sort.Direction.ASC, "username");
        Page<ClinicaMedica> ret = null;
        String uuid = buscaDLO.criar(query);

        try {
            ret = dao.busca(uuid, pacienteId ,req);
        } catch (Exception e) {
        }

        buscaDLO.excluir(uuid);
        return ret;

    }
}
