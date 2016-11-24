package dwalldorf.achallenge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AppointmentRating {

    public final static String ID_NAME = "appointment_rating_id";

    @Id
    @Column(name = ID_NAME)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // whatever goes into the rating

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
