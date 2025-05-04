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
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(PasswordEncoder passwordEncoder,
                          UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register")
                .permitAll()
                .antMatchers(HttpMethod.GET,
                        "/cinema-halls",
                        "/movies",
                        "/movie-sessions/available")
                .hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,
                        "/cinema-halls",
                        "/movies",
                        "/movie-sessions")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/movie-sessions/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/by-email")
                .hasRole("ADMIN")
                .antMatchers("/orders")
                .hasRole("USER")
                .antMatchers("/shopping-carts/**")
                .hasRole("USER")
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
