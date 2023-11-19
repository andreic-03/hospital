package org.hospital.mappers;

import org.hospital.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.hospital.api.model.AppointmentDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentMapper {

    AppointmentEntity convertAppointmentDTOtoEntity(AppointmentDTO appointmentDTO);

    AppointmentDTO convertAppointmentEntityToDTO(AppointmentEntity appointment);
}
