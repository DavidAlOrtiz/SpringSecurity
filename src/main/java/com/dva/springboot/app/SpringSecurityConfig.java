package com.dva.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dva.springboot.app.auth.handlesSucces.LoginHandleSucces;

@Configuration
//@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig  {
	
	@Autowired
	private LoginHandleSucces loginHandle; 
	
	@Autowired
	private BCryptPasswordEncoder  passwordEncoderC;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService usuerDetail;
	
//	@Bean
//	private static BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
//		PasswordEncoder encoder = passwordEncoder();
//		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
//		
//		
//		builder.inMemoryAuthentication()
//		.withUser(users.username("admin").password("1234").roles("ADMIN", "USER"))
//		.withUser(users.username("david").password("1234").roles("USER"));
//		
//	}
	
//	@Bean
//	protected SecurityFilterChain configure(HttpSecurity  http) throws Exception {
//		http.authorizeRequests().antMatchers()
//	}
//	
	 @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  
         http.authorizeRequests().requestMatchers("/", "/css/**", "/js/**", "/images/**", "/usuario/", "/loginA").permitAll()
                 .requestMatchers("/usuario/ver/**").hasAnyRole("USER")
                 .requestMatchers("/uploads/**").hasAnyRole("USER")
                 //.requestMatchers("/usuario/form/**").hasAnyRole("ADMIN")
                 //.requestMatchers("/usuario/eliminar/**").hasAnyRole("ADMIN")
                 .requestMatchers("/factura/**").hasAnyRole("ADMIN")
                 .and()
                 	.formLogin().loginPage("/login")
                 	.successHandler(loginHandle)
                 	.permitAll()
                 .and()
                 .logout().permitAll()
                 .and().exceptionHandling().accessDeniedPage("/error_403");
//                 .anyRequest().authenticated()
//                 .and()
//                 .formLogin().permitAll()
//                 .and()
//                 .logout().permitAll();
  
         return http.build();
     }
	 
	 

		
		
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
			
//			PasswordEncoder passwordEncoder = passwordEncoderC;
//			UserBuilder users = User.builder().passwordEncoder(passwordEncoder::encode);
//			
//			builder.inMemoryAuthentication()
//			.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
//			.withUser(users.username("juan").password("juan").roles("USER"));
			
//			builder.jdbcAuthentication()
//			.dataSource(dataSource)
//			.usersByUsernameQuery("select username, password, enabled from users where username=?")
//			.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u"
//					+ " on (a.user_id = u.id)  where u.username=? ");
			
			builder.userDetailsService(usuerDetail)
			.passwordEncoder(passwordEncoderC);
		}
	 
	 
	 
//	 @Bean 
//	 public SecurityFilterChain securityChain(HttpSecurity security) throws Exception {
//		 security.authorizeHttpRequests().requestMatchers("/login").permitAll()
//		 .requestMatchers("/login").hasAnyRole("ADMIN")
//		 	.and()
//		 	.formLogin().loginPage("loging")
//		 		.permitAll()
//		 	.and()
//		 	.logout().permitAll()
//		 ;
//		 return security.build();
//	 }
//	 
	  
	 
//	 @Bean 
//	 public SecurityFilterChain filterChanin2(HttpSecurity http) throws Exception{
//		 http.authorizeHttpRequests().requestMatchers("/", "/css/**", "/js/**", "/images/**", "/usuario/" ).permitAll()
//		 .requestMatchers("/usuario/ver/**").hasAnyRole("USER")
//		 .requestMatchers("/uploads/**").hasAnyRole("USER")
//		 .requestMatchers("/usuario/form/**").hasAnyRole("ADMIN")
//		 .requestMatchers("/usuario/eliminar/**").hasAnyRole("ADMIN")
//		 .requestMatchers("/factura/**").hasAnyRole("ADMIN");
//		 return http.build();
//	 }
	
	
}
