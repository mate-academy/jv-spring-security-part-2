package mate.academy.spring.config;

import mate.academy.spring.model.RoleName;
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

    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder encoder) {
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
                .antMatchers("/register").permitAll()
                .antMatchers(HttpMethod.POST,"/movies/*")
                .hasAuthority(RoleName.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/cinema-halls/*")
                .hasAuthority(RoleName.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/movie-sessions/*")
                .hasAuthority(RoleName.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/movie-sessions/*")
                .hasAuthority(RoleName.ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/movie-sessions/*")
                .hasAuthority(RoleName.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/users/by-email")
                .hasAuthority(RoleName.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/cinema-halls")
                .hasAnyAuthority(RoleName.ADMIN.name(), RoleName.USER.name())
                .antMatchers(HttpMethod.GET,"/movies")
                .hasAnyAuthority(RoleName.ADMIN.name(), RoleName.USER.name())
                .antMatchers(HttpMethod.GET,"/movie-sessions/available")
                .hasAnyRole(RoleName.ADMIN.name(), RoleName.USER.name())
                .antMatchers(HttpMethod.GET,"/orders")
                .hasAuthority(RoleName.USER.name())
                .antMatchers(HttpMethod.POST,"/orders/*")
                .hasAuthority(RoleName.USER.name())
                .antMatchers(HttpMethod.PUT,"/shopping-carts/movie-sessions")
                .hasAuthority(RoleName.USER.name())
                .antMatchers(HttpMethod.GET,"/shopping-carts/by-user")
                .hasAuthority(RoleName.USER.name())
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
