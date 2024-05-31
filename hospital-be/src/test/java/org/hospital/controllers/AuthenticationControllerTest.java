package org.hospital.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hospital.api.model.auth.AuthRequestModel;
import org.hospital.api.model.auth.AuthResponseModel;
import org.hospital.config.ControllerConfig;
import org.hospital.configuration.exception.RestResponseEntityExceptionHandler;
import org.hospital.configuration.exception.model.ErrorType;
import org.hospital.configuration.exception.model.HospitalUnauthorizedException;
import org.hospital.services.auth.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@Import(ControllerConfig.class)
@ActiveProfiles("controller-test")
public class AuthenticationControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private AuthenticationController authenticationController;
    @MockBean
    private AuthenticationService authenticationService;
    @Autowired
    private RestResponseEntityExceptionHandler exceptionHandler;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController)
                .setControllerAdvice(exceptionHandler)
                .build();
    }

    @Test
    void login_success() throws Exception {
        AuthRequestModel authRequestModel = new AuthRequestModel();
        authRequestModel.setUsername("login");
        authRequestModel.setPassword("pass");

        AuthResponseModel authResponseModel = new AuthResponseModel();
        authResponseModel.setUsername("login");
        authResponseModel.setAccessToken("accessToken");

        when(authenticationService.login(any(AuthRequestModel.class))).thenReturn(authResponseModel);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(authRequestModel)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.username", is("login")))
                .andExpect(jsonPath("$.accessToken", is("accessToken")));
    }

    @Test
    void login_shouldReturnUnauthorized() throws Exception {
        AuthRequestModel authRequestModel = new AuthRequestModel();
        authRequestModel.setUsername("login");
        authRequestModel.setPassword("pass");

        when(authenticationService.login(any(AuthRequestModel.class)))
                .thenThrow(new HospitalUnauthorizedException(ErrorType.AUTHENTICATION));

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequestModel)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void logout_success() throws Exception {

        mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isNoContent());
    }
}
