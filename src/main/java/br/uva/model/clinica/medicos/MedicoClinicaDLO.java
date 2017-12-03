package br.uva.model.clinica.medicos;

import br.uva.model.clinicas.ClinicaMedicaDLO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uva.model.user.UsuarioDLO;

@Service
public class MedicoClinicaDLO {

	@Autowired
	private MedicoClinicaDAO dao;

	@Autowired
	private ClinicaMedicaDLO dlo;
	
	@Autowired
	private UsuarioDLO usuarioDLO;

	public Iterable<MedicoClinica> findAll() {
		return dao.findAll();
	}

	public MedicoClinica findByUsername(String username) {
		return dao.findByUsername(username);
	}

	public MedicoClinica findById(Long id) {
		return dao.findOne(id);
	}

	public void updateUser(MedicoClinica user) {
		saveUser(user);
	}

	@Transactional
	public void deleteUserById(Long id) {
		MedicoClinica md = dao.findOne(id);
		if(md != null) {
			md.getClinicas().forEach((cm) -> {
				dlo.removeMedico(md, cm);
			});
			dao.delete(id);
		}
	}

	public void deleteAllUsers() {
		dao.deleteAll();
	}

	public Iterable<MedicoClinica> findAllUsers() {
		return dao.findAll();
	}

	public boolean isUserExist(MedicoClinica user) {
		return findByUsername(user.getUsername()) != null;
	}

	@Transactional
	public void saveUser(MedicoClinica user) {
		try {
			usuarioDLO.addRoleToUserByName(user, "MEDICO");
			dao.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}