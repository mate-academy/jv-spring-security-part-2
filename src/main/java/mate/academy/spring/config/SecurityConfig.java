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
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        String adminRole = Role.RoleName.ADMIN.name();
        String userRole = Role.RoleName.USER.name();
        http
                .authorizeRequests()
                .antMatchers("/users/by-email").hasAuthority(adminRole)
                .antMatchers("/shopping-carts/**").hasAuthority(userRole)
                .antMatchers("/orders/**").hasAuthority(userRole)
                .antMatchers(HttpMethod.GET, "/movie-sessions/**").permitAll()
                .antMatchers(HttpMethod.POST, "/movie-sessions/**").hasAuthority(adminRole)
                .antMatchers(HttpMethod.PUT, "/movie-sessions/**").hasAuthority(adminRole)
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/**").hasAuthority(adminRole)
                .antMatchers(HttpMethod.GET, "/movies").permitAll()
                .antMatchers(HttpMethod.POST, "/movies").hasAuthority(adminRole)
                .antMatchers(HttpMethod.GET, "/cinema-halls/**").permitAll()
                .antMatchers(HttpMethod.POST, "/cinema-halls/**").hasAuthority(adminRole)
                .antMatchers("/register").hasAuthority(adminRole)
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
