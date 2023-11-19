package org.hospital.mappers;

import org.hospital.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.hospital.api.model.AppointmentDTO;

@Mapper
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    AppointmentEntity convertAppointmentDTOtoEntity(AppointmentDTO appointmentDTO);

    AppointmentDTO convertAppointmentEntityToDTO(AppointmentEntity appointment);
}
