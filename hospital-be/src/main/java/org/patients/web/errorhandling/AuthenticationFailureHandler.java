package org.patients.web.errorhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.patients.errorhandling.Errors;
import org.patients.web.model.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    /**
     * The ObjectMapper instance used by Spring to read and write JSON requests and responses
     */
    @Autowired
    private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		ErrorDTO errorDTO;
		if (exception instanceof org.patients.errorhandling.AuthenticationException) {
			org.patients.errorhandling.AuthenticationException authenticatioException =
					(org.patients.errorhandling.AuthenticationException) exception;
			errorDTO = new ErrorDTO(authenticatioException.getErrorCode(), authenticatioException.getErrorMessage(),
					authenticatioException.getContext());
		} else {
			errorDTO = new ErrorDTO(Errors.Functional.UNAUTHENTICATED.getErrorCode(),
					Errors.Functional.UNAUTHENTICATED.getErrorMessage());
		}

		PrintWriter writer = response.getWriter();
		writer.print(objectMapper.writeValueAsString(errorDTO));
	}

}
