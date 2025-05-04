package mate.academy.spring.service;

import java.util.Set;
import mate.academy.spring.model.User;

public interface AuthenticationService {
    User register(String email, String password, Set<String> roles);
}
