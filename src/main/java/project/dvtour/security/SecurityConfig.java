package project.dvtour.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.*;
import project.dvtour.security.filter.AuthenticationFilter;
import project.dvtour.security.filter.ExceptionHandlerFilter;
import project.dvtour.security.filter.JWTAuthorizationFilter;
import project.dvtour.security.manager.CustomAuthenticationManager;
import project.dvtour.service.UserService;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    CustomAuthenticationManager customAuthenticationManager;
    UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager, userService);
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        http.cors().and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                // .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**")).hasAuthority("ROLE_ADMIN")
                .requestMatchers(AntPathRequestMatcher.antMatcher("/**/admin/**")).hasAuthority("ROLE_ADMIN")
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/user/register", "POST")).permitAll()
                // .requestMatchers(new AntPathRequestMatcher("/user/**", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**","OPTIONS")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/tour/**", "GET")).permitAll()
                // .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS,"/**")).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(userService), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}
