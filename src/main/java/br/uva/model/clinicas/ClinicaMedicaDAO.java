package br.uva.model.clinicas;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.uva.model.clinica.especialidades.Especialidade;
import br.uva.model.clinica.medicos.MedicoClinica;

/**
 *
 * @author csiqueira
 */
@Repository
public interface ClinicaMedicaDAO extends CrudRepository<ClinicaMedica, Long> {
	Page<ClinicaMedica> findDistinctByLogradouroInIgnoreCaseOrNomeInIgnoreCaseOrEspecialidadesIn(
			Collection<String> logradouro, Collection<String> busca, Collection<Especialidade> especialidade,
			Pageable pr);

	Page<ClinicaMedica> findDistinctByLogradouroInIgnoreCaseOrNomeInIgnoreCaseOrEspecialidadesInAndTipoAtendimento(
			Collection<String> logradouro, Collection<String> busca, Collection<Especialidade> especialidade,
			TipoAtendimento tipoAtendimento, Pageable pr);

	ClinicaMedica findByUsername(String username);

	ClinicaMedica findByEmail(String email);

	@Query("SELECT m FROM ClinicaMedica c INNER JOIN c.medicos m WHERE c.id=:id")
	public Iterable<MedicoClinica> findAllMedicos(@Param("id") Long id);

	@Query("SELECT DISTINCT c FROM ClinicaMedica c, Busca b INNER JOIN c.especialidades e WHERE c.tipoAtendimento IN :tipos AND b.uuid = :uuid AND (LOWER(c.logradouro) LIKE CONCAT('%', LOWER(b.termo), '%') OR LOWER(c.nome) LIKE CONCAT('%', LOWER(b.termo), '%') OR LOWER(e.nome) LIKE CONCAT('%', LOWER(b.termo), '%'))")
	public Page<ClinicaMedica> busca(@Param("uuid") String uuid, @Param("tipos") Collection<TipoAtendimento> tipos,
			Pageable req);
}