package org.patients.services;

import org.patients.models.Medic;
import org.patients.models.dto.MedicDTO;
import org.patients.models.mappers.MedicMapper;
import org.patients.repositories.MedicRepository;
import org.patients.util.ValidationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicServiceImpl implements MedicService {
    private static final MedicMapper MEDIC_MAPPER = MedicMapper.INSTANCE;

    @Autowired
    private MedicRepository medicRepository;

    @Override
    public MedicDTO createMedic(final MedicDTO medicDTO) {
        Medic medic = medicRepository.saveAndFlush(MEDIC_MAPPER.convertMedicDTOtoEntity(medicDTO));

        return MEDIC_MAPPER.convertMedicEntityToDTO(medic);
    }

    @Override
    public MedicDTO findById(Long id) {
        return MEDIC_MAPPER.convertMedicEntityToDTO(findMedicById(id));
    }

    private Medic findMedicById(Long id) {
        return ValidationsUtil.validateEntityExistence(medicRepository.findById(id), "medic.id", id);
    }
}
