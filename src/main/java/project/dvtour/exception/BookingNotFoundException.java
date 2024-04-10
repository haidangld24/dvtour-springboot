package project.dvtour.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(Long userId, Long tourId){
        super("The booking with user id: "+userId+" and tour id: "+tourId+"does not exist" );
    }
}
