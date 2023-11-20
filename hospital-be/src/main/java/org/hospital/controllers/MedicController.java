package org.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.MedicDTO;
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
    public MedicDTO create(@Valid @RequestBody final MedicDTO medicDTO) {
        medicDTO.setMedicId(null);
        return medicService.createMedic(medicDTO);
    }

    @GetMapping(value = "{id}")
    public MedicDTO get(@PathVariable final Long id) {
        return medicService.findById(id);
    }

    @GetMapping
    public List<MedicDTO> list() {
        return medicService.findAll();
    }
}
