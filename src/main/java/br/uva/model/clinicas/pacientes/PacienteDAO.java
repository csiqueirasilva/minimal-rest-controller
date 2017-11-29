/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.pacientes;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.TipoAtendimento;

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
