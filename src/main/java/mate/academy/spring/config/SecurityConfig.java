package mate.academy.spring.config;

import mate.academy.spring.model.Role;
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
    public static final String ADMIN = Role.RoleName.ADMIN.name();
    public static final String USER = Role.RoleName.USER.name();
    private UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
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
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.GET, "/cinema-halls", "/movies",
                        "/movie-sessions/available").hasAnyRole(USER, ADMIN)
                .antMatchers(HttpMethod.POST, "/cinema-halls", "/movie-sessions",
                        "/movies").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, "/movie-sessions/{id}").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/{id}").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/orders").hasRole(USER)
                .antMatchers(HttpMethod.POST, "/orders/complete").hasRole(USER)
                .antMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions",
                        "/shopping-carts/by-user").hasRole(USER)
                .antMatchers(HttpMethod.GET, "/users/by-email").hasRole(ADMIN)
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
