package org.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.appointment.AppointmentRequestModel;
import org.hospital.api.model.appointment.AppointmentResponseModel;
import org.hospital.configuration.exception.model.ErrorType;
import org.hospital.configuration.exception.model.HospitalUnauthorizedException;
import org.hospital.security.model.AppUserPrincipal;
import org.hospital.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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

    @GetMapping()
    public List<AppointmentResponseModel> getAllAppointments(@AuthenticationPrincipal AppUserPrincipal user) {
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        if (roles.contains("MEDIC")) {
            return appointmentService.findAllAppointmentsByMedic(user.getUserEntity().getMedic());
        } else if (roles.contains("PATIENT")) {
            return appointmentService.findAllAppointmentsByPatient(user.getUserEntity().getPatient());
        } else {
            throw new HospitalUnauthorizedException(ErrorType.AUTHENTICATION);
        }
    }
}
