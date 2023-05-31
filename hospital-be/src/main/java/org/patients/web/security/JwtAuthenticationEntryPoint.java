package org.patients.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.patients.errorhandling.Errors;
import org.patients.web.model.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    /**
     * The ObjectMapper instance used by Spring to read and write JSON requests and responses
     */
    @Autowired
    private ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
		ErrorDTO errorDTO = new ErrorDTO(Errors.Functional.UNAUTHENTICATED.getErrorCode(),
				Errors.Functional.UNAUTHENTICATED.getErrorMessage());

		response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		PrintWriter writer = response.getWriter();
		writer.print(objectMapper.writeValueAsString(errorDTO));
	}

	@Override
	public void afterPropertiesSet() {
		setRealmName("Hospital");
		super.afterPropertiesSet();
	}
}
