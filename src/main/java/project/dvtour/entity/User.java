package project.dvtour.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "username cannot be blank")
    @NonNull
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "password cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String password;

    @NonNull
    @Column(name = "isAdmin")
    private Boolean isAdmin = false;

    @JsonIgnore
    // Delete user = delete booking corresponding with that user
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Booking> bookings;

    // @JsonIgnore
    // @ManyToMany(cascade = CascadeType.REMOVE)
    // @JoinTable(
    //     name = "tour_user",
    //     joinColumns = @JoinColumn(name ="user_id", referencedColumnName = "id"),
    //     inverseJoinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id")
    // )
    // private Set<Tour> tours;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(isAdmin){
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    
}
