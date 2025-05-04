package mate.academy.spring.controller;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import mate.academy.spring.model.CinemaHall;
import mate.academy.spring.model.Movie;
import mate.academy.spring.model.MovieSession;
import mate.academy.spring.service.CinemaHallService;
import mate.academy.spring.service.MovieService;
import mate.academy.spring.service.MovieSessionService;
import mate.academy.spring.service.OrderService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InitialDataController {
    private final CinemaHallService cinemaHallService;
    private final MovieService movieService;
    private final MovieSessionService movieSessionService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final OrderService orderService;

    public InitialDataController(CinemaHallService cinemaHallService,
                                 MovieService movieService,
                                 MovieSessionService movieSessionService,
                                 ShoppingCartService shoppingCartService,
                                 UserService userService,
                                 OrderService orderService) {
        this.cinemaHallService = cinemaHallService;
        this.movieService = movieService;
        this.movieSessionService = movieSessionService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public String injectInitialData() {
        Movie firstMovie = new Movie();
        firstMovie.setTitle("American Beauty");
        firstMovie.setDescription("...take a closer look.");
        movieService.add(firstMovie);
        Movie secondMovie = new Movie();
        secondMovie.setTitle("Tetris");
        secondMovie.setDescription("A game that you can't stop playing. "
                + "A story that can't be imagined.");
        movieService.add(secondMovie);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(50);
        firstHall.setDescription("4DX");
        cinemaHallService.add(firstHall);
        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(200);
        secondHall.setDescription("IMAX");
        cinemaHallService.add(secondHall);

        MovieSession session = new MovieSession();
        session.setMovie(movieService.get(1L));
        session.setCinemaHall(cinemaHallService.get(1L));
        session.setShowTime(LocalDate.now().atTime(14,30));
        movieSessionService.add(session);
        session = new MovieSession();
        session.setMovie(movieService.get(1L));
        session.setCinemaHall(cinemaHallService.get(2L));
        session.setShowTime(LocalDate.now().atTime(18,15));
        movieSessionService.add(session);
        session = new MovieSession();
        session.setMovie(movieService.get(2L));
        session.setCinemaHall(cinemaHallService.get(1L));
        session.setShowTime(LocalDate.now().atTime(16,45).plusDays(1));
        movieSessionService.add(session);
        session = new MovieSession();
        session.setMovie(movieService.get(2L));
        session.setCinemaHall(cinemaHallService.get(2L));
        session.setShowTime(LocalDate.now().atTime(20,0).plusDays(1));
        movieSessionService.add(session);

        shoppingCartService.registerNewShoppingCart(userService.get(1L));
        shoppingCartService.registerNewShoppingCart(userService.get(2L));

        shoppingCartService.addSession(movieSessionService.get(1L), userService.get(1L));
        shoppingCartService.addSession(movieSessionService.get(3L), userService.get(1L));
        shoppingCartService.addSession(movieSessionService.get(2L), userService.get(2L));
        shoppingCartService.addSession(movieSessionService.get(4L), userService.get(2L));

        orderService.completeOrder(shoppingCartService.getByUser(
                userService.findByEmail("john@me.com").orElseThrow(
                        () -> new NoSuchElementException("User with current email not found"))));

        return "All data are injected!";
    }
}
