package LogicLayer;

import DataLayer.IDataManager;
import DataLayer.dataManager;
import ServiceLayer.IController;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Season {

    private IController system;
    private Date start;
    private Date end;
    private List<Game> gameList;
    private List<League> leagueList;
    private Map<League, Policy> Policies;
    private static IDataManager data = DataComp.getInstance();

    public Season(IController system, Date start, Date end, List<Game> gameList, List<League> leagueList, Map<League, Policy> policies) {
        this.system = system;
        this.start = start;
        this.end = end;
        this.gameList = gameList;
        this.leagueList = leagueList;
        Policies = policies;
    }

    public Season(Date start, Date end, League league){
        this.setStart(start);
        this.setEnd(end);
        leagueList = new LinkedList<League>();
        this.leagueList.add(league);
    }
    /**
     * id: Season@1
     * @param start date of the season
     * @param end date of the season
     * @param league linked to Season
     * @return new created Season
     * @throws IOException if season already exists
     */
    public static Season addSeason(Date start ,Date end,League league) throws IOException {
        Season season =  data.SearchSeason(start,end);
        if(season == null){
            season = new Season(start,end,league);
            data.addSeason(season);
        }
        else if(season.getLeagueList().contains(league)){
            throw new IOException("Season already exist");
        }else {
            season.leagueList.add(league);
        }
        return season;
    }

    /**
     * id: Season@2
     * show all existing Seasons
     * @return all system Seasons
     */
    public static List<Season> ShowAllSeasons(){
        return data.getSeasonList();
    }
    public IController getSystem() {
        return system;
    }

    public void setSystem(IController system) {
        this.system = system;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public List<League> getLeagueList() {
        return leagueList;
    }

    public void setLeagueList(List<League> leagueList) {
        this.leagueList = leagueList;
    }

    public Map<League, Policy> getPolicies() {
        return Policies;
    }

    public void setPolicies(Map<League, Policy> policies) {
        Policies = policies;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Season){
            return ((Season)obj ).getStart().equals(this.getStart()) &&
                    ((Season)obj ).getEnd().equals(this.getEnd());
        }
        return false;
    }
}
