package project.dvtour.security.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import lombok.*;
import project.dvtour.entity.User;
import project.dvtour.service.UserService;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager{
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.getUser(authentication.getName());
        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
            throw new BadCredentialsException("Incorrect password!");
        }
        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());
    }

    
}
