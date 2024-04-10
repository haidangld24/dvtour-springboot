package project.dvtour.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.*;
import project.dvtour.entity.Tour;
import project.dvtour.entity.User;
import project.dvtour.exception.EntityNotFoundException;
import project.dvtour.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserServiceImpl  implements UserService{
    private UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public Boolean usernameExists(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.isPresent();
    }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username); 
        return unwrapUser(user, 404L);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // @Override
    // public Set<Tour> getPurchasedTours(Long id) {
    //     User user = getUser(id);
    //     return user.getTours();
    // }

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }
    
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = unwrapUser(userRepository.findByUsername(username), null);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    static User unwrapUser(Optional<User> entity, Long id ){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }

}
