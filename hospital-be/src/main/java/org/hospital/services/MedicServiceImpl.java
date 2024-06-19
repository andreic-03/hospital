package org.hospital.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.api.model.medic.MedicRequestModel;
import org.hospital.api.model.medic.MedicResponseModel;
import org.hospital.configuration.exception.model.ErrorType;
import org.hospital.configuration.exception.model.HospitalException;
import org.hospital.persistence.entity.MedicEntity;
import org.hospital.mappers.MedicMapper;
import org.hospital.persistence.entity.MedicSpecialty;
import org.hospital.persistence.entity.PatientEntity;
import org.hospital.persistence.entity.UserEntity;
import org.hospital.persistence.repository.MedicRepository;
import org.hospital.persistence.repository.UserRepository;
import org.hospital.util.ValidationsUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.hospital.util.Utils.getProcessedSearchTerm;

@Slf4j
@Service
@AllArgsConstructor
public class MedicServiceImpl implements MedicService {
    private MedicRepository medicRepository;
    private UserRepository userRepository;
    private MedicMapper medicMapper;

    @Override
    public List<MedicResponseModel> filterMedics(String searchTerm) {
        final var processedSearchTerm = getProcessedSearchTerm(searchTerm);

        final var patient = StringUtils.hasLength(processedSearchTerm) ?
                medicRepository.filterMedics(processedSearchTerm) :
                medicRepository.findAll();

        return patient.stream()
                .map(medicMapper::toMedicModel)
                .collect(Collectors.toList());
    }

    @Override
    public MedicResponseModel createMedic(final MedicRequestModel medicRequestModel) {
        try {
            MedicEntity medic = medicRepository.saveAndFlush(medicMapper.toMedicEntity(medicRequestModel));
            return medicMapper.toMedicModel(medic);
        } catch (DataIntegrityViolationException e) {
            throw new HospitalException(ErrorType.EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public MedicResponseModel findById(Long id) {
        return medicMapper.toMedicModel(findMedicById(id));
    }

    @Override
    public MedicResponseModel findMedicByFirstNameAndLastName(String firstName, String lastName) {
        MedicEntity medic = medicRepository.findMedicByFirstNameAndLastName(firstName, lastName);

        if (medic == null) {
            throw new HospitalException(ErrorType.MEDIC_NOT_FOUND);
        }

        return medicMapper.toMedicModel(medic);
    }

    @Override
    public void delete(final Long id) {
        MedicEntity medicEntity = medicRepository.findById(id)
                .orElseThrow(() -> new HospitalException(ErrorType.MEDIC_NOT_FOUND));

        UserEntity user = medicEntity.getUser();

        //If there is a patient who is assigned to this medic, remove the medic from patient
        if (!medicEntity.getPatients().isEmpty()) {
            for (PatientEntity patient : medicEntity.getPatients()) {
                patient.getMedics().remove(medicEntity);
            }
        }

        if (user != null) {
            user.setMedic(null);
        }

        medicRepository.delete(medicEntity);

        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Override
    public List<MedicResponseModel> getAllMedicsBySpecialty(String specialty) {
        MedicSpecialty medicSpecialty = MedicSpecialty.valueOf(specialty.toUpperCase());

        return medicRepository.findMedicsBySpecialty(medicSpecialty).stream()
                .map(medicMapper::toMedicModel)
                .collect(Collectors.toList());
    }

    private MedicEntity findMedicById(Long id) {
        return ValidationsUtil.validateEntityExistence(medicRepository.findById(id), "medic.id", id);
    }
}
