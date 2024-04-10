package project.dvtour.web;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.*;
import project.dvtour.entity.Tour;
import project.dvtour.entity.User;
import project.dvtour.exception.ErrorResponse;
import project.dvtour.repository.UserRepository;
import project.dvtour.service.UserService;

@AllArgsConstructor
@RestController
public class UserController {
    UserService userService;
    UserRepository userRepository;
    
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUser(id),HttpStatus.OK);
    }

    @PostMapping("/user/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        if(userService.usernameExists(user.getUsername())){
            ErrorResponse error = new ErrorResponse(Arrays.asList("Username has been used"));
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("admin/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }

    // @GetMapping("/user/{id}/tours")
    // public ResponseEntity<Set<Tour>> getPurchasedTours(@PathVariable Long id, Authentication authentication){
    //     System.out.println("User authorities: " + authentication.getAuthorities());
    //     return new ResponseEntity<>(userService.getPurchasedTours(id), HttpStatus.OK);
    // }
    
}