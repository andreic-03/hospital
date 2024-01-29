package org.hospital.services;

import lombok.AllArgsConstructor;
import org.hospital.api.model.MedicRequestModel;
import org.hospital.api.model.MedicResponseModel;
import org.hospital.errorhandling.Errors;
import org.hospital.errorhandling.UncheckedException;
import org.hospital.persistence.entity.MedicEntity;
import org.hospital.mappers.MedicMapper;
import org.hospital.persistence.repository.MedicRepository;
import org.hospital.util.ValidationsUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicServiceImpl implements MedicService {
    private MedicRepository medicRepository;
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
        return medicMapper.toMedicModel(medicRepository.findMedicByFirstNameAndLastName(firstName, lastName));
    }

    private MedicEntity findMedicById(Long id) {
        return ValidationsUtil.validateEntityExistence(medicRepository.findById(id), "medic.id", id);
    }
}
