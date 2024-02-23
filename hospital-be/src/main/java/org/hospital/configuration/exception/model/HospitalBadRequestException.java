package org.hospital.configuration.exception.model;

import java.util.List;

public class HospitalBadRequestException extends HospitalException {

    public HospitalBadRequestException(ErrorType errorType) {
        super(errorType);
    }

    public HospitalBadRequestException(ErrorType errorType, List<String> errorArgs) {
        super(errorType, errorArgs);
    }
}
