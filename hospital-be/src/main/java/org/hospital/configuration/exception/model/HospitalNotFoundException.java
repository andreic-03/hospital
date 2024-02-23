package org.hospital.configuration.exception.model;

import java.util.List;

public class HospitalNotFoundException extends HospitalException {

    public HospitalNotFoundException(ErrorType errorType) {
        super(errorType);
    }

    public HospitalNotFoundException(ErrorType errorType, List<String> errorArgs) {
        super(errorType, errorArgs);
    }
}
