package org.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.PatientCreateRequestModel;
import org.hospital.api.model.PatientResponseModel;
import org.hospital.api.model.PatientUpdateRequestModel;
import org.hospital.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/getByFirstNameAndLastName")
    public PatientResponseModel findPatientByFirstNameAndLastName(@RequestParam("firstName") final String firstName,
                                                                  @RequestParam("lastName") final String lastName) {
        return patientService.findPatientByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping(value = "/cnp/{cnp}")
    public PatientResponseModel findPatientByCnp(@PathVariable final Long cnp) {
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
}
