package mate.academy.spring.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private RoleName roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public enum RoleName {
        ADMIN,
        USER
    }

    @Override
    public boolean equals(Object role) {
        if (this == role) {
            return true;
        }
        if (role == null) {
            return false;
        }
        if (role.getClass().equals(Role.class)) {
            Role current = (Role) role;
            return this.roleName == current.roleName;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }
}
