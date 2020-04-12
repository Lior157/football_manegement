package LogicLayer;

import DataLayer.dataManager;
import ServiceLayer.IController;

import java.util.LinkedList;
import java.util.List;

public class Team {

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

    public Manager getManager(Manager manager) {
        for (Manager manager1 : managerList) {
            if (manager1.equals(manager))
                return manager1;
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
     * ID: 1
     * returns a RoleHolder that belongs to the requiered team of the given owner
     * search is made by user name and email
     * @param owner
     * @param userName
     * @param email
     * @return
     */
    public RoleHolder getRoleHolder(Owner owner, String userName,String email) {

        User user = dataManager.getUserByMail(userName,email);
        if (this.ownerList.contains(owner)) {
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
        if(!playerList.contains(player))
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

}
