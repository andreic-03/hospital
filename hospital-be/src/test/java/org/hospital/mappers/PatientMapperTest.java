package org.hospital.mappers;

import org.hospital.api.model.patient.PatientResponseModel;
import org.hospital.persistence.entity.PatientEntity;
import org.hospital.persistence.entity.UserEntity;
import org.hospital.utils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientMapperTest {

    private static PatientMapper patientMapper = new PatientMapperImpl();

    private static TestUtils testUtils;

    @BeforeAll()
    public static void setUp() {
        testUtils = new TestUtils();
    }

    @Test
    void testPatientMapper() {
        UserEntity user = testUtils.createUserEntity();
        PatientEntity patient = testUtils.createPatientWithUser(user);

        PatientResponseModel patientResponseModel = patientMapper.toPatientModel(patient);

        assertAll(() -> {
            assertEquals(patient.getFirstName(), patientResponseModel.getFirstName());
            assertEquals(patient.getLastName(), patientResponseModel.getLastName());
            assertEquals(patient.getCnp(), patientResponseModel.getCnp());
            assertEquals(patient.getGender(), patientResponseModel.getGender());
            assertEquals(patient.getCitizenship(), patientResponseModel.getCitizenship());
            assertEquals(patient.getDateOfBirth(), patientResponseModel.getDateOfBirth());
            assertEquals(patient.getAddress(), patientResponseModel.getAddress());
            assertEquals(patient.getCity(), patientResponseModel.getCity());
            assertEquals(patient.getCountry(), patientResponseModel.getCountry());
            assertEquals(patient.getCounty(), patientResponseModel.getCounty());
            assertEquals(patient.getMaritalStatus(), patientResponseModel.getMaritalStatus());
        });
    }
}
//TODO add tests for appointments

// for new appointmentIds
//    @Test
//    public void testToMedicModel() {
//        MedicEntity medicEntity = new MedicEntity();
//        medicEntity.setMedicId(1L);
//        medicEntity.setFirstName("John");
//        medicEntity.setLastName("Doe");
//
//        AppointmentEntity appointment1 = new AppointmentEntity();
//        appointment1.setAppointmentId(101L);
//        AppointmentEntity appointment2 = new AppointmentEntity();
//        appointment2.setAppointmentId(102L);
//        medicEntity.setAppointments(Arrays.asList(appointment1, appointment2));
//
//        MedicResponseModel medicModel = medicMapper.toMedicModel(medicEntity);
//
//        assertNotNull(medicModel);
//        assertEquals(medicEntity.getMedicId(), medicModel.getMedicId());
//        assertEquals(2, medicModel.getAppointmentIds().size());
//        assertEquals(101L, medicModel.getAppointmentIds().get(0));
//        assertEquals(102L, medicModel.getAppointmentIds().get(1));
//    }