package org.hospital.configuration.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_SERVER_ERROR("F000", "Something unexpected happened."),
    AUTHENTICATION("F001", "Authentication error."),
    UNAUTHORIZED("F002", "You are not authorized to access this resource."),
    TOKEN_EXPIRED("F003", "The token has expired."),
    TOKEN_INVALID("F004", "The token is invalid."),
    WRONG_CREDENTIALS("F005", "No user account found for the supplied credentials."),
    UNSUPPORTED_MEDIA_TYPE("F006", "Unsupported Media Type"),
    WRONG_URL("F007", "No resource found for given URL"),

    REQUEST_METHOD_NOT_SUPPORTED("F008", "Request method {0} not supported"),
    MISSING_INPUT("F009", "The following required inputs are missing: ${paramNames}."),
    INVALID_INPUT("F010", "The value of {paramName} is invalid. Check context for details."),
    INVALID_FORMAT("F011", "The format of the input is invalid. Check context for details."),
    CONSTRAINT_VIOLATION("F012", "Validation error. Check context for details."),
    NOT_FOUND("F014", "The requested object was not found: ${id}"),
    CNP_NOT_FOUND("F016", "The patient with the entered CNP was not found."),
    PATIENT_FOR_MEDIC_NOT_FOUND("F017", "The patient for the given medic was not found."),
    ROLE_NOT_FOUND("F018", "The role was not found."),
    USER_NOT_FOUND("F019", "The user was not found."),
    EMAIL_ALREADY_EXISTS("F020", "The email already exists."),
    MEDIC_NOT_FOUND("F021", "The medic was not found."),
    PATIENT_NOT_FOUND("F022", "The patient was not found."),
    REGISTER_TOKEN_NOT_FOUND("F023", "Register account token not found."),
    NOT_ACCEPTABLE_MEDIA_TYPE("F024", "Not Acceptable Media Type"),
    MISSING_QUERY_PARAM("F025", "Missing query param: {0}"),
    TYPE_MISMATCH("F027", "Missing query param: {0}"),
    MULTIPART_CANNOT_BE_FOUND("F028", "The part {0} of the multipart/form-data request identified cannot be found"),
    MISSING_REQUEST_HEADER("F029", "Missing request header: {0}"),
    BAD_REQUEST("F030", "Bad request"),
    INVALID_PARAMETERS("F030", "Invalid parameters"),
    AUTHORIZATION("F031", "Authorization error."),
    OLD_PASSWORD_DID_NOT_MATCH("F032", "Old password did not match")
    ;

    private final String errorCode;
    private final String defaultErrorMessage;
}
