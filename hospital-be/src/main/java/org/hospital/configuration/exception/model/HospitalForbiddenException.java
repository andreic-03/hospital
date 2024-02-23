package org.hospital.configuration.exception.model;

import java.util.List;

public class HospitalForbiddenException extends HospitalException {

    public HospitalForbiddenException(ErrorType errorType) {
        super(errorType);
    }

    public HospitalForbiddenException(ErrorType errorType, List<String> errorArgs) {
        super(errorType, errorArgs);
    }
}
