package org.hospital.controllers;

import org.hospital.api.model.AppointmentDTO;
import org.hospital.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDTO create(@Valid @RequestBody final AppointmentDTO appointmentDTO) {
        appointmentDTO.setAppointmentId(null);
        return appointmentService.createAppointment(appointmentDTO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public AppointmentDTO update(@PathVariable final Long id, @Valid @RequestBody final AppointmentDTO appointmentDTO) {
        appointmentDTO.setAppointmentId(id);
        return appointmentService.updateAppointment(appointmentDTO);
    }
}
