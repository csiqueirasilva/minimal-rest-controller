package br.uva.model.clinicas.consultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uva.model.clinica.buscas.BuscaDLO;

/**
 *
 * @author csiqueira
 */
@Service
public class ConsultaDLO {

	@Autowired
	BuscaDLO buscaDLO;

	@Autowired
	private ConsultaDAO dao;

	public Consulta findById(Long id) {
		return dao.findOne(id);
	}

	public void updateUser(Consulta user) {
		saveUser(user);
	}

	public void deleteUserById(Long id) {
		dao.delete(id);
	}

	public void deleteAllUsers() {
		dao.deleteAll();
	}

	public Iterable<Consulta> findAllUsers() {
		return dao.findAll();
	}

	@Transactional
	public void saveUser(Consulta user) {
		try {
			dao.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Iterable<Consulta> getBusca(String query, Long pacienteId, Long clinicaId, Integer pageNumber) {
		PageRequest req = new PageRequest(pageNumber - 1, BuscaDLO.PAGE_SIZE, Sort.Direction.ASC, "dataConsulta");
		Page<Consulta> ret = null;
		String uuid = buscaDLO.criar(query);

		try {
			ret = dao.busca(pacienteId, clinicaId, uuid, req);
		} catch (Exception e) {
		}

		buscaDLO.excluir(uuid);
		return ret;
	}
}
