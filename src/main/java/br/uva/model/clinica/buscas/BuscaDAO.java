package br.uva.model.clinica.buscas;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author csiqueira
 */
@Repository
public interface BuscaDAO extends CrudRepository<Busca, Long> {
	int deleteByUuid(String uuid);
}