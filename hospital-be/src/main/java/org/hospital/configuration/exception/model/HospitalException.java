package org.hospital.configuration.exception.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hospital.configuration.exception.model.ErrorType.INTERNAL_SERVER_ERROR;

@Getter
public class HospitalException extends RuntimeException {

    private ErrorType errorType;
    private List<String> errorArgs;

    public HospitalException() {
        super(INTERNAL_SERVER_ERROR.getDefaultErrorMessage());
        this.errorType = INTERNAL_SERVER_ERROR;
        this.errorArgs = new ArrayList<>();
    }

    public HospitalException(ErrorType errorType) {
        super(Optional.ofNullable(errorType)
                .map(ErrorType::getDefaultErrorMessage)
                .orElseGet(INTERNAL_SERVER_ERROR::getDefaultErrorMessage));
        this.errorType = errorType;
        this.errorArgs = new ArrayList<>();
    }

    public HospitalException(ErrorType errorType, List<String> errorArgs) {
        super(Optional.ofNullable(errorType)
                .map(ErrorType::getDefaultErrorMessage)
                .orElseGet(INTERNAL_SERVER_ERROR::getDefaultErrorMessage)
        );
        this.errorType = errorType;
        this.errorArgs = errorArgs;
    }
}
