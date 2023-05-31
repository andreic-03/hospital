package org.patients.util;


import org.patients.errorhandling.Errors;
import org.patients.errorhandling.UncheckedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ValidationsUtil {

    public static <T> T validateEntityExistence(Optional<T> optional, String idKey, Object idValue) {
        return optional.orElseThrow(() -> {
            Map<String, Object> context = new HashMap<>();
            context.put(idKey, idValue);
            return new UncheckedException(Errors.Functional.NOT_FOUND, context);
        });
    }
}
