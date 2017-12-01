package br.uva.model.clinicas.exames;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author csiqueira
 */
@Repository
public interface ExameMedicoDAO extends CrudRepository<ExameMedico, Long> {
}
