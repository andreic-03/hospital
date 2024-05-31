package org.hospital.services;

import org.hospital.api.model.medic.MedicRequestModel;
import org.hospital.api.model.medic.MedicResponseModel;

import java.util.List;

public interface MedicService {

    MedicResponseModel createMedic(final MedicRequestModel medicRequestModel);

    MedicResponseModel findById(final Long id);
    MedicResponseModel findMedicByFirstNameAndLastName(final String firstName, final String lastName);

    List<MedicResponseModel> findAll();

    void delete(final Long id);
}
