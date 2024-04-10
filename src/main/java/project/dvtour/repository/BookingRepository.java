package project.dvtour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import project.dvtour.entity.Booking;

public interface BookingRepository extends CrudRepository<Booking,Long>{
    Optional<Booking> findByUserIdAndTourId(Long userId, Long TourId);
    List<Booking> findByUserId(Long userId);
    List<Booking> findByTourId(Long tourId);
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.purchaseDate DESC")
    List<Booking> findTop8BookingByUserIdOrderByPurchaseDate(@Param("userId") Long userId, Pageable pageable);
}
