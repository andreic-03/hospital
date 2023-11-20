package org.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.PatientDTO;
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
    public PatientDTO create(@Valid @RequestBody final PatientDTO patientDTO) {
        patientDTO.setPatientId(null);
        return patientService.createPatient(patientDTO);
    }

    @GetMapping(value = "{firstName}/{lastName}")
    public PatientDTO findPatientByFirstNameAndLastName(@PathVariable final String firstName,
                                                        @PathVariable final String lastName) {
        return patientService.findPatientByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping(value = "/cnp/{cnp}")
    public PatientDTO findPatientByCnp(@PathVariable final Long cnp) {
        return patientService.findPatientByCnp(cnp);
    }

    @GetMapping(value = "/medic/{medicId}")
    public PatientDTO findPatientByMedic(@PathVariable final Long medicId) {
        return patientService.findPatientByMedic(medicId);
    }

//    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
//    public PatientDTO update(@PathVariable final Long id, @Valid @RequestBody final PatientDTO patientDTO) {
//        patientDTO.setPatientId(id);
//        return patientService.updatePatient(patientDTO);
//    }

}
