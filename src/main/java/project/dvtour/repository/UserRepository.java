package project.dvtour.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import project.dvtour.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByUsername(String username);
    
}
