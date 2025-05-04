package mate.academy.spring.config;

import static mate.academy.spring.model.Role.RoleName.ADMIN;
import static mate.academy.spring.model.Role.RoleName.USER;

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
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/register").anonymous()
                .mvcMatchers(HttpMethod.GET, "/cinema-halls", "/movies",
                        "/movie-sessions/available").hasAnyRole(
                        ADMIN.name(), USER.name())
                .mvcMatchers(HttpMethod.POST, "/cinema-halls", "/movies",
                        "/movie-sessions").hasRole(ADMIN.name())
                .mvcMatchers(HttpMethod.PUT, "PUT: /movie-sessions/**")
                .hasRole(ADMIN.name())
                .mvcMatchers(HttpMethod.DELETE, "DELETE: /movie-sessions/**")
                .hasRole(ADMIN.name())
                .mvcMatchers(HttpMethod.GET, "/orders", "/shopping-carts/by-user")
                .hasRole(USER.name())
                .mvcMatchers(HttpMethod.POST, "/orders/complete")
                .hasRole(USER.name())
                .mvcMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions")
                .hasRole(USER.name())
                .mvcMatchers(HttpMethod.GET, "/users/by-email")
                .hasRole(ADMIN.name())
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
