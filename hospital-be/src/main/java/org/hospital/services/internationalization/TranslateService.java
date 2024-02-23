package org.hospital.services.internationalization;


import org.hospital.api.model.general.ApiError;
import org.hospital.configuration.exception.model.ErrorType;

import java.util.List;

public interface TranslateService {
    ApiError translate(final ErrorType errorType);

    ApiError translate(final ErrorType errorType, final List<String> args);

    ApiError translate(final ErrorType errorType, final List<String> args, final String details);
    ApiError translate(final ErrorType errorType, final List<String> args, final List<String> details);
}
