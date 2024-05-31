package org.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.appointment.AppointmentRequestModel;
import org.hospital.api.model.appointment.AppointmentResponseModel;
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
    public AppointmentResponseModel create(@Valid @RequestBody final AppointmentRequestModel appointmentRequestModel) {
        return appointmentService.createAppointment(appointmentRequestModel);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public AppointmentResponseModel update(@PathVariable final Long id, @Valid @RequestBody final AppointmentRequestModel appointmentRequestModel) {
        return appointmentService.updateAppointment(appointmentRequestModel, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        appointmentService.deleteAppointment(id);
    }
}
