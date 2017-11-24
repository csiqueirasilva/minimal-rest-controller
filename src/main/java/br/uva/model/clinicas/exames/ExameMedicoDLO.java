/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinicas.exames;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;

/**
 *
 * @author csiqueira
 */
@Service
public class ExameMedicoDLO {

	@Autowired
	private ExameMedicoDAO dao;

	public Iterable<ExameMedico> findAll() {
		return dao.findAll();
	}

	public void generateTestData(String[] listaNomes) {
		TreeSet<String> set = new TreeSet<String>();

		for (int i = 0; i < 50; i++) {
			String nome = listaNomes[(int) (Math.random() * listaNomes.length)];
			set.add(nome);
		}

		List<ExameMedico> exames = new ArrayList<>();

		String nome;
		while ((nome = set.pollFirst()) != null) {
			ExameMedico em = new ExameMedico();
			String nomeExame = "Exame " + nome;
			em.setNome(nomeExame);
			em.setInformacoesTecnicas("Segredo do " + nome);
			exames.add(em);
		}

		dao.save(exames);
	}
	
	public ExameMedico getRandom() {
		Iterable<ExameMedico> all = dao.findAll();
		int position = (int) (Iterables.size(all) * Math.random());
		ExameMedico ret = Iterables.get(all, position);
		return ret;
	}
}