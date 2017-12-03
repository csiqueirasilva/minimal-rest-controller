package br.uva.model.user;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uva.model.clinica.buscas.BuscaDLO;
import br.uva.model.clinicas.pessoas.fisicas.PessoaFisica;
import java.util.Date;

@Service
public class UsuarioDLO {

	@Autowired
	private BuscaDLO buscaDLO;

	@Autowired
	private UsuarioDAO dao;

	@Autowired
	private RoleUsuarioDAO roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public String encodePassword (String str) {
		return bCryptPasswordEncoder.encode(str);
	}
	
	public RoleUsuario getRole(String str) {
		RoleUsuario ru = null;
		try {
			ru = roleRepository.findByRole(str);
		} catch (Exception e) {
		}
		return ru;
	}

	public Usuario findByUsername(String username) {
		return dao.findByUsername(username);
	}

	public Usuario findById(Long id) {
		return dao.findOne(id);
	}

	public void updateUser(Usuario user) {
		saveUser(user);
	}

	public void deleteUserById(Long id) {
		dao.delete(id);
	}

	public void deleteAllUsers() {
		dao.deleteAll();
	}

	public Iterable<Usuario> findAllUsers() {
		return dao.findAll();
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
			user = dao.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Iterable<Usuario> getBusca(String query, Integer pageNumber) {

		PageRequest req = new PageRequest(pageNumber - 1, BuscaDLO.PAGE_SIZE, Sort.Direction.ASC, "username");
		Page<Usuario> ret = null;
		String uuid = buscaDLO.criar(query);

		try {
			ret = dao.busca(uuid, req);
		} catch (Exception e) {
		}

		buscaDLO.excluir(uuid);
		return ret;

	}

}
