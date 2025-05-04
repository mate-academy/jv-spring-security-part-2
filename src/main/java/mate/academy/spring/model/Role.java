package mate.academy.spring.model;

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

    public enum RoleName {
        ADMIN,
        USER
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass().equals(Role.class)) {
            Role role = (Role) o;
            return this.id.equals(role.id)
                    && this.roleName == role.roleName;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 21;
        result = 51 * result + (id == null ? 0 : id.hashCode());
        result = 51 * result + (roleName == null ? 0 : roleName.hashCode());
        return result;
    }
}
