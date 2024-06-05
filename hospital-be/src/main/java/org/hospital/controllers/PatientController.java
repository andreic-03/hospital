package org.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.patient.PatientCreateRequestModel;
import org.hospital.api.model.patient.PatientResponseModel;
import org.hospital.api.model.patient.PatientUpdateRequestModel;
import org.hospital.api.model.user.UserRegisterStepTwoRequestModel;
import org.hospital.security.model.AppUserPrincipal;
import org.hospital.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.hospital.api.model.general.Constants.API_PATIENT;

@RestController
@RequestMapping(API_PATIENT)
@AllArgsConstructor
@Validated
public class PatientController {
    private PatientService patientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseModel create(@Valid @RequestBody final PatientCreateRequestModel patientCreateRequestModel) {
        return patientService.createPatient(patientCreateRequestModel);
    }

    @GetMapping(value = "{id}")
    public PatientResponseModel findById(@PathVariable final Long id) {
        return patientService.findById(id);
    }

    @GetMapping("/getByFirstNameAndLastName")
    public PatientResponseModel findPatientByFirstNameAndLastName(@RequestParam("firstName") final String firstName,
                                                                  @RequestParam("lastName") final String lastName) {
        return patientService.findPatientByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping(value = "/cnp/{cnp}")
    public PatientResponseModel findPatientByCnp(@PathVariable final String cnp) {
        return patientService.findPatientByCnp(cnp);
    }

    @GetMapping(value = "/medic/{medicId}")
    public PatientResponseModel findPatientByMedic(@PathVariable final Long medicId) {
        return patientService.findPatientByMedic(medicId);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public PatientResponseModel update(@PathVariable final Long id, @Valid @RequestBody final PatientUpdateRequestModel patientUpdateRequestModel) {
        return patientService.updatePatient(patientUpdateRequestModel, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        patientService.delete(id);
    }

    @PostMapping("/registration/step-two")
    public PatientResponseModel userRegisterStepTwo(@RequestBody final UserRegisterStepTwoRequestModel userRegisterStepTwoRequestModel) {
        return patientService.registerUserStepTwo(userRegisterStepTwoRequestModel);
    }

    @GetMapping("/createdByCurrentUser")
    public List<PatientResponseModel> getPatientsCreatedByCurrentUser(@AuthenticationPrincipal AppUserPrincipal user) {
        return patientService.getPatientsCreatedByCurrentUser(user.getUserEntity());
    }
}
