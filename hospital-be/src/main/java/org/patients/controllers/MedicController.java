package org.patients.controllers;

import org.patients.models.dto.MedicDTO;
import org.patients.services.MedicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/medics")
public class MedicController {

    @Autowired
    private MedicService medicService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicDTO create(@Valid @RequestBody final MedicDTO medicDTO) {
        medicDTO.setMedicId(null);
        return medicService.createMedic(medicDTO);
    }

    @GetMapping(value = "{id}")
    public MedicDTO get(@PathVariable final Long id) {
        return medicService.findById(id);
    }
}
