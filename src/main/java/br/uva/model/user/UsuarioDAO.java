package br.uva.model.user;

import br.uva.model.clinicas.ClinicaMedica;
import br.uva.model.clinicas.TipoAtendimento;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    Usuario findByEmail(String email);

    @Query("SELECT DISTINCT c FROM Usuario c, Busca b INNER JOIN c.especialidades e WHERE b.uuid = :uuid AND (LOWER(c.username) LIKE CONCAT('%', LOWER(b.termo), '%') OR LOWER(c.email) LIKE CONCAT('%', LOWER(b.termo), '%'))")
    public Page<Usuario> busca(@Param("uuid") String uuid, Pageable req);

}
