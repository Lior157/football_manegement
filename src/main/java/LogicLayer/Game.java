package LogicLayer;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Game {

    private Season season;
    private Team home;
    private Team away;
    private Referee line;
    private Referee main;
    private List<GameEventCalender> gameEventCalender;
    private String date; // format "2019-04-09"
    private String startTime; // format (13:50)
    private String endTime;
    private GameReport gameReport;


    public Game(Season season, Team home, Team away, Referee line, Referee main, List<GameEventCalender> gameEventCalender,String date, String start, String end) {
        this.season = season;
        this.home = home;
        this.away = away;
        this.line = line;
        this.main = main;
        this.gameEventCalender = gameEventCalender;
        this.date=date;
        this.startTime=start;
        this.endTime=end;
        this.gameReport=new GameReport(this);
    }
    public Game(Season season, Team home, Team away, Referee line, Referee main,String date, String start, String end) {
        this.season = season;
        this.home = home;
        this.away = away;
        this.line = line;
        this.main = main;
        this.gameEventCalender = new LinkedList<>();
        this.date=date;
        this.startTime=start;
        this.endTime=end;
        this.gameReport=new GameReport(this);
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
    }

    public Referee getLine() {
        return line;
    }

    public void setLine(Referee line) {
        this.line = line;
    }

    public Referee getMain() {
        return main;
    }

    public void setMain(Referee main) {
        this.main = main;
    }

    public List<GameEventCalender> getGameEventCalender() {
        return gameEventCalender;
    }

    public void setGameEventCalender(List<GameEventCalender> gameEventCalender) {
        this.gameEventCalender = gameEventCalender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void addEventGame(GameEventCalender event){
        gameEventCalender.add(event);
    }


    /**
     * function number: 1
     * prints the game's details
     */
    public void displayDetails(){
        System.out.println("Date: " + getDate());
        System.out.println("Start Time: " + getStartTime());
        System.out.println("End Time: " + getEndTime());
    }

    public GameReport getGameReport() {
        return gameReport;
    }

    public void setGameReport(GameReport gameReport) {
        this.gameReport = gameReport;
    }


    /**
     * function number: 2
     * compare two games
     * @param game2 game we wanyt to compare
     * @return return true if the two games are equal
     */
    public boolean equals(Game game2){
        if(this.getDate().equals(game2.getDate())&& this.getHome().equals(game2.getHome())&&
        this.getAway().equals(game2.getAway())){
            return true;
        }
        else{
            return false;
        }
    }
}
