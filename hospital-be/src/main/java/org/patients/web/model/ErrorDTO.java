package org.patients.web.model;

import org.patients.errorhandling.ErrorDetails;

import java.util.HashMap;
import java.util.Map;

public class ErrorDTO implements ErrorDetails {

	private final String errorCode;
	private final String errorMessage;
	private final Map<String, Object> context = new HashMap<String, Object>();
	
	public ErrorDTO(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ErrorDTO(String errorCode, String errorMessage, Map<String, Object> context) {
		this(errorCode, errorMessage);
		if (context != null) {
			this.context.putAll(context);
		}
	}

	@Override
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	public Map<String, Object> getContext() {
		return context;
	}

}
