package mate.academy.spring.initializer;

import java.util.Set;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.CinemaHall;
import mate.academy.spring.model.Movie;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.CinemaHallService;
import mate.academy.spring.service.MovieService;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;
    private final RoleService roleService;

    public DataInitializer(UserService userService,
                           ShoppingCartService shoppingCartService,
                           MovieService movieService,
                           CinemaHallService cinemaHallService,
                           RoleService roleService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setName(Role.RoleName.USER);
        roleService.add(userRole);
        User user = new User();
        user.setEmail("max@max.com");
        user.setPassword("maximMaxim12345");
        user.setRoles(Set.of(adminRole, userRole));
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        Movie movie = new Movie();
        movie.setTitle("Movie");
        movie.setDescription("Movie Description");
        movieService.add(movie);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("Nice hall");
        cinemaHallService.add(cinemaHall);
    }
}
