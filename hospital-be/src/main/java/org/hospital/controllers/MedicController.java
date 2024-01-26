package org.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.MedicRequestModel;
import org.hospital.api.model.MedicResponseModel;
import org.hospital.services.MedicService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hospital.api.model.general.Constants.API_MEDIC;

@RestController
@RequestMapping(API_MEDIC)
@AllArgsConstructor
@Validated
public class MedicController {

    private MedicService medicService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicResponseModel create(@Valid @RequestBody final MedicRequestModel medicRequestModel) {
        return medicService.createMedic(medicRequestModel);
    }

    @GetMapping(value = "{id}")
    public MedicResponseModel get(@PathVariable final Long id) {
        return medicService.findById(id);
    }

    @GetMapping
    public List<MedicResponseModel> list() {
        return medicService.findAll();
    }

    @GetMapping(value = "{firstName}/{lastName}")
    public MedicResponseModel findMedicByFirstNameAndLastName(@PathVariable final String firstName,
                                                                  @PathVariable final String lastName) {
        return medicService.findMedicByFirstNameAndLastName(firstName, lastName);
    }
}
