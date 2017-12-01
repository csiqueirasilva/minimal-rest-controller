package br.uva.model.clinica.medicos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoClinicaDAO extends CrudRepository<MedicoClinica, Long> {
	MedicoClinica findByUsername(String username);

	MedicoClinica findByEmail(String email);
}