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
                .antMatchers("/register", "/inject").permitAll()
                .antMatchers(HttpMethod.GET, "/movies",
                        "/movie-sessions", "/cinema-halls").hasAnyRole(Role.RoleName.ADMIN.name(),
                Role.RoleName.USER.name())
                .antMatchers(HttpMethod.POST,"/movies", "/movie-sessions", "/cinema-halls")
                .hasRole(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/by-email").hasRole(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/complete",
                        "/shopping-carts/movie-sessions").hasRole(Role.RoleName.USER.name())
                .antMatchers(HttpMethod.PUT, "/movies-sessions")
                .hasRole(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/movies-sessions")
                .hasRole(Role.RoleName.ADMIN.name())
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
