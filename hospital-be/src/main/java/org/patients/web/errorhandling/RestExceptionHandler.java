package org.patients.web.errorhandling;

import org.patients.errorhandling.AuthenticationException;
import org.patients.errorhandling.Errors;
import org.patients.errorhandling.GenericCheckedException;
import org.patients.errorhandling.UncheckedException;
import org.patients.web.model.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Map<String, HttpStatus> ERROR_CODE_TO_HTTP_STATUS_MAP = new HashMap<>();

	static {
		ERROR_CODE_TO_HTTP_STATUS_MAP.put(Errors.Functional.UNAUTHENTICATED.getErrorCode(), HttpStatus.UNAUTHORIZED);
		ERROR_CODE_TO_HTTP_STATUS_MAP.put(Errors.Functional.TOKEN_EXPIRED.getErrorCode(), HttpStatus.UNAUTHORIZED);
		ERROR_CODE_TO_HTTP_STATUS_MAP.put(Errors.Functional.UNAUTHORIZED.getErrorCode(), HttpStatus.FORBIDDEN);
		ERROR_CODE_TO_HTTP_STATUS_MAP.put(Errors.Functional.TOKEN_INVALID.getErrorCode(), HttpStatus.BAD_REQUEST);
		ERROR_CODE_TO_HTTP_STATUS_MAP.put(Errors.Functional.MISSING_INPUT.getErrorCode(), HttpStatus.BAD_REQUEST);
		ERROR_CODE_TO_HTTP_STATUS_MAP.put(Errors.Functional.NOT_FOUND.getErrorCode(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ UncheckedException.class })
	public ResponseEntity<ErrorDTO> handleUncheckedException(UncheckedException ex, WebRequest request) {
		ErrorDTO errorDTO = new ErrorDTO(ex.getErrorCode(), ex.getErrorMessage(), ex.getContext());
	    return new ResponseEntity<ErrorDTO>(errorDTO, new HttpHeaders(), mapErrorCodeToHttpStatus(ex.getErrorCode()));
	}

	@ExceptionHandler({ GenericCheckedException.class })
	public ResponseEntity<ErrorDTO> handleCheckedException(GenericCheckedException ex, WebRequest request) {
		ErrorDTO errorDTO = new ErrorDTO(ex.getErrorCode(), ex.getErrorMessage(), ex.getContext());
	    return new ResponseEntity<ErrorDTO>(errorDTO, new HttpHeaders(), mapErrorCodeToHttpStatus(ex.getErrorCode()));
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ErrorDTO> handleOtherExceptions(Exception ex, WebRequest request) {
		ErrorDTO errorDTO = new ErrorDTO(Errors.Functional.GENERIC.getErrorCode(), Errors.Functional.GENERIC.getErrorMessage());
		return new ResponseEntity<ErrorDTO>(errorDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<ErrorDTO> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
		ErrorDTO errorDTO = new ErrorDTO(ex.getErrorCode(), ex.getErrorMessage(), ex.getContext());
		return new ResponseEntity<ErrorDTO>(errorDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<ErrorDTO> errors = new ArrayList<>();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			ErrorDTO errorDTO = new ErrorDTO(Errors.Functional.CONSTRAINT_VIOLATION.getErrorCode(), Errors.Functional.CONSTRAINT_VIOLATION.getErrorMessage());

			errorDTO.getContext().put("detailedMessage", violation.getMessage());
			errorDTO.getContext().put("propertyPath", violation.getPropertyPath().toString());
			errors.add(errorDTO);
		}

		return new ResponseEntity<Object>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorDTO errorDTO = new ErrorDTO(Errors.Functional.WRONG_URL.getErrorCode(), Errors.Functional.WRONG_URL.getErrorMessage());
		return new ResponseEntity<Object>(errorDTO, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers,	HttpStatus status, WebRequest request) {

		ErrorDTO errorDTO = new ErrorDTO(Errors.Functional.METHOD_NOT_ALLOWED.getErrorCode(), Errors.Functional.METHOD_NOT_ALLOWED.getErrorMessage());
		errorDTO.getContext().put("supportedMethods", ex.getSupportedHttpMethods());

		return new ResponseEntity<Object>(errorDTO, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorDTO errorDTO = new ErrorDTO(Errors.Functional.UNSUPPORTED_MEDIA_TYPE.getErrorCode(), Errors.Functional.UNSUPPORTED_MEDIA_TYPE.getErrorMessage());
		errorDTO.getContext().put("supportedMediaTypes", ex.getContentType());

		return new ResponseEntity<Object>(errorDTO, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ErrorDTO> errors = new ArrayList<>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			ErrorDTO errorDTO = new ErrorDTO(Errors.Functional.INVALID_INPUT.getErrorCode(), Errors.Functional.INVALID_INPUT.getErrorMessage());
			errorDTO.getContext().put("detailedMessage", error.getDefaultMessage());
			errorDTO.getContext().put("paramName", error.getField());
			errors.add(errorDTO);
		}

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			ErrorDTO errorDTO = new ErrorDTO(Errors.Functional.INVALID_INPUT.getErrorCode(), Errors.Functional.INVALID_INPUT.getErrorMessage());
			errorDTO.getContext().put("detailedMessage", error.getDefaultMessage());
			errorDTO.getContext().put("paramName", error.getObjectName());
			errors.add(errorDTO);
		}

		return new ResponseEntity<Object>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorDTO errorDTO = new ErrorDTO(Errors.Functional.INVALID_FORMAT.getErrorCode(), Errors.Functional.INVALID_FORMAT.getErrorMessage());
		errorDTO.getContext().put("detailedMessage", ex.getMessage());

		return new ResponseEntity<Object>(errorDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	private HttpStatus mapErrorCodeToHttpStatus(String errorCode) {
		return ERROR_CODE_TO_HTTP_STATUS_MAP.get(errorCode) != null ? ERROR_CODE_TO_HTTP_STATUS_MAP.get(errorCode) : HttpStatus.INTERNAL_SERVER_ERROR;
	}

}
