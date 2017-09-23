package br.uva.model.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUsuarioDAO extends CrudRepository<RoleUsuario, Long> {
	RoleUsuario findByRole(String role);
}
