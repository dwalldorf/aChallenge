package dwalldorf.achallenge.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dwalldorf.achallenge.model.Appointment;
import dwalldorf.achallenge.model.AppointmentRating;
import dwalldorf.achallenge.model.Customer;
import dwalldorf.achallenge.repository.AppointmentRepository;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveChecksForIdAndSetsRating() throws Exception {
        Appointment appointmentMock = Mockito.mock(Appointment.class);
        when(appointmentMock.getId()).thenReturn(null);

        service.save(appointmentMock);

        verify(appointmentMock).getId();
        verify(appointmentMock).setRating(any(AppointmentRating.class));
    }

    @Test
    public void testGetNextWeeksAppointments() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowInSevenDays = LocalDateTime.now().plusDays(7);

        ArgumentCaptor<LocalDateTime> fromCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> toCaptor = ArgumentCaptor.forClass(LocalDateTime.class);

        service.getNextWeeksAppointments();

        verify(appointmentRepository).findByDateBetween(fromCaptor.capture(), toCaptor.capture());

        assertTrue(fromCaptor.getValue().isAfter(now));
        assertTrue(toCaptor.getValue().isAfter(nowInSevenDays));
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");

        return customer;
    }
}