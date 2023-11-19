package org.hospital.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.hospital.persistence.entity.MedicEntity;
import org.hospital.api.model.MedicDTO;

@Mapper
public interface MedicMapper {

    MedicMapper INSTANCE = Mappers.getMapper(MedicMapper.class);

    MedicEntity convertMedicDTOtoEntity(MedicDTO medicDTO);

    MedicDTO convertMedicEntityToDTO(MedicEntity medic);
}
