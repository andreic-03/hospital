package org.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.AppointmentDTO;
import org.hospital.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import static org.hospital.api.model.general.Constants.API_APPOINTMENT;

@RestController
@RequestMapping(API_APPOINTMENT)
@AllArgsConstructor
@Validated
public class AppointmentController {
    AppointmentService appointmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDTO create(@Valid @RequestBody final AppointmentDTO appointmentDTO) {
        return appointmentService.createAppointment(appointmentDTO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public AppointmentDTO update(@PathVariable final Long id, @Valid @RequestBody final AppointmentDTO appointmentDTO) {
        return appointmentService.updateAppointment(appointmentDTO, id);
    }
}
