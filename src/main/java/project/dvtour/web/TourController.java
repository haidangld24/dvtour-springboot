package project.dvtour.web;

import java.util.List;
import java.util.Set;

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
import project.dvtour.entity.Tour;
import project.dvtour.entity.User;
import project.dvtour.service.TourService;

@AllArgsConstructor
@RestController
@RequestMapping("/tour")
public class TourController {
    TourService tourService;

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTour(@PathVariable Long id){
        return new ResponseEntity<>(tourService.getTour(id),HttpStatus.OK);
    }

    // @GetMapping("/{id}/users")
    // public ResponseEntity<Set<User>> getUsersByTour(@PathVariable Long id){
    //     return new ResponseEntity<>(tourService.getUsersByTour(id), HttpStatus.OK);
    // }

    @GetMapping("/all")
    public ResponseEntity<List<Tour>> getTours(){
        return new ResponseEntity<>(tourService.getTours(), HttpStatus.OK);
    }

    @GetMapping("/{type}/all")
    public ResponseEntity<Long> getAllToursByType(@PathVariable String type){
        return new ResponseEntity<Long>(tourService.getTotalToursByType(type), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Tour> createTour(@Valid @RequestBody Tour tour){
        tourService.saveTour(tour);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Long id ,@RequestBody Tour updatedTour){
        updatedTour.setId(id);
        return new ResponseEntity<>(tourService.updateTour(updatedTour), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTour(@PathVariable Long id){
        tourService.deleteTour(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/type/{type}/page/{page}")
    public ResponseEntity<List<Tour>> getTop8FurthestDepartureDaysByType(@PathVariable String type, @PathVariable int page){
        
        return new ResponseEntity<>(tourService.getTop8FurthestDepartureDaysByType(type, page), HttpStatus.OK);
    }
}
