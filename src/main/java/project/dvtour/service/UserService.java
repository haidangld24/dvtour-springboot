package project.dvtour.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;

import project.dvtour.entity.Tour;
import project.dvtour.entity.User;

public interface UserService extends UserDetailsService{
    User getUser(Long id);
    User getUser(String username);
    Boolean usernameExists(String username);
    User saveUser(User user);
    void deleteUser(Long id);
    List<User> getUsers();
    // Set<Tour> getPurchasedTours(Long id);
}
