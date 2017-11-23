package br.uva.model.clinicas.pessoas.fisicas;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaDAO extends CrudRepository<PessoaFisica, Long> {

    PessoaFisica findByUsername(String username);

    PessoaFisica findByEmail(String email);

}
