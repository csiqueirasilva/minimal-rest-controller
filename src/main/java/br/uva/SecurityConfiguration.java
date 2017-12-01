package br.uva;

import javax.sql.DataSource;

import org.apache.logging.log4j.core.config.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests()
				.antMatchers("/homeadmin.html", "/listarusuario.html", "/editarusuario.html").hasAnyAuthority("ADMIN")
				.antMatchers("/homeclinica.html", "/form/consulta.html").hasAnyAuthority("CLINICA,ADMIN")
				.antMatchers("/homemedico.html").hasAnyAuthority("MEDICO,ADMIN")
				.antMatchers("/homepaciente.html", "/listarconsulta.html").hasAnyAuthority("PACIENTE,ADMIN")
				.antMatchers("/fisica/.+").hasAuthority("ADMIN")
				.antMatchers("/paciente/.+").hasAnyAuthority("PACIENTE,ADMIN")
				.antMatchers("/medico/.+").hasAnyAuthority("MEDICO,ADMIN")
				.antMatchers("/clinica/.+").hasAnyAuthority("CLINICA,ADMIN")
				.antMatchers("/login","/","/index.html","/main.html","/main.css","/json/**", 
						"/registro.html", "/busca.html", "/clinica.html", "/form/**",
						"/fisica/", "/paciente/", "/medico/", "/clinica/").permitAll()
				.anyRequest().authenticated()
				.and()
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/js/**", "/fonts/**", "/dist/**", "/console/**");
	}

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}
}
