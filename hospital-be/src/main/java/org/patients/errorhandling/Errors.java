package org.patients.errorhandling;

/**
 * Common container for all the error codes in the whole application domain (helps avoid duplication).
 */
public enum Errors {

	/* empty */	;

	/**
	 * Technical errors are most likely temporary (like a server not being available)
	 * and the operation being performed at the time of the error can be retried later with the same inputs.
	 * Exceptions using these codes should be unchecked.
	 */
	public enum Technical implements ErrorDetails {
	
		GENERIC("T000", "An error occured. Please try again later.");
	
		private String errorCode;
		private String errorMessage;
	
		private Technical(String errorCode, String errorMessage) {
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public String getErrorMessage() {
			return errorMessage;
		}
	
	}

	/**
	 * Functional errors are business logic errors (caused by invalid inputs or application state transitions)
	 * and the operation being performed at the time of the error can not be retried later with the same inputs.
	 * Exceptions using these codes should be checked if the caller can be reasonably expected to recover, otherwise unchecked.
	 */
	public enum Functional implements ErrorDetails {
	
		GENERIC("F000", "An error occured. Please check the context information (if any) or contact support."),
		UNAUTHENTICATED("F001", "Authentication required."),
		UNAUTHORIZED("F002", "You are not authorized to access this resource."),
		TOKEN_EXPIRED("F003", "The token has expired."),
		TOKEN_INVALID("F004", "The token is invalid."),
		WRONG_CREDENTIALS("F005", "No user account found for the supplied credentials."),
		METHOD_NOT_ALLOWED("F006", "Method is not supported for this request. Supported methods are: ${supportedMethods}"),
		WRONG_URL("F007", "No resource found for given URL"),
		UNSUPPORTED_MEDIA_TYPE("F008", "Media type is not supported. Supported media types are: ${supportedMediaTypes}"),
		MISSING_INPUT("F009", "The following required inputs are missing: ${paramNames}."),
		INVALID_INPUT("F010", "The value of {paramName} is invalid. Check context for details."),
		INVALID_FORMAT("F011", "The format of the input is invalid. Check context for details."),
		CONSTRAINT_VIOLATION("F012", "Validation error. Check context for details."),
		NOT_FOUND("F014", "The requested object was not found: ${id}"),
		CNP_NOT_FOUND("F016", "The patient with the entered CNP was not found."),
		MEDIC_NOT_FOUND("F017", "The patient for the given medic was not found.");

		private String errorCode;
		private String errorMessage;
	
		private Functional(String errorCode, String errorMessage) {
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public String getErrorMessage() {
			return errorMessage;
		}
	
	}

}
