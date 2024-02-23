package org.hospital.configuration.exception.model;

import java.util.List;

public class HospitalConflictException extends HospitalException {

    public HospitalConflictException(ErrorType errorType) {
        super(errorType);
    }

    public HospitalConflictException(ErrorType errorType, List<String> errorArgs) {
        super(errorType, errorArgs);
    }
}
