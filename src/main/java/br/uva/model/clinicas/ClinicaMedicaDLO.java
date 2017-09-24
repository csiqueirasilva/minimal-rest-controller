package br.uva.model.clinicas;

import br.uva.model.clinicas.exames.ExameMedico;
import br.uva.model.clinica.medicos.MedicoClinica;
import br.uva.model.clinica.especialidades.Especialidade;
import br.uva.model.clinica.buscas.BuscaDLO;
import br.uva.model.clinica.especialidades.EspecialidadeDLO;
import br.uva.model.user.RoleUsuario;
import br.uva.model.user.RoleUsuarioDAO;
import br.uva.model.user.UsuarioDLO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author csiqueira
 */
@Service
public class ClinicaMedicaDLO {

	private final static int PAGE_SIZE = 10;

	private final static int RNG_TEST_NUMBER = 26;

	private final static String[] LISTA_NOMES = {"Michael", "Jackson", "Golias", "Almirante", "Nacional", "Japones", "Coreano", "Chinesa", "Arte Milenar", "Jacinto", "Leite", "Milenar", "Teste", "Bangu", "Alameda", "Caxias", "Leblon", "Rural", "Economica", "Bom Jesus", "Santo Milagre", "De Santos", "Últimas Horas", "Maria", "Jardim", "Macarena", "Contoso", "Microsoft", "Delphi", "Debian", "Levanta até Defunto", "Coisas da Vida"};

	@Autowired
	private ClinicaMedicaDAO dao;

	@Autowired
	private BuscaDLO buscaDLO;

	@Autowired
	private UsuarioDLO usuarioDLO;

	@Autowired
	private EspecialidadeDLO especialidadeDLO;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private RoleUsuarioDAO roleDAO;

	private Page<ClinicaMedica> busca(String query, TipoAtendimento tipo, Integer pageNumber) {
		PageRequest req = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "nome");
		Page<ClinicaMedica> ret = null;
		String uuid = buscaDLO.criar(query);
		Collection<TipoAtendimento> tipos = new ArrayList<TipoAtendimento>();

		if (tipo != null) {
			tipos.add(tipo);
		} else {
			tipos.add(TipoAtendimento.GRATUITO);
			tipos.add(TipoAtendimento.PRIVADO);
		}

		try {
			ret = dao.busca(uuid, tipos, req);
		} catch (Exception e) {
		}

		buscaDLO.excluir(uuid);
		return ret;
	}

	public Page<ClinicaMedica> getBuscaClinica(String query, Integer pageNumber) {
		return busca(query, null, pageNumber);
	}

	public Page<ClinicaMedica> getBuscaClinica(String query, Integer pageNumber, TipoAtendimento tipo) {
		return busca(query, tipo, pageNumber);
	}

	private String nomeAleatorio() {
		return LISTA_NOMES[(int) (Math.random() * LISTA_NOMES.length)];
	}

	private Integer intAleatorio(Integer n) {
		return (int) (Math.random() * n);
	}

	private boolean bool() {
		return intAleatorio(2) == 1;
	}

	@Transactional
	public void criarDadosTeste() {

		int max = RNG_TEST_NUMBER;
		
		RoleUsuario roleMedico = roleDAO.findByRole("MEDICO");
		RoleUsuario roleClinica = roleDAO.findByRole("CLINICA");
		
		for (int i = 0; i < max; i++) {
			ClinicaMedica cm = new ClinicaMedica();

			cm.setNome("Clínica " + nomeDuploAleatorio());
			
			String usernameClinica = cm.getNome().toLowerCase().replaceAll("[^a-z]+", "");
			
			cm.setUsername(usernameClinica + (Math.random() * 1000));
			cm.setEmail(cm.getUsername() + "@" + usernameClinica + ".com");
			Set<RoleUsuario> rolesClinica = new TreeSet<>();
			rolesClinica.add(roleClinica);
			cm.setRoles(rolesClinica);
			cm.setActive(true);
			cm.setPassword(bCryptPasswordEncoder.encode("123456"));

			cm.setTelefone("1111-1111");
			cm.setLogradouro("Rua " + nomeAleatorio());
			cm.setNumend(intAleatorio(1000));
			cm.setCep("12345-123");
			cm.setEstado("RJ");
			cm.setCidade("Rio de Janeiro");

			cm.setCnpj("33555921000170");
			
			cm.setEmailDeContato("contato@" + usernameClinica + ".com");
			cm.setTipoAtendimento(bool() ? TipoAtendimento.GRATUITO : TipoAtendimento.PRIVADO);

			if (bool()) {
				ExameMedico em = new ExameMedico();
				String nomeExame = "Exame " + nomeAleatorio();
				em.setNome(nomeExame);
				em.setInformacoesTecnicas("Segredo do " + nomeExame);
				cm.getExames().add(em);
			}

			int nMedicos = 1 + intAleatorio(3);
			for (int j = 0; j < nMedicos; j++) {
				MedicoClinica mc = new MedicoClinica();
				String nomeMedico = nomeDuploAleatorio();
				mc.setNome(nomeMedico);
				mc.setCRM(UUID.randomUUID().toString());
				mc.setTitulo(bool() ? "Dr." : "Professor Doutor");
				mc.setCelular("12345-4444");
				mc.setCep("12345-123");
				mc.setEstado("RJ");
				mc.setCidade("Rio de Janeiro");
				mc.setCpf("11111111111");
				try {
					mc.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("10/10/1970"));
				} catch (ParseException ex) {
					Logger.getLogger(ClinicaMedicaDLO.class.getName()).log(Level.SEVERE, null, ex);
				}

				mc.setLogradouro("Rua Almirante Dado de Teste");
				mc.setNumend(123);
				mc.setPassword(bCryptPasswordEncoder.encode("123456"));
				Set<RoleUsuario> roles = new TreeSet<>();
				roles.add(roleMedico);
				mc.setRoles(roles);
				mc.setActive(true);
				mc.setSexo(Math.random() > 0.5 ? "F" : "M");
				mc.setTelefone("1234-4321");

				String username = nomeMedico.toLowerCase().replaceAll("[^a-z]", "") + (Math.random() * 1000);
				mc.setUsername(username);
				mc.setEmail(username + "@" + cm.getNome().toLowerCase().replaceAll("[^a-z]+", "") + ".com");

				cm.getMedicoClinica().add(mc);
			}

			int nEspecialidades = 1 + intAleatorio(4);

			Iterable<Especialidade> findAll = especialidadeDLO.findAll();

			List<Especialidade> especialidades = new ArrayList<>();
			findAll.forEach(especialidades::add); // JDK 8

			for (int j = 0; j < nEspecialidades; j++) {
				cm.getEspecialidades().add(especialidades.get(intAleatorio(especialidades.size())));
			}

			int nTelefones = 2 + intAleatorio(3);

			for (int j = 0; j < nTelefones; j++) {
				String tel = "";
				for (int k = 0; k < 10; k++) {
					tel += intAleatorio(10);
				}
				cm.getTelefonesDeContato().add(tel);
			}

			dao.save(cm);
		}

	}

	public ClinicaMedica obterClinica(Long id) {
		ClinicaMedica cm = null;
		try {
			cm = dao.findOne(id);
		} catch (Exception e) {
		}
		return cm;
	}

	public void saveClinica(ClinicaMedica clinica) {
		dao.save(clinica);
	}
	
	private String nomeDuploAleatorio() {
		String nome1 = nomeAleatorio();
		String nome2;
		do {
			nome2 = nomeAleatorio();
		} while (nome2.equals(nome1));
		return nome1 + " " + nome2;
	}
}
