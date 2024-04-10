package project.dvtour.service;

import project.dvtour.entity.Booking;
import project.dvtour.entity.Tour;
import project.dvtour.entity.User;
import project.dvtour.exception.BookingNotFoundException;
import project.dvtour.repository.BookingRepository;
import project.dvtour.repository.TourRepository;
import project.dvtour.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.*;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private TourRepository tourRepository;

    @Override
    public Booking getBooking(Long userId, Long tourId) {
        Optional<Booking> booking = bookingRepository.findByUserIdAndTourId(userId, tourId);
        return unwrappedBooking(booking, userId, tourId);
    }

    

    @Override
    public Booking getBookingById(Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return unwrappedBooking(booking, 404L, 404L);
    }



    @Override
    public List<Booking> getUserBooking(Long userId) {
        List<Booking> booking = bookingRepository.findByUserId(userId);
        return booking;
    }

    


    @Override
    public List<Booking> getBookingByTourid(Long tourId) {
        return bookingRepository.findByTourId(tourId);
    }



    @Override
    public Boolean canBookSlots(Booking booking, Long tourId) {
        Tour tour = TourServiceImpl.unwrapTour(tourRepository.findById(tourId), tourId);
        if(booking.getPurchaseSlots() <= tour.getRemainingSlot()){
            return true;
        }
        return false;
    }



    @Override
    public Booking saveBooking(Booking booking, Long userId, Long tourId) {
        User user = UserServiceImpl.unwrapUser(userRepository.findById(userId), userId);
        Tour tour = TourServiceImpl.unwrapTour(tourRepository.findById(tourId), tourId);
        //because tour_user is a bidirectional relationship, that tour will also be part of the user's list of tours.
        // <=> add user to tour = add tour to user
        // tour.getUsers().add(user);
        booking.setUser(user);
        booking.setTour(tour);
        tour.setRemainingSlot(tour.getRemainingSlot() - booking.getPurchaseSlots());
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Booking updatedBooking) {
        Booking existingBooking = unwrappedBooking(bookingRepository.findById(updatedBooking.getId()), 
                                    null,null);
        //method from org.springframework.beans.BeanUtils to copy the non-null properties from the updatedBooking object to the existingBooking object.
        //We exclude the "id" property from being copied to avoid accidentally modifying the ID of the existing booking.

        //update the remainingSlot of the existingTour by adjusting it based on 
        //the difference between the slots purchased in the original booking and the updated booking.
        existingBooking.getTour().setRemainingSlot(existingBooking.getTour().getRemainingSlot() + 
              existingBooking.getPurchaseSlots() - updatedBooking.getPurchaseSlots()  
        );
        BeanUtils.copyProperties(updatedBooking, existingBooking, "id","tour","user");
        // Long tourId = existingBooking.getTour().getId();
        // Tour existingTour = TourServiceImpl.unwrapTour(tourRepository.findById(tourId),null);
        // System.out.println(existingTour.getRemainingSlot());
        return bookingRepository.save(existingBooking);
    }

    

    @Override
    public void deleteBooking(Long bookingId) {
        Booking booking = unwrappedBooking(bookingRepository.findById(bookingId), null, null);
        // User user = booking.getUser();
        Tour tour = booking.getTour();
        // user.getTours().remove(tour);
        // tour.getUsers().remove(user);
        tour.setRemainingSlot(tour.getRemainingSlot() + booking.getPurchaseSlots());
        bookingRepository.deleteById(bookingId);
        
    }

    

    @Override
    public List<Booking> getTop8BookingByUserIdOrderByPurchaseDate(Long userId, int page) {
        int pageSize = 8;
        Pageable pageRequest = PageRequest.of(page-1, pageSize);
        return bookingRepository.findTop8BookingByUserIdOrderByPurchaseDate(userId, pageRequest);
    }



    public Booking unwrappedBooking(Optional<Booking> entity, Long userId, Long tourId){
        if(entity.isPresent()) return entity.get();
        else throw new BookingNotFoundException(userId, tourId);
    }
    
}
