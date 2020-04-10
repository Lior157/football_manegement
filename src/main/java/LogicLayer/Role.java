package LogicLayer;

public abstract class Role  {

    private User user;

    public Role(User user) {
        this.user = user;
    }

    public Role() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}