package org.patients.errorhandling;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationException extends org.springframework.security.core.AuthenticationException
		implements ErrorDetails {

	private static final long serialVersionUID = 6554008759916382112L;

	private final String errorCode;
	private final String errorMessage;
	private final Map<String, Object> context = new HashMap<String, Object>();

	public AuthenticationException(ErrorDetails error) {
		this(error, null, null);
	}

	public AuthenticationException(ErrorDetails error, Map<String, Object> context) {
		this(error, context, null);
	}

	public AuthenticationException(ErrorDetails error, Map<String, Object> context, Throwable cause) {
		super(error.getErrorMessage(), cause);
		this.errorCode = error.getErrorCode();
		this.errorMessage = error.getErrorMessage();
		if (context != null) {
			this.context.putAll(context);
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Map<String, Object> getContext() {
		return context;
	}

}
