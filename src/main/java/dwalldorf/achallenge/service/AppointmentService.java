package dwalldorf.achallenge.service;

import dwalldorf.achallenge.model.Appointment;
import dwalldorf.achallenge.model.AppointmentRating;
import dwalldorf.achallenge.model.Customer;
import dwalldorf.achallenge.repository.AppointmentRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Inject
    private AppointmentRepository appointmentRepository;

    public Appointment save(Appointment appointment) {
        if (appointment.getId() == null) {
            appointment.setRating(new AppointmentRating());
        }

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment getNextAppointmentByCustomer(final Customer customer) {
        return appointmentRepository.findFirstByCustomerIdAndDateAfterOrderByDateAsc(customer.getId(), getNow());
    }

    public Appointment getLastAppointmentByCustomer(final Customer customer) {
        return appointmentRepository.findFirstByCustomerIdAndDateBeforeOrderByDateDesc(customer.getId(), getNow());
    }

    public List<Appointment> getNextWeeksAppointments() {
        LocalDateTime from = getNow();
        LocalDateTime to = getNow().plusDays(7);

        return appointmentRepository.findByDateBetween(from, to);
    }

    private LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}
