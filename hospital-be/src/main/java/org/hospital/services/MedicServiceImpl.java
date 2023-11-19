package org.hospital.services;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MedicServiceImpl implements MedicService {
    private MedicRepository medicRepository;
    private MedicMapper medicMapper;

    @Override
    public MedicDTO createMedic(final MedicDTO medicDTO) {
        MedicEntity medic = medicRepository.saveAndFlush(medicMapper.convertMedicDTOtoEntity(medicDTO));

        return medicMapper.convertMedicEntityToDTO(medic);
    }

    @Override
    public List<MedicDTO> findAll() {
        return medicRepository.findAll().stream()
                .map(medicMapper::convertMedicEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicDTO findById(Long id) {
        return medicMapper.convertMedicEntityToDTO(findMedicById(id));
    }

    private MedicEntity findMedicById(Long id) {
        return ValidationsUtil.validateEntityExistence(medicRepository.findById(id), "medic.id", id);
    }
}
