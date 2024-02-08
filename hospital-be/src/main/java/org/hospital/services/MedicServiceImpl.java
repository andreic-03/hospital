package org.hospital.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.api.model.MedicRequestModel;
import org.hospital.api.model.MedicResponseModel;
import org.hospital.errorhandling.Errors;
import org.hospital.errorhandling.UncheckedException;
import org.hospital.persistence.entity.MedicEntity;
import org.hospital.mappers.MedicMapper;
import org.hospital.persistence.entity.PatientEntity;
import org.hospital.persistence.entity.UserEntity;
import org.hospital.persistence.repository.MedicRepository;
import org.hospital.persistence.repository.UserRepository;
import org.hospital.util.ValidationsUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MedicServiceImpl implements MedicService {
    private MedicRepository medicRepository;
    private UserRepository userRepository;
    private MedicMapper medicMapper;

    @Override
    public MedicResponseModel createMedic(final MedicRequestModel medicRequestModel) {
        try {
            MedicEntity medic = medicRepository.saveAndFlush(medicMapper.toMedicEntity(medicRequestModel));
            return medicMapper.toMedicModel(medic);
        } catch (DataIntegrityViolationException e) {
            throw new UncheckedException(Errors.Functional.EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public List<MedicResponseModel> findAll() {
        return medicRepository.findAll().stream()
                .map(medicMapper::toMedicModel)
                .collect(Collectors.toList());
    }

    @Override
    public MedicResponseModel findById(Long id) {
        return medicMapper.toMedicModel(findMedicById(id));
    }

    @Override
    public MedicResponseModel findMedicByFirstNameAndLastName(String firstName, String lastName) {
        MedicEntity medic = medicRepository.findMedicByFirstNameAndLastName(firstName, lastName);

        if (medic == null) {
            throw new UncheckedException(Errors.Functional.MEDIC_NOT_FOUND);
        }

        return medicMapper.toMedicModel(medic);
    }

    @Override
    public void delete(final Long id) {
        MedicEntity medicEntity = medicRepository.findById(id)
                .orElseThrow(() -> new UncheckedException(Errors.Functional.MEDIC_NOT_FOUND));

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

    private MedicEntity findMedicById(Long id) {
        return ValidationsUtil.validateEntityExistence(medicRepository.findById(id), "medic.id", id);
    }
}
