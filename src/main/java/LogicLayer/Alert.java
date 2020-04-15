package LogicLayer;

import ServiceLayer.IController;

import java.util.Date;

public class Alert {

    private User user;
    private IController system;
    private String description;
    String date;
    // hour is missing

    public Alert(User user, String description, String date) {
        this.user = user;
        this.description = description;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IController getSystem() {
        return system;
    }

    public void setSystem(IController system) {
        this.system = system;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
