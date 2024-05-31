package org.hospital.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hospital.api.model.user.UserRegisterStepOneRequestModel;
import org.hospital.api.model.user.UserRegisterStepOneResponseModel;
import org.hospital.config.ControllerConfig;
import org.hospital.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


@WebMvcTest(UserController.class)
@Import(ControllerConfig.class)
@ActiveProfiles("controller-test")
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserController userController;
    @MockBean
    private UserService userService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void registerStepOne_success() {
        UserRegisterStepOneRequestModel requestModel = new UserRegisterStepOneRequestModel();
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");

        requestModel.setUsername("login");
        requestModel.setPassword("pass");
        requestModel.setEmail("email@email.com");
        requestModel.setPhoneNumber("0745123123");
        requestModel.setRoles(roles);

        UserRegisterStepOneResponseModel userRegisterStepOneResponseModel = new UserRegisterStepOneResponseModel();
        userRegisterStepOneResponseModel.setUsername("login");
        userRegisterStepOneResponseModel.setEmail("email@email.com");
        userRegisterStepOneResponseModel.setId(1L);

        when(userController.userRegisterStepOne(any())).thenReturn(userRegisterStepOneResponseModel);

//        mockMvc.perform(post())
    }
}
