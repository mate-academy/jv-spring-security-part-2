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

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/cinema-halls").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/cinema-halls").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/movies").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/movies").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/movie-sessions/").hasRole("ADMIN")
                .antMatchers(
                        HttpMethod.GET, "/movie-sessions/available").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/movie-sessions/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/register").anonymous()
                .antMatchers(HttpMethod.POST, "/orders/complete").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/orders").hasAnyRole("USER", "ADMIN")
                .antMatchers(
                        HttpMethod.PUT,
                                "/shopping-carts/movie-sessions").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/shopping-carts/by-user").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/by-email").hasAnyRole("USER", "ADMIN")
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
