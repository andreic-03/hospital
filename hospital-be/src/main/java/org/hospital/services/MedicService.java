package org.hospital.services;

import org.hospital.api.model.medic.MedicRequestModel;
import org.hospital.api.model.medic.MedicResponseModel;

import java.util.List;

public interface MedicService {

    List<MedicResponseModel> filterMedics(final String searchTerm);
    MedicResponseModel createMedic(final MedicRequestModel medicRequestModel);
    MedicResponseModel findById(final Long id);
    MedicResponseModel findMedicByFirstNameAndLastName(final String firstName, final String lastName);
    List<MedicResponseModel> getAllMedicsBySpecialty(final String specialty);
    void delete(final Long id);
}
