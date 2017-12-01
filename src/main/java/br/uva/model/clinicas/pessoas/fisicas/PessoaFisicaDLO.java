package br.uva.model.clinicas.pessoas.fisicas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaFisicaDLO {

	@Autowired
	private PessoaFisicaDAO dao;

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

}
