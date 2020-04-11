package ServiceLayer;

import LogicLayer.Game;
import LogicLayer.GameEventCalender;
import LogicLayer.GameReport;
import LogicLayer.Referee;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class RefereeService extends AUserService {
    private Referee referee;
    private static final Logger testLogger = Logger.getLogger(RefereeService.class);


    public RefereeService(Referee referee) {
        this.referee = referee;
    }

    /**
     * UC 10.1
     * display the Referee's details
     */
    @Override
    public void showDetails(){
        System.out.println("Name of referee: " + referee.getName());
        System.out.println("Qualification: " + referee.getQualification());
    }

    public Referee getReferee() {
        return referee;
    }

    /**
     * UC 10.1
     * change the details of the referee
     * @param newName - the new name we want to save for the referee
     * @param newCualif - the new qualification for the referee
     * @throws IOException
     */
    @Override
    public void changeDetails(String newName, String newCualif) throws IOException {
        referee.setName(newName);
        referee.setQualification(newCualif);
    }


    /**
     * UC 10.2
     * displays the referee's games
     * @throws IOException
     */
    @Override
    public void displayGames() throws IOException {
        System.out.println("main games:");
        int i=1;
        for(Game game : referee.getMain()){
            System.out.println("Game number " + i);
            System.out.println(game.toString());
        }
        i=1;
        System.out.println("line games: ");
        for(Game game : referee.getLine()){
            System.out.println("Game number " + i);
            game.displayDetails();
        }
    }


    /**
     * UC 10.3
     * adds a new gameEvnet to the system while the game is active
     * @param game - the game we want to add an event for
     * @param description - the description of the event
     * @param eventType - event type (enum)
     * @throws IOException
     */
    @Override
    public void addGameEvent(Game game,String description, String eventType) throws IOException {
        LocalDate date=LocalDate.now();
        LocalTime now=LocalTime.now();
        if(game.getLine().equals(referee) || game.getMain().equals(referee)){
            if(date.compareTo(LocalDate.parse(game.getDate()))==0){
                if(now.isBefore(LocalTime.parse(game.getEndTime()))&& now.isAfter(LocalTime.parse(game.getStartTime()))){
                    GameEventCalender event = new GameEventCalender(game,now.toString(),date.toString(),eventType,description,now.getMinute());
                    game.addEventGame(event);
                    String propertiesPath = "log4j.properties";
                    PropertyConfigurator.configure(propertiesPath);
                    testLogger.info("Added new game event");
                }
            }
        }
    }


    /**
     * UC 10.4.1
     * adds a new game event to the system after the game ended by a main referee
     * @param game - the game we want to add an event for
     * @param description - the description of the event
     * @param eventType - event type (enum)
     * @throws IOException
     */
    @Override
    public void addGameEventAfterGame(Game game,String description, String eventType) throws IOException {
        LocalDate date=LocalDate.now();
        LocalTime now=LocalTime.now();
        if(game.getMain().equals(referee)){
            if(date.compareTo(LocalDate.parse(game.getDate()))==0){
                if(now.minusHours(5).isBefore(LocalTime.parse(game.getEndTime()))){
                    GameEventCalender event = new GameEventCalender(game,now.toString(),date.toString(),eventType,description,now.getMinute());
                    game.addEventGame(event);
                    String propertiesPath = "log4j.properties";
                    PropertyConfigurator.configure(propertiesPath);
                    testLogger.info("Added new game event");
                }
                else{
                    System.out.println("Its been more then 5 hours, tou cen't edit the gameEvent");
                }
            }
        }
    }


    /**
     * UC 10.4.2
     * creates game report for a specific game by main referee
     * @param game - the game we want to create a report for
     * @param description - description of the report
     * @throws IOException
     */
    @Override
    public void createGameReport(Game game, String description)throws IOException {
        if(referee.getMain().contains(game)){
            GameReport gameReport= new GameReport(game,description);
            game.setGameReport(gameReport);
            String propertiesPath = "log4j.properties";
            PropertyConfigurator.configure(propertiesPath);
            testLogger.info("Added new game report");
        }
    }
}