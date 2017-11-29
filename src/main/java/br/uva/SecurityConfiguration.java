package br.uva;

import javax.sql.DataSource;

import org.apache.logging.log4j.core.config.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

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
		http
				.httpBasic().and()
				.authorizeRequests()
				.antMatchers("/homeadmin.html","/listarusuario.html","/editarusuario.html").hasAnyAuthority("ADMIN")
				.antMatchers("/homeclinica.html", "/form/consulta.html").hasAnyAuthority("CLINICA,ADMIN")
				.antMatchers("/homemedico.html").hasAnyAuthority("MEDICO,ADMIN")
				.antMatchers("/homepaciente.html", "/listarconsulta.html").hasAnyAuthority("PACIENTE,ADMIN")
				.regexMatchers("/fisica/(\\d+)").hasAuthority("ADMIN")
				.regexMatchers("/paciente/(\\d+)").hasAnyAuthority("PACIENTE,ADMIN")
				.regexMatchers("/medico/(\\d+)").hasAnyAuthority("MEDICO,ADMIN")
				.regexMatchers("/clinica/(\\d+)").hasAnyAuthority("CLINICA,ADMIN")
				.antMatchers("/console/**", "/login").permitAll()
				.and()
				.csrf().disable()
				.headers().frameOptions().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
				.ignoring()
				.antMatchers("/resources/**", "/static/**");
	}

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
				jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}
}
