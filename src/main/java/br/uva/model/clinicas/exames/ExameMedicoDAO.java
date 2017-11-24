/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.exames;

import br.uva.model.clinicas.consultas.Consulta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author csiqueira
 */
@Repository
public interface ExameMedicoDAO extends CrudRepository<ExameMedico, Long> {
}
