package org.patients.web.config;

import java.util.List;
import java.util.stream.Collectors;

import org.patients.web.security.JwtAuthenticationEntryPoint;
import org.patients.web.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.google.common.collect.Lists;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	// everyone is allowed to authenticate themselves and use the Swagger docs
	private static final String[] UNRESTRICTED_ACCESS_PATHS = {
			"/api/v1/authenticate",
			"/api/v1/credential",
			"/h2-console/**",
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/**"
	};

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	public JwtRequestFilter jwtAuthenticationFilter() throws Exception {
		JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(new OrRequestMatcher(unathorizedRequestMatchers()));
		return jwtRequestFilter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// removing the default Spring Security CSRF support and disabling the
		// HttpSession creation because we are using stateless token
		// authentication instead of the default configured cookie based
		// authentication
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// disable default Spring login/logout pages
		httpSecurity.formLogin().disable();
		httpSecurity.logout().disable();

		// unrestricted access to whitelist paths
		List<RequestMatcher> unathorizedRequestMatchers = unathorizedRequestMatchers();
		httpSecurity.authorizeRequests().requestMatchers(unathorizedRequestMatchers
				.toArray(new RequestMatcher[unathorizedRequestMatchers.size()])).permitAll();

		// all other requests need to be authenticated
		httpSecurity.authorizeRequests().anyRequest().authenticated();

		httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	private List<RequestMatcher> unathorizedRequestMatchers() {
		List<RequestMatcher> matchers = Lists.newArrayList(UNRESTRICTED_ACCESS_PATHS)
				.stream()
				.map(pattern -> new AntPathRequestMatcher(pattern))
				.collect(Collectors.toList());

		matchers.add(new AntPathRequestMatcher("/api/v1/medics", HttpMethod.GET.toString()));
		matchers.add(new AntPathRequestMatcher("/api/v1/users", HttpMethod.POST.toString()));

		return matchers;
	}

}
