package dwalldorf.achallenge.model;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = Customer.ID_NAME)
    @NotNull
    private Customer customer;

    @NotNull
    @Column
    private LocalDateTime date;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @PrimaryKeyJoinColumn
    private AppointmentRating rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public AppointmentRating getRating() {
        return rating;
    }

    public void setRating(AppointmentRating rating) {
        this.rating = rating;
    }
}
