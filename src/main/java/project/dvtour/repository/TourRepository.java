package project.dvtour.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import project.dvtour.entity.Tour;

public interface TourRepository  extends CrudRepository<Tour,Long>{
    @Query("SELECT t FROM Tour t WHERE t.type = :type ORDER BY t.departureDate DESC")
    List<Tour> findTop8ToursByTypeOrderByDepartureDateDesc(@Param("type") String type, Pageable pageable);
    long countByType(String type);
}
