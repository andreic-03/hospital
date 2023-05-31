package org.patients.controllers;

import org.patients.repositories.AppointmentRepository;
import org.patients.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sendEmail")
public class SendEmailController {
    @Autowired
    EmailService emailService;

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping
    public void sendEmail() {
        emailService.sendSimpleMessage("cioropar.andrei3@gmail.com", "Test", "Test");
    }
}
