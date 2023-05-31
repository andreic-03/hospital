package org.patients.web.security;

import org.patients.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class JwtRequestFilter extends OncePerRequestFilter {

	private static final String BEARER_PREFIX_REGEX = "^Bearer\\s+";

	private RequestMatcher unrestrictedRequestsMatcher;

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private AuthenticationService authenticationService;

	public JwtRequestFilter(RequestMatcher unrestrictedRequestsMatcher) {
		this.unrestrictedRequestsMatcher = unrestrictedRequestsMatcher;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String requestAuthorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		return this.unrestrictedRequestsMatcher.matches(request)
				|| requestAuthorizationHeader == null
				|| !Pattern.matches(BEARER_PREFIX_REGEX + ".*", requestAuthorizationHeader);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// header was checked when shouldNotFilter was called in OncePerRequestFilter.doFilter
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).replaceFirst(BEARER_PREFIX_REGEX, "");

		try {
			Authentication authenticationResult = authenticationService.authenticate(jwtToken);

			if (authenticationResult instanceof AbstractAuthenticationToken) {
				((AbstractAuthenticationToken) authenticationResult).setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request));
			}

			filterChain.doFilter(request, response);
		} catch (AuthenticationException authEx) {
			authenticationFailureHandler.onAuthenticationFailure(request, response, authEx);
		}
	}

}
