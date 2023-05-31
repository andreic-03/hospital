package org.patients.web.config;

import org.patients.errorhandling.ErrorDetails;
import org.patients.errorhandling.Errors;
import org.patients.web.model.ErrorDTO;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ErrorAttributesConfiguration {

	@Bean
	public ErrorAttributes errorAttributes() {
		return new ErrorDTOLikeErrorAttributes();
	}

	/**
	 * {@link ErrorAttributes} implementation which produces a map with keys corresponding to {@link ErrorDTO} fields.
	 */
	public static class ErrorDTOLikeErrorAttributes extends DefaultErrorAttributes {

		public ErrorDTOLikeErrorAttributes() {
			super();
		}

		@Override
		public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions errorAttributeOptions) {
			Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, errorAttributeOptions);
			Map<String, Object> errorAttributes = new LinkedHashMap<>();

			ErrorDetails errorDetails = mapHttpStatusToErrorDetails((Integer) defaultErrorAttributes.getOrDefault("status", 500));
			errorAttributes.put("errorCode", errorDetails.getErrorCode());
			errorAttributes.put("errorMessage", errorDetails.getErrorMessage());

			Map<String, Object> errorContext = new LinkedHashMap<>();
			errorAttributes.put("context", errorContext);
			errorContext.put("timestamp", defaultErrorAttributes.get("timestamp"));
			errorContext.put("detailedMessage", defaultErrorAttributes.get("message"));

			return errorAttributes;
		}

		protected ErrorDetails mapHttpStatusToErrorDetails(Integer httpStatus) {
			if (HttpStatus.NOT_FOUND.value() == httpStatus) {
				return Errors.Functional.WRONG_URL;
			}
			return HttpStatus.valueOf(httpStatus).is4xxClientError() ? Errors.Functional.GENERIC : Errors.Technical.GENERIC;
		}
	}

}
