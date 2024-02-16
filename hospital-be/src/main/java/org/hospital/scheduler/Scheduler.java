package org.hospital.scheduler;

import org.hospital.persistence.entity.AppointmentEntity;
import org.hospital.services.AppointmentService;
import org.hospital.services.notification.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    EmailService emailService;

    //TODO Get email of the doctor in order to send email to
//    @Scheduled(cron = "*/10 * * * * *")
    public void sendEmailJob() {
        List<AppointmentEntity> appointments = appointmentService.findAllAppointmentsFromToday();

        if (!appointments.isEmpty()) {
            appointments.forEach(appointment -> {
//                emailService.sendEmail("cioropar.andrei3@gmail.com", "test", appointment.getAppointmentDetails());
            });
        }
    }
}
