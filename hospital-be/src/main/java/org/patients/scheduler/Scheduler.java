package org.patients.scheduler;

import org.patients.models.Appointment;
import org.patients.services.AppointmentService;
import org.patients.services.EmailService;
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
        List<Appointment> appointments = appointmentService.findAllAppointmentsFromToday();

        if (!appointments.isEmpty()) {
            appointments.forEach(appointment -> {
                emailService.sendSimpleMessage("cioropar.andrei3@gmail.com", "test", appointment.getAppointmentDetails());
            });
        }
    }
}
