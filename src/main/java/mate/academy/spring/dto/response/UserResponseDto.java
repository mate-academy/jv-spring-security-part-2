package mate.academy.spring.dto.response;

import java.util.Set;

public class UserResponseDto {
    private Long id;
    private String email;
    private Set<Long> authoritieIds;

    public Set<Long> getAuthorities() {
        return authoritieIds;
    }

    public void setAuthorities(Set<Long> authorities) {
        this.authoritieIds = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
