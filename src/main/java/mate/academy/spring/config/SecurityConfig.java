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
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/cinema-halls", "/movies",
                        "/movie-sessions")
                .hasRole(Role.RoleName.ADMIN.toString())
                .antMatchers("/register", "/cinema-halls", "/movies",
                        "/movie-sessions/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/movie-sessions/**")
                .hasRole(Role.RoleName.ADMIN.toString())
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/**")
                .hasRole(Role.RoleName.ADMIN.toString())
                .antMatchers("/orders", " /shopping-carts/by-user")
                .hasRole(Role.RoleName.USER.toString())
                .antMatchers(HttpMethod.POST,"/orders/complete",
                        "/shopping-carts/movie-sessions")
                .hasRole(Role.RoleName.USER.toString())
                .antMatchers("/inject", "/users/by-email")
                .hasRole(Role.RoleName.ADMIN.toString())
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
