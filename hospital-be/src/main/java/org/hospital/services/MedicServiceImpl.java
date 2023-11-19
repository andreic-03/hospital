package org.hospital.services;

import org.hospital.persistence.entity.MedicEntity;
import org.hospital.api.model.MedicDTO;
import org.hospital.mappers.MedicMapper;
import org.hospital.persistence.repository.MedicRepository;
import org.hospital.util.ValidationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicServiceImpl implements MedicService {
    private static final MedicMapper MEDIC_MAPPER = MedicMapper.INSTANCE;

    @Autowired
    private MedicRepository medicRepository;

    @Override
    public MedicDTO createMedic(final MedicDTO medicDTO) {
        MedicEntity medic = medicRepository.saveAndFlush(MEDIC_MAPPER.convertMedicDTOtoEntity(medicDTO));

        return MEDIC_MAPPER.convertMedicEntityToDTO(medic);
    }

    @Override
    public List<MedicDTO> findAll() {
        return medicRepository.findAll().stream()
                .map(MEDIC_MAPPER::convertMedicEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicDTO findById(Long id) {
        return MEDIC_MAPPER.convertMedicEntityToDTO(findMedicById(id));
    }

    private MedicEntity findMedicById(Long id) {
        return ValidationsUtil.validateEntityExistence(medicRepository.findById(id), "medic.id", id);
    }
}
