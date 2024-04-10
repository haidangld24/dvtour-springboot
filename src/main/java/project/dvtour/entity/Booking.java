package project.dvtour.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id","tour_id"})
})
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "purchase_slots")
    private Integer purchaseSlots;

    @NonNull
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @NonNull
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private Tour tour;
    
}
