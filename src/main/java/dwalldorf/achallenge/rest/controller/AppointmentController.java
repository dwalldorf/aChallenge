package dwalldorf.achallenge.rest.controller;

import dwalldorf.achallenge.model.Appointment;
import dwalldorf.achallenge.model.Customer;
import dwalldorf.achallenge.rest.exception.NotFoundException;
import dwalldorf.achallenge.service.AppointmentService;
import dwalldorf.achallenge.service.CustomerService;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Inject
    private AppointmentService appointmentService;

    @Inject
    private CustomerService customerService;

    @PostMapping("/{customerId}")
    public ResponseEntity<Appointment> createAppointment(
            @PathVariable final Integer customerId,
            @RequestBody Appointment appointment)
            throws NotFoundException {

        Optional<Customer> customer = customerService.findById(customerId);
        if (!customer.isPresent()) {
            throw new NotFoundException();
        }
        appointment.setCustomer(customer.get());

        appointment = appointmentService.save(appointment);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.findAll();
    }

    @GetMapping("/next_week")
    public List<Appointment> getNextWeeksAppointments() {
        return appointmentService.getNextWeeksAppointments();
    }

    @GetMapping("/{customerId}/next")
    public Appointment getNextAppointmentByCustomer(@PathVariable final Integer customerId) throws NotFoundException {
        Optional<Customer> customer = customerService.findById(customerId);
        if (!customer.isPresent()) {
            throw new NotFoundException();
        }

        return appointmentService.getNextAppointmentByCustomer(customer.get());
    }

    @GetMapping("/{customerId}/last")
    public Appointment getLastAppointmentByCustomer(@PathVariable final Integer customerId) throws NotFoundException {
        Optional<Customer> customer = customerService.findById(customerId);
        if (!customer.isPresent()) {
            throw new NotFoundException();
        }

        return appointmentService.getLastAppointmentByCustomer(customer.get());
    }
}
