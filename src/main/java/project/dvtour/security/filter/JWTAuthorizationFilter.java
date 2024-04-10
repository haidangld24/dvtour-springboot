package project.dvtour.security.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.dvtour.security.SecurityConstants;
import project.dvtour.service.UserService;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter{

    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith(SecurityConstants.BEARER)){
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.replaceAll(SecurityConstants.BEARER, "");
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                        .build()
                        .verify(token)
                        .getSubject();

        UserDetails userDetails = userService.loadUserByUsername(user);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user,null, authorities);        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}
