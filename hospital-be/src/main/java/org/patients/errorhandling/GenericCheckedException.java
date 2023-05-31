package org.patients.errorhandling;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic checked exception (using {@link Errors.Functional.GENERIC}).
 * Should be extended if another error code is required.
 */
public class GenericCheckedException extends Exception implements ErrorDetails {

	private static final long serialVersionUID = 4258002697103913924L;
	private static final ErrorDetails errorDetails = Errors.Functional.GENERIC;

	private final Map<String, Object> context = new HashMap<String, Object>();

	public GenericCheckedException() {
		this(null, null);
	}

	public GenericCheckedException(Map<String, Object> context) {
		this(context, null);
	}

	public GenericCheckedException(Map<String, Object> context, Throwable cause) {
		super(errorDetails.getErrorMessage(), cause);
		if (context != null) {
			this.context.putAll(context);
		}
	}

	public String getErrorCode() {
		return errorDetails.getErrorCode();
	}

	public String getErrorMessage() {
		return errorDetails.getErrorMessage();
	}

	public Map<String, Object> getContext() {
		return context;
	}

}
