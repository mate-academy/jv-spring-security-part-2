package mate.academy.spring.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Enumerated(value = EnumType.STRING)
    private RoleName roleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(getRoleId(),
                role.getRoleId()) && getRoleName() == role.getRoleName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleId(), getRoleName());
    }

    @Override
    public String toString() {
        return "Role{roleId=%d, roleName=%s}"
                .formatted(roleId, roleName);
    }

    public enum RoleName {
        ADMIN,
        USER
    }
}
