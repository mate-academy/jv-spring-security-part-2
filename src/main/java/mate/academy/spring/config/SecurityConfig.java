package mate.academy.spring.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.GET, "/cinema-halls").hasAnyRole(ROLE_USER, ROLE_ADMIN)
                .antMatchers(HttpMethod.POST, "/cinema-halls").hasRole(ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/movies").hasAnyRole(ROLE_USER, ROLE_ADMIN)
                .antMatchers(HttpMethod.POST, "/movies").hasAnyRole(ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/movie-sessions/available")
                .hasAnyRole(ROLE_USER, ROLE_ADMIN)
                .antMatchers(HttpMethod.POST, "/movie-sessions").hasRole(ROLE_ADMIN)
                .antMatchers(HttpMethod.PUT, "/movie-sessions/{id}").hasRole(ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/{id}").hasRole(ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/orders").hasRole(ROLE_USER)
                .antMatchers(HttpMethod.POST, "/orders/complete").hasRole(ROLE_USER)
                .antMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions").hasRole(ROLE_USER)
                .antMatchers(HttpMethod.GET, "/shopping-carts/by-user").hasRole(ROLE_USER)
                .antMatchers(HttpMethod.GET, "/users/by-email").hasRole(ROLE_ADMIN)
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
