package org.hospital.services;

import org.hospital.api.model.MedicDTO;

import java.util.List;

public interface MedicService {

    MedicDTO createMedic(final MedicDTO medicDTO);

    MedicDTO findById(final Long id);

    List<MedicDTO> findAll();
}
