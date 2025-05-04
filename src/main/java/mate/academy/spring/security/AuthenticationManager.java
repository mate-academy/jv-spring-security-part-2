package mate.academy.spring.security;

import mate.academy.spring.model.User;
import org.springframework.security.core.token.Token;

public interface AuthenticationManager {
    public User authenticate(Token token);
}
