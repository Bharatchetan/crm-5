package com.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * Step:3 
 * 1. Provide the Implementation of User Details {Interface} implement
 * Class Name CustomUserDetails.[To Login with Database UserName or email,
 * UserDetails interface having usefull Method which help to do so.] 2. Provide
 * the Implementation of UserDetailsService {Interface} Implement Class
 * UserDetailsServiceImpl. [UserDetailsServiceImpl provide useful services to
 * hold and get database user with DAO ] 3. Write Security Configuration class
 * with all Configuration, A simple class that extends
 * WebSecurityConfigurationAdapter. [To initialised Security and provide the our
 * User Name Password Login Authentication.] 
 * 3 Bean require for configure Security UserDetailsService,BCryptPasswordEncoder,DaoAuthenticationProvider
 * Overide 2 Methods configure(HttpSecurity http),configure(AuthenticationManagerBuilder auth)
 */

@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/user/**").hasRole("USER")
				.antMatchers("/**").permitAll().and().formLogin()
				.loginPage("/signin")
//				.loginProcessingUrl("/dologin")
				.defaultSuccessUrl("/user/index")
//				.failureUrl("/fail-login")
				.and().csrf().disable();
		/*
		 * Several methods that we can use to configure the Behavior of the form login:
		 * loginPage()-the custom login page
		 * loginProcessingUrl()-the url to submit the username and password to 
		 * defaultSuccessUrl()- the landing page after a successful login
		 * failureUrl- the landing page after an unsuccessful login
		 */
	}

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServicesImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
//*********************************************
	
//	  @Bean
//	    public DataSource dataSource() {
//	        return new EmbeddedDatabaseBuilder()
//	            .setType(EmbeddedDatabaseType.H2)
//	            .build();
//	    }
//
//	    @Override
//	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	        UserDetails user = User.withDefaultPasswordEncoder()
//	            .username("user")
//	            .password("password")
//	            .roles("USER")
//	            .build();
//	        auth.jdbcAuthentication()
//	            .withDefaultSchema()
//	            .dataSource(dataSource())
//	            .withUser(user);
//	    }
	
	
	
	
//    @Bean
//    public DataSource dataSource(){
//        System.out.println(driverClass+" "+ url+" "+username+" "+password);
//        DriverManagerDataSource source = new DriverManagerDataSource();
//        source.setDriverClassName(driverClass);
//        source.setUrl(url);
//        source.setUsername(username);
//        source.setPassword(password);
//        return source;
//    }
}

/*
 * // New Technique based on Component based Security Configuration by
 * SecurityFilterChain,AuthenticationManager, // DaoAuthenticationProvider Not
 * Implemented yet. Hence 3.1.2 to 2.7.2 convert the Version of Spring Security
 * 
 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
 * Exception { // http.authorizeHttpRequests((authz) ->
 * authz.anyRequest().authenticated()).httpBasic(withDefaults()); http
 * .csrf().disable() .authorizeHttpRequests()
 * .antMatchers("/admin/**").hasRole("USER")
 * .antMatchers("/user/**").hasRole("USER") .antMatchers("/**").permitAll()
 * .and() .formLogin(); http.authenticationProvider(authenticationProvider());
 * return http.build(); }
 * 
 * 
 * 
 * @Bean public AuthenticationManager
 * authenticationManagerBean(AuthenticationConfiguration configuration) throws
 * Exception {
 * 
 * return configuration.getAuthenticationManager(); }
 * 
 * // * 3 Bean require for configure Security
 * UserDetailsService,BCryptPasswordEncoder
 * 
 * @Bean public UserDetailsService getUserDetailsService() { return new
 * UserDetailsServicesImpl(); }
 * 
 * @Bean public BCryptPasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); }
 * 
 * @Bean public DaoAuthenticationProvider authenticationProvider() {
 * DaoAuthenticationProvider daoAuthenticationProvider = new
 * DaoAuthenticationProvider();
 * daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService())
 * ; //
 * daoAuthenticationProvider.setUserDetailsService(this.UserDetailsServicesImpl(
 * )); daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); return
 * daoAuthenticationProvider; }
 * 
 * }
 */
