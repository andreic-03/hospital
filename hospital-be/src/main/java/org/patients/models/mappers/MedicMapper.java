package org.patients.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.patients.models.Medic;
import org.patients.models.dto.MedicDTO;

@Mapper
public interface MedicMapper {

    MedicMapper INSTANCE = Mappers.getMapper(MedicMapper.class);

    Medic convertMedicDTOtoEntity(MedicDTO medicDTO);

    MedicDTO convertMedicEntityToDTO(Medic medic);
}
