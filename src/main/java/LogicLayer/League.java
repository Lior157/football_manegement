package LogicLayer;

import DataLayer.IDataManager;
import DataLayer.dataManager;
import ServiceLayer.IController;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class League implements Serializable {

    public enum LeagueType{
        MAJOR_LEAGUE, SECOND_LEAGUE, LEAGUE_A,LEAGUE_B, LEAGUE_C
    }

    private LeagueType type;
    private List<Referee> refereeList;
    private List<Season> seasonList;
    private Map<Season,Policy> policyList;
    private String name;

    public League( LeagueType type, List<Referee> refereeList, List<Season> seasonList, Map<Season, Policy> policyList) {
        this.type = type;
        this.refereeList = refereeList;
        this.seasonList = seasonList;
        this.policyList = policyList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof League){
            return this.getType() == ((League) obj).getType() ;
        }
        return false;
    }

    private static IDataManager data(){
        return DataComp.getInstance();
    }

    public League(LeagueType leagueType){
        this.type = leagueType;
        data().addLeague(this);
    }

    /**
     * id: League@1
     * check if League already exist
     * @param leagueType
     * @return League if existing , null if not
     */
    public static League checkIfLeagueExist(LeagueType leagueType){
        return data().SearchLeagueByType(leagueType);
    }

    /**
     * id: League@2
     * show all existing Leagues
     * @return all system leagues
     */
    public static List<League> ShowAllLeagues(){
        return data().getLeagueList();
    }

    /**
     * id: League@3
     * add new league
     * @param leagueType
     * @return if success/unsuccessful operation
     * @throws Exception if league type illegal
     */
    public static boolean addLeague(League.LeagueType leagueType) throws IOException{
        if(leagueType==null){
            throw new IOException("illegal league type");
        }
        League league= League.checkIfLeagueExist(leagueType);
        if(league != null){
            return false ;
        }
        data().addLeague(new League(leagueType));
        return true;
    }

    /**
     * id: League@5
     *  number of Dates correct - for pre-algorithm check
     * @param numberOfGamesPerTeam
     * @param numberOfTeam
     * @return number of games
     */
    public static int numberOfNeededDates(int numberOfGamesPerTeam , int numberOfTeam){
        int numberOfGames = 0 ;
        for(int i=numberOfTeam-1 ; i > 0  ; i--){
            numberOfGames = numberOfGames +i;
        }
        return numberOfGamesPerTeam*numberOfGames ;
    }

    /**
     * id: League@4
     * schedule games in season
     * @param numberOfGamesPerTeam
     * @return number of schduled Games
     */
    public int gamescheduling(int numberOfGamesPerTeam , Season season , List<String[]> allPossiableTimes){
        if (numberOfGamesPerTeam == 0 || season == null) return 0;
        List<Team> teams = Team.getAllTeamsInLeague(this);
        Team[] teamsArray = new Team[teams.size()];
        if(allPossiableTimes.size()<numberOfNeededDates(numberOfGamesPerTeam , teams.size())) return 0;
            teams.toArray(teamsArray);
        List<Referee> referees = Referee.legalRefereesForLeague(this, season);
        Referee[] refereesArray = new Referee[referees.size()];
        referees.toArray(refereesArray);
        int nextTime = 0;
        int a = 0, b = 0;
        int games_counter = 0;
        int refereeSchecule = 0;
        for (int interations = 0; interations < numberOfGamesPerTeam; interations++) {
            for (int i = teams.size() - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (i % 2 == 0) {
                        a = i;
                        b = j;
                    } else {
                        a = j;
                        b = i;
                    }
                    Game game = new Game(season, teamsArray[a], teamsArray[b], null, refereesArray[refereeSchecule%(refereesArray.length-1)], null,
                            allPossiableTimes.get(nextTime)[0], allPossiableTimes.get(nextTime)[1], allPossiableTimes.get(nextTime)[2]);
                    nextTime++;
                    refereeSchecule++;
                    teamsArray[a].addHomeGame(game);
                    teamsArray[b].addAwayGame(game);
                    System.out.println(teamsArray[a].getName() + "-" + teamsArray[b].getName());
                    games_counter++;
                }
            }
        }
        return games_counter;
    }
    public LeagueType getType() {
        return type;
    }

    public void setType(LeagueType type) {
        this.type = type;
    }

    public List<Referee> getRefereeList() {
        return refereeList;
    }

    public void setRefereeList(List<Referee> refereeList) {
        this.refereeList = refereeList;
    }

    public List<Season> getSeasonList() {
        return seasonList;
    }

    public void setSeasonList(List<Season> seasonList) {
        this.seasonList = seasonList;
    }

    public Map<Season, Policy> getPolicyList() {
        return policyList;
    }

    public void setPolicyList(Map<Season, Policy> policyList) {
        this.policyList = policyList;
    }
}
