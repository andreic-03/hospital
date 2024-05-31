package org.hospital.api.model.medic;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hospital.persistence.entity.MedicSpecialization;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class MedicRequestModel {
    private String firstName;
    private String lastName;
    private String gender;
    private String cnp;
    private MedicSpecialization specialization;

}
