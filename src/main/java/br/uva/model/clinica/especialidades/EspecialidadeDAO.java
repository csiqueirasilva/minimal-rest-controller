package br.uva.model.clinica.especialidades;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author csiqueira
 */
@Repository
public interface EspecialidadeDAO extends CrudRepository<Especialidade, Long> {
}