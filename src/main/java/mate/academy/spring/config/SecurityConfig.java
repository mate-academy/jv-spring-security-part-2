package mate.academy.spring.config;

import mate.academy.spring.model.Role;
import mate.academy.spring.service.impl.CustomUserDetailsService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/register").permitAll()
                .antMatchers(HttpMethod.GET,"/cinema-halls")
                .hasAnyRole(Role.RoleName.ADMIN.name(),
                        Role.RoleName.USER.name())
                .antMatchers(HttpMethod.POST,"/cinema-halls")
                .hasAnyRole(Role.RoleName.ADMIN.name(),
                        Role.RoleName.USER.name())
                .antMatchers(HttpMethod.GET,"/movies").hasAnyRole(Role.RoleName.ADMIN.name(),
                        Role.RoleName.USER.name())
                .antMatchers(HttpMethod.POST,"/movies").hasRole(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/movie-sessions/available")
                .hasAnyRole(Role.RoleName.ADMIN.name(), Role.RoleName.USER.name())
                .antMatchers(HttpMethod.POST,"/movie-sessions")
                .hasRole(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/movie-sessions/{id}")
                .hasRole(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/movie-sessions/{id}")
                .hasRole(Role.RoleName.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/order").hasRole(Role.RoleName.USER.name())
                .antMatchers(HttpMethod.POST,"/order/complete").hasRole(Role.RoleName.USER.name())
                .antMatchers(HttpMethod.PUT,"/shopping-carts/movie-sessions")
                .hasRole(Role.RoleName.USER.name())
                .antMatchers(HttpMethod.GET,"/shopping-carts/by-user")
                .hasRole(Role.RoleName.USER.name())
                .antMatchers(HttpMethod.GET,"/users/by-email").hasRole(Role.RoleName.ADMIN.name())
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
