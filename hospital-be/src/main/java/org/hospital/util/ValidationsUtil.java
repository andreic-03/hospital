package org.hospital.util;

import org.hospital.configuration.exception.model.ErrorType;
import org.hospital.configuration.exception.model.HospitalException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ValidationsUtil {

    public static <T> T validateEntityExistence(Optional<T> optional, String idKey, Object idValue) {
        return optional.orElseThrow(() -> {
            Map<String, Object> context = new HashMap<>();
            context.put(idKey, idValue);
            return new HospitalException(ErrorType.NOT_FOUND);
        });
    }
}
