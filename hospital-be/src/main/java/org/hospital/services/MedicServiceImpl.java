package org.hospital.services;

import lombok.AllArgsConstructor;
import org.hospital.api.model.MedicRequestModel;
import org.hospital.api.model.MedicResponseModel;
import org.hospital.persistence.entity.MedicEntity;
import org.hospital.mappers.MedicMapper;
import org.hospital.persistence.repository.MedicRepository;
import org.hospital.util.ValidationsUtil;
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
        MedicEntity medic = medicRepository.saveAndFlush(medicMapper.toMedicEntity(medicRequestModel));

        return medicMapper.toUserModel(medic);
    }

    @Override
    public List<MedicResponseModel> findAll() {
        return medicRepository.findAll().stream()
                .map(medicMapper::toUserModel)
                .collect(Collectors.toList());
    }

    @Override
    public MedicResponseModel findById(Long id) {
        return medicMapper.toUserModel(findMedicById(id));
    }

    @Override
    public MedicResponseModel findMedicByFirstNameAndLastName(String firstName, String lastName) {
        return medicMapper.toUserModel(medicRepository.findMedicByFirstNameAndLastName(firstName, lastName));
    }

    private MedicEntity findMedicById(Long id) {
        return ValidationsUtil.validateEntityExistence(medicRepository.findById(id), "medic.id", id);
    }
}
