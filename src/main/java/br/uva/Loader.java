package br.uva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.uva.model.clinica.especialidades.EspecialidadeDLO;
import br.uva.model.clinicas.ClinicaMedicaDLO;
import br.uva.model.clinicas.pessoas.fisicas.PessoaFisicaDLO;
import br.uva.model.user.UsuarioDLO;

/**
 *
 * @author csiqueira
 */
@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ClinicaMedicaDLO dlo;
	
	@Autowired
	private PessoaFisicaDLO dloUsuario;

	@Autowired
	private EspecialidadeDLO especialidadesDLO;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		especialidadesDLO.criarEspecialidades();
		dlo.criarDadosTeste();
		dloUsuario.criarUsuarioRoot();
	}
}
