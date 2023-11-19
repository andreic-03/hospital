package org.hospital.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.hospital.persistence.entity.MedicEntity;
import org.hospital.api.model.MedicDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicMapper {
    MedicEntity convertMedicDTOtoEntity(MedicDTO medicDTO);

    MedicDTO convertMedicEntityToDTO(MedicEntity medic);
}
