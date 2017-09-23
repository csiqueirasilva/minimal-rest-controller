package br.uva.model.user;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioDLO {

	@Autowired
	private UsuarioDAO userRepository;

	@Autowired
	private RoleUsuarioDAO roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public RoleUsuario getRole(String str) {
		RoleUsuario ru = null;
		try {
			ru = roleRepository.findByRole(str);
		} catch (Exception e) {
		}
		return ru;
	}

	public Usuario findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Usuario findById(Long id) {
		return userRepository.findOne(id);
	}

	public void updateUser(Usuario user) {
		saveUser(user);
	}

	public void deleteUserById(Long id) {
		userRepository.delete(id);
	}

	public void deleteAllUsers() {
		userRepository.deleteAll();
	}

	public Iterable<Usuario> findAllUsers() {
		return userRepository.findAll();
	}

	public boolean isUserExist(Usuario user) {
		return findByUsername(user.getUsername()) != null;
	}

	public void addRoleToUserByName(Usuario user, String roleName) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		RoleUsuario userRole = roleRepository.findByRole(roleName);
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
	}

	@Transactional
	public void saveUser(Usuario user) {
		try {
			addRoleToUserByName(user, "ADMIN");
			user = userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}