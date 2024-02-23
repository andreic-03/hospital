package org.hospital.web.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.hospital.security.JwtTokenUtil;
import org.hospital.services.security.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.hospital.api.model.general.Constants.ACCESS_TOKEN_PREFIX_NAME;
import static org.hospital.api.model.general.Constants.AUTHORIZATION_HEADER_NAME;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
	private final JwtTokenUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		if (!hasAuthorizationBearer(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getAccessToken(request);

		if (!jwtUtil.validateAccessToken(token) || !tokenService.isUserSessionKnown(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			setAuthenticationContext(token, request);
			filterChain.doFilter(request, response);
		} finally {
			SecurityContextHolder.clearContext();
		}
	}

	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
		return !ObjectUtils.isEmpty(header) && header.startsWith(ACCESS_TOKEN_PREFIX_NAME);
	}

	private String getAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER_NAME))
				.map(value -> value.split(" "))
				.filter(headerParts -> headerParts.length > 1)
				.map(headerParts -> headerParts[1])
				.orElse("");
	}

	private void setAuthenticationContext(String token, HttpServletRequest request) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.getUsernameFromToken(token));
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

}
