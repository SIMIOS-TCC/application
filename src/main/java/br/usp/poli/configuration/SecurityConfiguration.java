package br.usp.poli.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.usp.poli.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
				.antMatchers("/home").permitAll()
				.antMatchers("/").permitAll()
				
				.antMatchers("/user/**").hasAuthority("ADMIN")
				
				.anyRequest().authenticated()
				
				.and()			
					.formLogin()
					.loginPage("/login").defaultSuccessUrl("/home")
					.permitAll()
				.and()
					.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/")
					.permitAll()
				.and()
					.sessionManagement()
					.invalidSessionUrl("/login")
					.maximumSessions(1)
			        .maxSessionsPreventsLogin(true)
			        .sessionRegistry(sessionRegistry());
		
		http.exceptionHandling().accessDeniedPage("/acess-denied");
		
		//http.csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
 
    }
	
	@Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

	 /* CRIPTOGRAFANDO A SENHA PARA TESTE
	public static void main(String[] args) {
		String password = new BCryptPasswordEncoder().encode("123456");
		return;
	}*/
	
}