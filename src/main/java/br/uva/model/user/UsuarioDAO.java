package br.uva.model.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, Long> {
	 Usuario findByUsername(String username);
	 Usuario findByEmail(String email);
}