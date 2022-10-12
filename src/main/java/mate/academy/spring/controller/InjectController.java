package mate.academy.spring.controller;

import java.time.LocalDateTime;
import java.util.Random;
import mate.academy.spring.model.CinemaHall;
import mate.academy.spring.model.Movie;
import mate.academy.spring.model.MovieSession;
import mate.academy.spring.service.CinemaHallService;
import mate.academy.spring.service.MovieService;
import mate.academy.spring.service.MovieSessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    static final int NUMBER_OF_MOVIES = 8;
    static final int NUMBER_OF_HALLS = 4;
    static final int NUMBER_OF_SESSIONS = 10;
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;
    private final MovieSessionService movieSessionService;

    public InjectController(MovieService movieService,
                            CinemaHallService cinemaHallService,
                            MovieSessionService movieSessionService) {
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
        this.movieSessionService = movieSessionService;
    }

    @GetMapping("/data")
    public String injectData() {
        for (int i = 0; i < NUMBER_OF_MOVIES; i++) {
            Movie movie = new Movie();
            movie.setTitle("Movie " + i + " title");
            movie.setDescription("Description of movie number " + i);
            movieService.add(movie);
        }
        for (int i = 0; i < NUMBER_OF_HALLS; i++) {
            CinemaHall cinemaHall = new CinemaHall();
            cinemaHall.setCapacity(100 * i);
            cinemaHall.setDescription("Cinema hall for " + (i * 100) + " peoples.");
            cinemaHallService.add(cinemaHall);
        }

        Random rnd = new Random();
        int today = LocalDateTime.now().getDayOfMonth();
        for (int i = 0; i < NUMBER_OF_SESSIONS; i++) {
            Integer movieId = rnd.ints(1, NUMBER_OF_MOVIES).iterator().next();
            Integer cinemaId = rnd.ints(1, NUMBER_OF_HALLS).iterator().next();
            int day = rnd.ints(today, (today + 10) < 31 ? (today + 10) : 30).iterator().next();
            int hour = rnd.ints(10, 22).iterator().next();
            LocalDateTime dateTime = LocalDateTime.of(2022, 10, day, hour, 0);
            MovieSession movieSession = new MovieSession();
            movieSession.setMovie(movieService.get(Long.valueOf(movieId)));
            movieSession.setCinemaHall(cinemaHallService.get(Long.valueOf(cinemaId)));
            movieSession.setShowTime(dateTime);
            movieSessionService.add(movieSession);
        }
        return "Added to DB movies, cinema halls, movie sessions!";
    }
}
