package project.dvtour.service;

import java.util.List;

import project.dvtour.entity.Booking;

public interface BookingService {
    Booking getBooking(Long userId, Long tourId);
    Booking getBookingById(Long bookingId);
    List<Booking> getUserBooking(Long userId);
    List<Booking> getBookingByTourid(Long tourId);
    Booking saveBooking(Booking booking, Long userId, Long tourId);
    Booking updateBooking(Booking booking);
    void deleteBooking(Long bookingId);
    Boolean canBookSlots(Booking booking, Long tourId);
    List<Booking> getTop8BookingByUserIdOrderByPurchaseDate(Long userId, int page);
}
