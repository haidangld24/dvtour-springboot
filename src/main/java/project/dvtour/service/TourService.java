package project.dvtour.service;

import java.util.List;
import java.util.Set;

import project.dvtour.entity.Tour;
import project.dvtour.entity.User;

public interface TourService {
    Tour getTour(Long id);
    List<Tour> getTours();
    Tour saveTour(Tour tour);
    void deleteTour(Long id);
    Tour updateTour(Tour tour);
    // Set<User> getUsersByTour(Long id);
    List<Tour> getTop8FurthestDepartureDaysByType(String type, int page);
    Long getTotalToursByType(String type);
}
