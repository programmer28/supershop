package mate.academy.internetshop.model;

public class Role {
    private Long id;
    private RoleName roleName;

    public Role() {
        id = GeneratorId.getNewRoleId();
    }

    public Role(RoleName roleName) {
        this();
        this.roleName = roleName;
    }

    public enum RoleName {
        USER, ADMIN;
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

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }
}
