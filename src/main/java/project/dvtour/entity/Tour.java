package project.dvtour.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Getter
@Setter
// @AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tour")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name can not be blank")
    @NonNull
    @Column(name ="name", nullable = false)
    private String name;

    @NotBlank(message = "Destination can not be blank")
    @NonNull
    @Column(name ="destination", nullable = false)
    private String destination;

    @NotBlank(message = "Type can not be blank")
    @NonNull
    @Column(name ="type", nullable = false)
    private String type;

    @NonNull
    @Column(name ="departure_date", nullable = false)
    private LocalDate departureDate;

    @NonNull
    @Column(name ="duration", nullable = false)
    private String duration;

    @NonNull
    @Column(name ="max_slot", nullable = false)
    private Integer maxSlot;

    @NonNull
    @Column(name ="remaining_slot", nullable = false)
    private Integer remainingSlot;

    @NonNull
    @Column(name ="price", nullable = false)
    private BigDecimal  price;

    @JsonIgnore
    // Delete tour = delete booking corresponding with that tour
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    // @JsonIgnore
    // @ManyToMany(cascade = CascadeType.ALL)
    // @JoinTable(
    //     name = "tour_user",
    //     joinColumns = @JoinColumn(name ="tour_id", referencedColumnName = "id"),
    //     inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    // )
    // private Set<User> users;
}
