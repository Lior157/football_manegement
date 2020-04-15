package DataLayer;
import LogicLayer.*;
//import com.sun.org.apache.xml.internal.security.encryption.ReferenceList;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.List;
import java.util.Map;

public class dataManager implements IDataManager {

    private List<Guest> guestsList;
    private List<User> userList;
    private Map<User, List<LogicLayer.Alert>> Alerts;
    private Map<User, List<Complaint>> complaints;
    private List<League> leagueList;
    private List<Season> seasonList;
    private List<Team> teamList;
    private List<Page> pageList;
    private LinkedList<Referee> RefereeList;
    private static final Logger systemLoger = Logger.getLogger(dataManager.class);


    public dataManager() {
        this.guestsList =  new LinkedList<>();
        this.userList = new LinkedList<>();
        this.Alerts = new HashMap<>();
        this.complaints = new HashMap<>();
        this.leagueList = new LinkedList<>();
        this.seasonList = new LinkedList<>();
        this.pageList = new LinkedList<>();
        this.RefereeList = new LinkedList<>();
    }



    public User getUserByPassword(String userName, String password){
        for (User user : userList){
            if (user.getUserName().equals(userName)&&user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public User getUserByMail(String userName, String email){
        for (User user : userList){
            if (user.getUserName().equals(userName)&&user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        if(!userList.contains(user))
            userList.add(user);
    }

    public List<Guest> getGuestsList() {
        return guestsList;
    }

    public void setGuestsList(List<Guest> guestsList) {
        this.guestsList = guestsList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Map<User, List<LogicLayer.Alert>> getAlerts() {
        return Alerts;
    }

    public void setAlerts(Map<User, List<Alert>> alerts) {
        Alerts = alerts;
    }

    public Map<User, List<Complaint>> getComplaint() {
        return complaints;
    }

    public void setComplaint(Map<User, List<Complaint>> complaint) {
        this.complaints = complaint;
    }

    public List<League> getLeagueList() {
        return leagueList;
    }

    /**
     * id: dataManager@1
     * Search League by league type
     * @param leagueType
     * @return League if existing
     */
    public League SearchLeagueByType(League.LeagueType leagueType) {
        for (League league:
             leagueList) {
            if(league.getType() == leagueType){
                return  league;
            }
        }
        return null;
    }

    /**
     * id: dataManager@2
     * add New League To Data
     * @param league to add
     */
    public void addLeague(League league){
        if(SearchLeagueByType(league.getType())==null) {
            leagueList.add(league);
            systemLoger.info("league been added , type:" + league.getType());
        }
    }

    /**
     * id: dataManager@3
     * Search Season by start and end dates
     * @param start date of season
     * @param End date of season
     * @return Season if found, else null
     */
    public Season SearchSeason(String start , String End) {
        for (Season season:
                seasonList) {
            if(season.getEnd().equals(End) && season.getStart().equals(start)  ){
                return  season;
            }
        }
        return null;
    }

    /**
     *id: dataManager@4
     * add new season to data
     * @param season season to add
     */
    public void addSeason(Season season){
        if(SearchSeason(season.getStart() , season.getEnd())==null) {
            seasonList.add(season);
            systemLoger.info("Season been added , linked to League:" + " , Start date:" + season.getStart() +
                    " , End date:" + season.getEnd());
        }else if(SearchSeason(season.getStart() , season.getEnd()).getLeagueList().contains(season.getLeagueList())){
            systemLoger.info("Season Linked to existing League:" + " , Start date:" + season.getStart() +
                    " , End date:" + season.getEnd());
        }
    }

    /**
     * id: dataManager@5
     * add New Referee To Data
     * @param referee
     * @return if added successfully, if not -> already contains the element
     */
    public boolean addReferee(Referee referee){
        if(! RefereeList.contains(referee)){
            RefereeList.add(referee);
            systemLoger.info("new Referee been added , belong to user : "+ referee.getUser().getUserName());
            return true;
        }
        return false;
    }

    /**
     * id: dataManager@6
     * @param user
     * @param alert
     */
    public void addAlert(User user,Alert alert) {
        if (!Alerts.containsKey(user)) {
            List<Alert>alerts = new LinkedList<>();
            alerts.add(alert);
            Alerts.put(user,alerts);
        }
        else
            Alerts.get(user).add(alert);
    }

    /**
     *
     * @param referee
     * @return
     */
    public boolean removeReferee(Referee referee){
        if(RefereeList.contains(referee)){
            RefereeList.remove(referee);
            systemLoger.info("new Referee been removed , belong to user : "+ referee.getUser().getUserName());
            return true;
        }
        return false;
    }
    public void setLeagueList(List<League> leagueList) {
        this.leagueList = leagueList;
    }

    public List<Season> getSeasonList() {
        return seasonList;
    }

    public void setSeasonList(List<Season> seasonList) {
        this.seasonList = seasonList;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }
}


