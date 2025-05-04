package mate.academy.spring.model;

import java.util.Arrays;
import java.util.NoSuchElementException;
import javax.persistence.Column;
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
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private RoleName roleName;

    public Role() {
    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
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

    public enum RoleName {
        ADMIN, USER;

        public static RoleName getByRoleName(String roleName) {
            return Arrays.stream(RoleName.values()).filter(rn -> rn.name().equals(roleName))
                    .findFirst().orElseThrow(() -> new NoSuchElementException(
                            "Role with name (" + roleName + ") doesn't exist"));
        }
    }
}
