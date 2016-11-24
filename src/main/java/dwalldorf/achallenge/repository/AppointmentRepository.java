package dwalldorf.achallenge.repository;

import dwalldorf.achallenge.model.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByDateBetween(LocalDateTime from, LocalDateTime to);

    Appointment findFirstByCustomerIdAndDateAfterOrderByDateAsc(Integer customerId, LocalDateTime date);

    Appointment findFirstByCustomerIdAndDateBeforeOrderByDateDesc(Integer customerId, LocalDateTime date);
}
