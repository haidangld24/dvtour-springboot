package project.dvtour.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.*;
import project.dvtour.entity.Booking;
import project.dvtour.exception.ErrorResponse;
import project.dvtour.service.BookingService;

@AllArgsConstructor
@RestController
@RequestMapping("/booking")
public class BookingController {
    BookingService bookingService;

    @GetMapping("/user/{userId}/tour/{tourId}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long userId, @PathVariable Long tourId){
        return new ResponseEntity<>(bookingService.getBooking(userId, tourId), HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId){
        return new ResponseEntity<>(bookingService.getBookingById(bookingId), HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<Booking>> getUserBooking(@PathVariable Long userId){
        return new ResponseEntity<List<Booking>>(bookingService.getUserBooking(userId),HttpStatus.OK);
    }

    @GetMapping("/tour/{tourId}")
    public ResponseEntity<List<Booking>> getBookingByTourId(@PathVariable Long tourId){
        return new ResponseEntity<List<Booking>>(bookingService.getBookingByTourid(tourId),HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/tour/{tourId}")
    public ResponseEntity<Object> saveBooking(@Valid @RequestBody Booking booking, @PathVariable Long userId, @PathVariable Long tourId){
        if(!bookingService.canBookSlots(booking, tourId)){
            ErrorResponse error = new ErrorResponse(Arrays.asList("Invalid purchaseSlots value. Please choose a smaller number of slots."));
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookingService.saveBooking(booking, userId, tourId), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id ,@RequestBody Booking updatedBooking){
        updatedBooking.setId(id);
        return new ResponseEntity<>(bookingService.updateBooking(updatedBooking), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}/page/{page}")
    public ResponseEntity<List<Booking>> getTop8BookingByUserIdOrderByPurchaseDate(@PathVariable Long userId, @PathVariable int page){
        return new ResponseEntity<List<Booking>>(bookingService.getTop8BookingByUserIdOrderByPurchaseDate(userId, page), HttpStatus.OK);
    }
}
