package org.hospital.mappers;

import org.hospital.api.model.medic.MedicRequestModel;
import org.hospital.api.model.medic.MedicResponseModel;
import org.hospital.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.hospital.persistence.entity.MedicEntity;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AppointmentMapper.class})
public interface MedicMapper {
    MedicEntity toMedicEntity(MedicRequestModel medicRequestModel);

    @Mapping(source = "appointments", target = "appointmentIds", qualifiedByName = "appointmentsToIds")
    MedicResponseModel toMedicModel(MedicEntity medic);

    @Named("appointmentsToIds")
    default List<Long> mapAppointmentsToIds(List<AppointmentEntity> appointments) {
        return appointments.stream()
                .map(AppointmentEntity::getAppointmentId)
                .collect(Collectors.toList());
    }
}
