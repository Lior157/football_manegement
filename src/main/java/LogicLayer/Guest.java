package LogicLayer;
import DataLayer.IDataManager;
import java.io.Serializable;
import java.util.List;

public class Guest implements Serializable {

    private IDataManager data(){
        return DataComp.getInstance();
    }

    public Guest() { }

    /**
     * id: guest@1
     * creates new user and add its roles a fan
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @return User
     */
    public User createNewUser(String email, String password, String firstName, String lastName){
        User newUser = new User(email, password, firstName, lastName);
        Fan fan = new Fan(newUser,firstName);
        newUser.getRoles().add(fan);
        addNewUser(newUser,true);
        return newUser;
    }

    /**
     * id: guest@2
     * returns user from DB according to arguments
     * @param email
     * @param password
     * @return User
     */
    public User signIn(String email, String password){
        User signedInUser = data().getUser(email, password);
        return signedInUser;
    }

    /**
     * id: guest@3
     * returns true or false due to if email exists
     * @param email
     * @return boolean
     */
    public boolean checkIfEmailExists(String email) {
        return data().checkIfEmailExists(email);
    }

    /**
     * id: guest@4
     *  add new user to DB
     * @param newUser
     * @param isNewUser
     */
    public void addNewUser(User newUser,boolean isNewUser) {
        if (isNewUser && newUser != null){
            data().addNewUser(newUser);
        }
    }

    /**
     * returns all games
     * @return List<Game>
     */
    public List<Game> retrieveGames() {
        return data().getGameList();
    }

    /**
     * returns all players
     * @return List<Player>
     */
    public List<Player> retrievePlayers() {
        return data().getPlayers();
    }

    /**
     * returns all leagues
     * @return List<League>
     */
    public List<League> retrieveLeagues() {
        return data().getLeagueList();
    }

    /**
     * returns all teams
     * @return List<Team>
     */
    public List<Team> retrieveTeams() {
        return data().getTeamList();
    }

    /**
     * returns all seasons
     * @return List<Season>
     */
    public List<Season> retrieveSeasons(){
        return data().getSeasonList();
    }

    /**
     * returns all coaches
     * @return List<Coach>
     */
    public List<Coach> retrieveCoaches() {
        return data().getCoaches();
    }

    /**
     * returns all team owners
     * @return List<Owner>
     */
    public List<Owner> retrieveOwners() {
        return data().getOwners();
    }

    /**
     * returns all team managers
     * @return List<Manager>
     */
    public List<Manager> retrieveManagers() {
        return data().getManagers();
    }

    /**
     * returns all users with the same name
     * @param name
     * @return
     */
    public List<User> SearchUserByName(String name) {
        return data().searchUserByName(name);
    }

    /**
     * returns all leagues with the same league type
     * @param league
     * @return
     */
    public List<League> SearchLeagueByName(String league) {
        return data().searchLeagueByName(league);
    }

    /**
     * returns all teams with same name
     * @param team
     * @return
     */
    public List<Team> SearchTeamByName(String team) {
        return  data().searchTeamByName(team);
    }
}
