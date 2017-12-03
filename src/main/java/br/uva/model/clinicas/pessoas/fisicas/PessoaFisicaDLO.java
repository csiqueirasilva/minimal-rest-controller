package br.uva.model.clinicas.pessoas.fisicas;

import br.uva.model.user.UsuarioDLO;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaFisicaDLO {

	@Autowired
	private PessoaFisicaDAO dao;

	@Autowired
	private UsuarioDLO dlo;
	
	public PessoaFisica findByUsername(String username) {
		return dao.findByUsername(username);
	}

	public PessoaFisica findById(Long id) {
		return dao.findOne(id);
	}

	public void updateUser(PessoaFisica user) {
		dao.save(user);
	}

	public void deleteUserById(Long id) {
		dao.delete(id);
	}

	public void deleteAllUsers() {
		dao.deleteAll();
	}

	public Iterable<PessoaFisica> findAllUsers() {
		return dao.findAll();
	}

	public boolean isUserExist(PessoaFisica user) {
		return findByUsername(user.getUsername()) != null;
	}

	@Transactional
	public void criarUsuarioRoot() {
		PessoaFisica u = new PessoaFisica();
		u.setActive(true);
		u.setEmail("root@root.com");
		u.setPassword("123456");
		u.setUsername("root");
		u.setTelefone("1111111111");
		u.setCpf("111111111111");
		u.setDataNascimento(new Date());
		u.setSexo("masc");
		u.setLogradouro("Rua xyz");
		u.setNome("userroot");
		u.setCidade("RJ");
		u.setEstado("RJ");
		u.setNumend(123);
		u.setCep("22222222");
		dlo.addRoleToUserByName(u, "ADMIN");
		dao.save(u);
	}
}
