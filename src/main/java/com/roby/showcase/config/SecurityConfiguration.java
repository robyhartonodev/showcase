package com.roby.showcase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.roby.showcase.service.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login-error";
	private static final String LOGIN_URL = "/login";
	private static final String LOGIN_SUCCESS_URL = "/";
	private static final String LOGOUT_SUCCESS_URL = "/login";

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// In Memory Authentication, temporary solution for data import, password :
		// password
		auth.inMemoryAuthentication().withUser("roby@showcase.com")
				.password("$2y$10$R3/Q2pw07CS57Pe5BXu0C.TEzth2NFRMooDFCgOawzlFkc4yB02gG").roles("ADMIN");

		// Authentication with CustomUserDetailsService
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Not using Spring CSRF here to be able to use plain HTML for the login page
		http.cors()

				.and().authorizeRequests()

				// Permit access to these guest URLs
				.antMatchers("/v2/api-docs", "/swagger-resources/**", "/resources/**", "/js/**", "/css/**", "/img/**",
						"/webjars/**", "/error", "/login", "/logout", "/register")
				.permitAll()

				// Allow certain requests based on role here (role restricted requests)
				// .and().authorizeRequests()

				// .antMatchers("/api/task/**").hasAnyAuthority("CaptivePortal")

				// Otherwise, allow all remaining requests only by all logged in users. (no role
				// restricted request)
				.anyRequest().authenticated()

				// Configure the login page.
				.and().formLogin().loginPage("/login").usernameParameter("email").failureUrl("/login-error")
				.defaultSuccessUrl(LOGIN_SUCCESS_URL, true).permitAll() //
				.loginProcessingUrl(LOGIN_PROCESSING_URL) //

				// logout" will log the user out by invalidating the HTTP Session, cleaning up
				// any {link rememberMe()} authentication that was configured
				.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL).permitAll()

//		// Enable basic authentication for rest end points
//		.and().httpBasic()

				// configuring the session on the server
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

				// Not using Spring CSRF here to be able to use plain HTML for the login page
				.and().csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				// Vaadin Flow static resources
				"/VAADIN/**",

				// the standard favicon URI
				"/favicon.ico",

				// the robots exclusion standard
				"/robots.txt",

				// web application manifest
				"/manifest.webmanifest", "/sw.js", "/offline-page.html",

				// icons and images
				"/icons/**", "/images/**",

				// (development mode) static resources
				"/frontend/**",

				// (development mode) webjars
				"/webjars/**",

				// (development mode) H2 debugging console
				"/h2-console/**",

				// (production mode) static resources
				"/frontend-es5/**", "/frontend-es6/**");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
