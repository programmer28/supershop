package mate.academy.internetshop.model;

public class User {
    private String name;
    private Long id;

    public User(String name) {
        id = GeneratorId.getNewUserId();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" + "name='"
                + name + '\'' + ", id="
                + id + '}';
    }
}
