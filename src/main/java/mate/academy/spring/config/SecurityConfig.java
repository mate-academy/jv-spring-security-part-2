package mate.academy.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers(HttpMethod.GET, "/orders", "/shopping-carts/by-user").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/orders/complete").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/cinema-halls").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/cinema-halls", "/movie-sessions", "/movies",
                        "/users/by-email").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/movie-sessions/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/movie-sessions/available", "/movies")
                .hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
