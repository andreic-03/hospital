package org.hospital.web.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.hospital.configuration.exception.model.ErrorType;
import org.hospital.services.internationalization.TranslateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
@AllArgsConstructor
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

	private final TranslateService translateService;
	private final ObjectMapper mapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		var errorMessage = translateService.translate(ErrorType.AUTHENTICATION);
		var body = mapper.writeValueAsBytes(errorMessage);
		response.getOutputStream().write(body);
	}
}
