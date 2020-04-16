package LogicLayer;

import DataLayer.dataManager;
import ServiceLayer.IController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Team {

    enum teamStatus{
        activityClosed, activityOpened, ActivityOpened
    }

    private dataManager dataManager;
    private String name;
    private String stadium;
    private Page page;
    private List<Player> playerList;
    private List<Manager> managerList;
    private List<Owner> ownerList;
    private List<Game> away;
    private List<Game> home;
    private League league;
    private List<Coach> coachList;
    private List<RoleHolder> roleHolders;
    private teamStatus status;

    public Team(String stadium, String name, Page page, dataManager dataManager) {

        this.name = name;
        this.stadium = stadium;
        this.page = page;
        this.dataManager = dataManager;
        managerList = new LinkedList<>();
        playerList = new LinkedList<>();
        ownerList = new LinkedList<>();
        away = new LinkedList<>();
        home = new LinkedList<>();
        coachList = new LinkedList<>();
        roleHolders = new LinkedList<>();
        status = teamStatus.ActivityOpened;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addOwner(Owner owner) {
        if (!this.ownerList.contains(owner))
            ownerList.add(owner);
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public Manager getManager(User user) {
        for (Manager manager : managerList) {
            if (manager.getUser().equals(user))
                return manager;
        }
        return null;
    }

    public Player getPlayer(User user) {
        for (Player player : playerList) {
            if (player.getUser().equals(user))
                return player;
        }
        return null;
    }

    public Coach getCoach(User user) {
        for (Coach coach : coachList) {
            if (coach.getUser().equals(user))
                return coach;
        }
        return null;
    }

    public Owner getOwner(User user) {
        for (Owner owner : ownerList) {
            if (owner.getUser().equals(user))
                return owner;
        }
        return null;
    }

    public List<Manager> getManagerList() {
        return managerList;
    }

    public void setManager(Manager manager) {
        if (!managerList.contains(manager))
            managerList.add(manager);
    }

    /**
     * id: Team@1
     * returns a RoleHolder that belongs to the requiered team
     * search is made by user name and email
     * @param userName
     * @param email
     * @return
     */
    public RoleHolder getRoleHolder(String userName,String email) {

        User user = dataManager.getUserByMail(userName,email);
        if (user!=null) {
            for (RoleHolder roleHolder : this.roleHolders) {
                if (roleHolder.getUser().equals(user))
                    return roleHolder;
            }
        }
        return null;
    }

    public List<RoleHolder> getRoleHolders() {
        return roleHolders;
    }

    public List<Owner> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<Owner> ownerList) {
        this.ownerList = ownerList;
    }

    public List<Game> getAway() {
        return away;
    }

    public void setAway(List<Game> away) {
        this.away = away;
    }

    public List<Game> getHome() {
        return home;
    }

    public void setHome(List<Game> home) {
        this.home = home;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void setPlayer(Player player) {
        if (!playerList.contains(player))
        playerList.add(player);
    }

    public void setCoach(Coach coach) {
        if (!coachList.contains(coach))
            coachList.add(coach);
    }

    public List<Coach> getCoachList() {
        return coachList;
    }

    public void setCoachList(List<Coach> coachList) {
        this.coachList = coachList;
    }


    /**
     * id: Team@2
     * changes the status of the team to close if the owner is the real owner of the team
     * @param owner
     */
    public void changeTeamActivity(Owner owner, teamStatus newStatus) throws IOException {
        if (ownerList.contains(owner)) {
            String date = LocalDate.now().toString();
            Alert alert;
            for(RoleHolder roleHolder: getRoleHolders()){
                if (roleHolder instanceof Manager || roleHolder instanceof Owner) {
                    if (newStatus == teamStatus.activityClosed)
                        alert = new Alert(roleHolder.getUser(), "The team: " + this.getName() + " is closed temporarily",date);
                    else // Opened
                        alert = new Alert(roleHolder.getUser(), "The team: " + this.getName() + " is open", date);
                    dataManager.addAlert(roleHolder.getUser(),alert);
                }
            }
            setStatus(newStatus);
        }
        else {
            throw new IOException("This team can not be closed without official owner premission");
        }
    }

    public void setStatus(teamStatus status) {
        this.status = status;
    }

    public teamStatus getStatus(){
        return status;
    }
}
