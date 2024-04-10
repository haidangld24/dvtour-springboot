package project.dvtour.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.*;
import project.dvtour.entity.Tour;
import project.dvtour.entity.User;
import project.dvtour.exception.EntityNotFoundException;
import project.dvtour.repository.TourRepository;

@AllArgsConstructor
@Service
public class TourServiceImpl implements TourService{
    private TourRepository tourRepository;

    @Override
    public void deleteTour(Long id) {
        tourRepository.deleteById(id);        
    }

    @Override
    public Tour getTour(Long id) {
        Optional<Tour> tour = tourRepository.findById(id);
        return unwrapTour(tour, id);
    }

    

    @Override
    public List<Tour> getTours() {
        return (List<Tour>) tourRepository.findAll();
    }

    @Override
    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
    }

    
    @Override
    public Tour updateTour(Tour updatedTour) {
        Tour existingTour = unwrapTour(tourRepository.findById(updatedTour.getId()), null);
        BeanUtils.copyProperties(updatedTour, existingTour, "id","bookings","users");
        return tourRepository.save(existingTour);
    }

    

    // @Override
    // public Set<User> getUsersByTour(Long id) {
    //     Tour tour = unwrapTour(tourRepository.findById(id), id);
    //     return tour.getUsers();
    // }

    



    @Override
    public Long getTotalToursByType(String type) {
        return tourRepository.countByType(type);
    }

    @Override
    public List<Tour> getTop8FurthestDepartureDaysByType(String type, int page) {
        int pageSize = 8;
        Pageable pageRequest = PageRequest.of(page -1, pageSize);
        return tourRepository.findTop8ToursByTypeOrderByDepartureDateDesc(type, pageRequest);
    }

    static Tour unwrapTour(Optional<Tour> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id,Tour.class);
    }
}