package org.patients.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.patients.models.Appointment;
import org.patients.models.dto.AppointmentDTO;

@Mapper
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    Appointment convertAppointmentDTOtoEntity(AppointmentDTO appointmentDTO);

    AppointmentDTO convertAppointmentEntityToDTO(Appointment appointment);
}
