package br.uva.model.clinica.medicos;

import br.uva.model.user.UsuarioDLO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicoClinicaDLO {

	@Autowired
	private MedicoClinicaDAO userRepository;
	
	@Autowired
	private UsuarioDLO usuarioDLO;
	
	public Iterable<MedicoClinica> findAll() {
		return userRepository.findAll();
	}
	
	public MedicoClinica findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
    public MedicoClinica findById(Long id) {
        return userRepository.findOne(id);
    }
	
    public void updateUser(MedicoClinica user){
        saveUser(user);
    }
	
    public void deleteUserById(Long id){
        userRepository.delete(id);
    }
	
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
	
    public Iterable<MedicoClinica> findAllUsers(){
        return userRepository.findAll();
    }
	
    public boolean isUserExist(MedicoClinica user) {
        return findByUsername(user.getUsername()) != null;
    }
	
	@Transactional
	public void saveUser(MedicoClinica user) {
		try {
			usuarioDLO.addRoleToUserByName(user, "MEDICO");
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}