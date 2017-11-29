/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.consultas;


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
public interface ConsultaDAO extends CrudRepository<Consulta, Long> {

	@Query("SELECT c FROM Consulta c, Busca b WHERE c.paciente.id=:id AND c.clinica.id=:cid AND b.uuid=:uuid")
	public Page<Consulta> busca(@Param("id") Long id, @Param("cid") Long cid, @Param("uuid") String uuid, Pageable req);
}
