package org.hospital.api.model.medic;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hospital.persistence.entity.MedicSpecialization;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class MedicResponseModel {
    private Long medicId;
    private String firstName;
    private String lastName;
    private String gender;
    private String cnp;
    private MedicSpecialization specialization;
    private List<Long> appointmentIds;

}
