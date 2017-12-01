package br.uva.model.clinicas.pacientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.uva.model.clinicas.ClinicaMedica;

/**
 *
 * @author csiqueira
 */
@Repository
public interface PacienteDAO extends CrudRepository<Paciente, Long> {

	Paciente findByUsername(String username);

	Paciente findByEmail(String email);

	@Query("SELECT clinica FROM Paciente p, Busca b INNER JOIN p.clinicas clinica WHERE p.id = :id AND b.uuid = :uuid AND (LOWER(clinica.logradouro) LIKE CONCAT('%', LOWER(b.termo), '%') OR LOWER(clinica.nome) LIKE CONCAT('%', LOWER(b.termo), '%'))")
	public Page<ClinicaMedica> busca(@Param("uuid") String uuid, @Param("id") Long id, Pageable req);
}
