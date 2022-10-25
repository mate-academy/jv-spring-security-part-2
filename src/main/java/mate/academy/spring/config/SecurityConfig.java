package mate.academy.spring.config;

import mate.academy.spring.model.Role.RoleName;
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
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.GET, "/cinema-halls").authenticated()
                .antMatchers(HttpMethod.GET, "/movies").authenticated()
                .antMatchers(HttpMethod.GET, "/movie-sessions/available")
                .authenticated()
                .antMatchers(HttpMethod.POST, "/cinema-halls")
                .hasRole(String.valueOf(RoleName.ADMIN))
                .antMatchers(HttpMethod.POST, "/movies")
                .hasRole(String.valueOf(RoleName.ADMIN))
                .antMatchers(HttpMethod.POST, "/movie-sessions")
                .hasRole(String.valueOf(RoleName.ADMIN))
                .antMatchers(HttpMethod.PUT, "/movie-sessions/{id}")
                .hasRole(String.valueOf(RoleName.ADMIN))
                .antMatchers(HttpMethod.DELETE, "/movie-sessions/{id}")
                .hasRole(String.valueOf(RoleName.ADMIN))
                .antMatchers(HttpMethod.GET, "/users/by-email")
                .hasRole(String.valueOf(RoleName.ADMIN))
                .antMatchers(HttpMethod.GET, "/orders")
                .hasRole(String.valueOf(RoleName.USER))
                .antMatchers(HttpMethod.POST, "/orders/complete")
                .hasRole(String.valueOf(RoleName.USER))
                .antMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions")
                .hasRole(String.valueOf(RoleName.USER))
                .antMatchers(HttpMethod.GET, "/shopping-carts/by-user")
                .hasRole(String.valueOf(RoleName.USER))
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
