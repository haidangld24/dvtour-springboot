package project.dvtour.security.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import project.dvtour.entity.User;
import project.dvtour.security.SecurityConstants;
import project.dvtour.security.manager.CustomAuthenticationManager;
import project.dvtour.service.UserService;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    CustomAuthenticationManager customAuthenticationManager;
    UserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return customAuthenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        String token = JWT.create()
                        .withSubject(authResult.getName())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ SecurityConstants.TOKEN_EXPIRATION))
                        .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        User user = userService.getUser(authResult.getName());
        String isAdmin = Boolean.toString(user.getIsAdmin());
        String userId = Long.toString(user.getId());
        response.addHeader(SecurityConstants.AUTHORIZATION,SecurityConstants.BEARER + token);
        response.addHeader("isAdmin", isAdmin);
        response.addHeader("userId", userId);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

    
}
