package org.hospital.util;

import org.hospital.configuration.exception.model.ErrorType;
import org.hospital.configuration.exception.model.HospitalException;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {

    public static String getDateOfBirthFromCNP(final String cnp) {
        if (cnp == null || cnp.length() != 13) {
            throw new HospitalException(ErrorType.CNP_INVALID_LENGTH);
        }

        if (!cnp.matches("\\d{13}")) {
            throw new HospitalException(ErrorType.CNP_INVALID);
        }

        StringBuilder dateOfBirth = new StringBuilder();
        String yearPrefix = switch (cnp.charAt(0)) {
            case '1', '2' -> "19";
            case '3', '4' -> "18";
            case '5', '6' -> "20";
            default -> throw new HospitalException(ErrorType.CNP_INVALID_FIRST_DIGIT);
        };

        String year = yearPrefix + cnp.substring(1, 3);
        String month = cnp.substring(3, 5);
        String day = cnp.substring(5, 7);

        dateOfBirth.append(day).append(".").append(month).append(".").append(year);

        return dateOfBirth.toString();
    }

    public static String getGenderFromCNP(final String cnp) {

        return switch (cnp.charAt(0)) {
            case '1', '3', '5' -> "Masculin";
            case '2', '4', '6' -> "Feminin";
            default -> throw new HospitalException(ErrorType.CNP_INVALID_FIRST_DIGIT);
        };
    }

    public static String getProcessedSearchTerm(String searchTerm) {
        return Optional.ofNullable(searchTerm)
                .map(value -> value.trim().replaceAll("\\s{2,}", " "))
                .map(value -> Arrays.stream(value.split(" "))
                        .map(word -> word + ":*")
                        // if multiple values are provided, & should be added instead of space
                        .collect(Collectors.joining("&"))
                )
                .orElse("");
    }
}
