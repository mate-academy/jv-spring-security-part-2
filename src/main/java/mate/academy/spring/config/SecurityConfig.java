package mate.academy.spring.config;

import mate.academy.spring.model.Role;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.UserService;
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
    private final PasswordEncoder encoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder encoder,
                          RoleService roleService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register", "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/cinema-halls", "/movies",
                        "/movie-sessions/available").hasAnyAuthority(Role.RoleName
                        .ADMIN.name(), Role.RoleName.USER.name())
                .antMatchers(HttpMethod.GET, "/users/by-email")
                .hasAnyAuthority(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/shopping-carts/by-user", "/orders")
                .hasRole(Role.RoleName.USER.name())
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/{id}")
                .hasAnyAuthority(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/movie-sessions/{id}")
                .hasAnyAuthority(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/cinema-halls", "/movies",
                        "/movie-sessions").hasAnyAuthority(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions")
                .hasAnyAuthority(Role.RoleName.USER.name())
                .antMatchers(HttpMethod.POST, "/orders/complete")
                .hasAnyAuthority(Role.RoleName.USER.name())
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
