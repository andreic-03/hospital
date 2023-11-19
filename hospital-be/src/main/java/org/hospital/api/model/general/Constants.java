package org.hospital.api.model.general;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access= AccessLevel.PRIVATE)
public final class Constants {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_EDITOR = "EDITOR";

    public static final String API_AUTH = "/auth";

    public static final String API_USERS = "/user";
    public static final String API_ADMIN_USERS = "/user/admin";
    public static final String API_ROLES = "/role";
    public static final String API_PATIENT = "/patient";
    public static final String API_MEDIC = "/medic";
    public static final String API_APPOINTMENT = "/appointment";

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String ACCESS_TOKEN_PREFIX_NAME = "Bearer";

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "ASC";

}
