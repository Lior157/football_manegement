package LogicLayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import ServiceLayer.*;

public class User {

    private String email;
    private String password;
    private String userName;
    //private IController system;
    private List<Role> roles;

    public User(User other) {
        this.email = other.password;
        this.password = other.password;
        this.userName = other.userName;
        this.roles = other.getRoles();
    }

    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.roles = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email) &&
                password.equals(user.password) &&
                userName.equals(user.userName) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, userName);
    }

    /**
     * id: User@2
     * add new Role
     * @param role
     * @return true if added successfully , else if already exists
     */
    public boolean addRole(Role role){
        if(!roles.contains(role)) {
            roles.add(role);
            return true;
        }
        return false ;
    }

    /**
     * id: User@3
     * remove Role
     * @param role
     * @return Object that been removed , null if object removed before
     */
    public Object removeRole(Role role){
        if(roles.contains(role)) {
            return roles.remove(role);
        }
        return null ;
    }


    /**
     * id: User@
     * find RefereeRoleIfExist  , else return null
     * @return
     */
    public Referee ifUserRoleIncludeReferee(){
        for(Role r : roles){
            if(r instanceof Referee){
                return (Referee)r ;
            }
        }
        return null;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRole(Role role){
        //if (!roles.contains(role))
            this.roles.add(role);
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
