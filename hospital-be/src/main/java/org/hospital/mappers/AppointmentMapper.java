package org.hospital.mappers;

import org.hospital.api.model.appointment.AppointmentRequestModel;
import org.hospital.api.model.appointment.AppointmentResponseModel;
import org.hospital.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentMapper {

    AppointmentEntity toAppointmentEntity(AppointmentRequestModel appointmentRequestModel);

    @Mapping(source = "medic.medicId", target = "medicId")
    @Mapping(source = "patient.patientId", target = "patientId")
    AppointmentResponseModel toAppointmentModel(AppointmentEntity appointment);
}
