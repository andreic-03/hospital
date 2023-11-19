package org.hospital.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hospital.errorhandling.Errors;
import org.hospital.web.model.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthorizationEntryPoint implements AccessDeniedHandler {

    /**
     * The ObjectMapper instance used by Spring to read and write JSON requests and responses
     */
    @Autowired
    private ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		ErrorDTO errorDTO = new ErrorDTO(Errors.Functional.UNAUTHENTICATED.getErrorCode(),
				Errors.Functional.UNAUTHORIZED.getErrorMessage());

		response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		PrintWriter writer = response.getWriter();
		writer.print(objectMapper.writeValueAsString(errorDTO));
	}

}
