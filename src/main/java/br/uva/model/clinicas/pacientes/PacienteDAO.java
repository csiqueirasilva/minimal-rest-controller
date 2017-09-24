/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.pacientes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author csiqueira
 */
@Repository
public interface PacienteDAO extends CrudRepository<Paciente, Long> {
	Paciente findByUsername(String username);
	Paciente findByEmail(String email);
}
