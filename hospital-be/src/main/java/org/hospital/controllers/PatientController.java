package org.hospital.controllers;

import org.hospital.api.model.PatientDTO;
import org.hospital.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/patients")
public class PatientController {
    @Autowired
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
