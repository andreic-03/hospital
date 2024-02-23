package org.hospital.configuration.exception.model;

import java.util.List;

public class HospitalUnauthorizedException extends HospitalException {

    public HospitalUnauthorizedException(ErrorType errorType) {
        super(errorType);
    }

    public HospitalUnauthorizedException(ErrorType errorType, List<String> errorArgs) {
        super(errorType, errorArgs);
    }
}
