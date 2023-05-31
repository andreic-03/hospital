package org.patients.services;

import org.patients.models.dto.MedicDTO;

public interface MedicService {

    MedicDTO createMedic(final MedicDTO medicDTO);

    MedicDTO findById(final Long id);
}
