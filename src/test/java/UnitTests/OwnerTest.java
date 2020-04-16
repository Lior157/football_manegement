package UnitTests;

import DataLayer.dataManager;
import LogicLayer.*;
import ServiceLayer.Controller;
import ServiceLayer.OwnerService;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class OwnerTest {

    dataManager dataManager = new dataManager();
    Controller controller = new Controller(null,null);

    User ownerUser = new User("alonalas@post.bgu.ac.il","123","alona");
    User u1 = new User("a@b@c","1234","alonalas");
    User u2 = new User("amir@post", "234","amirLasry");

    OwnerService os = new OwnerService(controller);
    Owner own = new Owner(ownerUser,"Alona the queen",dataManager);
    Page p = new Page();
    Team team = new Team("Blumfield", "Hapoel",p, dataManager);
    User u3 = new User("abc", "aaa","haermi");
    User u4 = new User("Hogwarts.com","12345678","Albus Dumbeldore");


    /**
     * the 5 following tests are testing UC 6.1.1
     * the 5 following tests assumes owner's account is connected allready
     * the 5 following tests assumes the following process in the presentation layer:
     *
     * the owner push the button "insert new asset to team"
     * GUI shows 4 following buttons:
     * -insert new manager
     * -insert new player
     * -insert new coach
     * -insert new stadium
     * after choosing the desiered option, each of the 4 following tests corresponds to the selected option from above
     *
     *THE FOLLOWING TEST is testing also UC 6.4
     *
     */
    @Test
    public void testOwnerAddManager() {

        initializeSystem();

        String userName = "alonalas";
        String email = "a@b@c";
        String teamName = "Hapoel";
        Map<Manager.Permission,Boolean> permissionBooleanMap = new HashMap<>();
        permissionBooleanMap.put(Manager.Permission.playerDeletion,true);
        permissionBooleanMap.put(Manager.Permission.coachDeletion, true);
        permissionBooleanMap.put(Manager.Permission.coachAddition, true);
        permissionBooleanMap.put(Manager.Permission.playerAddition, true);


        try {
            os.insertNewManager(own,teamName, "Yossi", userName,email, permissionBooleanMap);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        try {
            os.insertNewManager(own,teamName,"Yossi",userName,email,permissionBooleanMap);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(),"The selected user is allready nominated as manager in this team");
        }



    }


    @Test
    public void testOwnerAddCoach() {

        initializeSystem();

        //"amir@post", "234","amirLasry"
        String userName = "amirLasry";
        String email = "amir@post";
        String teamName = "Hapoel";

        try {
            os.insertNewCoach(own,teamName,"Dani","university of life","fitting rooms", userName,email);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        assertTrue(team.getCoachList().size() > 0);
        assertNotNull(team.getRoleHolder(userName,email));
    }

    @Test
    public void testOwnerAddPlayer() {

        initializeSystem();

        String userName = "alonalas";
        String email = "a@b@c";
        String teamName = "Hapoel";

        try {
            os.insertNewPlayer(own,teamName,"David","GoalKeeper",1,2,1995,userName,email);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        assertTrue(team.getPlayerList().size() > 0);
        assertNotNull(team.getRoleHolder(userName,email));

    }

    @Test
    public void testOwnerAddStadium() {

        initializeSystem();

        String stadium = "Sami offer"; // when button "Manager" is chosen
        String teamName = "Hapoel";

        try {
            os.insertNewStadium(own,teamName,stadium);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        assertEquals(team.getStadium(),stadium);
    }

    @Test
    public void checkExceptionThrown() {

        initializeSystem();

        boolean thrown = false;
        String message="";

        try {
            check("Hapoel", "aaa", "bbb");
        }
        catch (IOException e) {
            thrown = true;
            message = e.getMessage();
        }
        assertTrue(thrown);
        assertEquals(message,"The chosen user does not exist, please insert valid inputs");
        thrown=false;

        try {
            check("Beitar", "a@b@c", "alonalas");
        }
        catch (IOException e) {
            thrown = true;
            message = e.getMessage();
        }
        assertTrue(thrown);
        assertEquals(message,"The chosen team does not exist, please choose a valid team");

    }

    public void check(String teamName, String email, String userName) throws IOException {

        Map<Manager.Permission,Boolean> permissionBooleanMap = new HashMap<>();
        permissionBooleanMap.put(Manager.Permission.playerDeletion,true);
        permissionBooleanMap.put(Manager.Permission.coachDeletion, true);
        permissionBooleanMap.put(Manager.Permission.coachAddition, true);
        permissionBooleanMap.put(Manager.Permission.playerAddition, true);
        os.insertNewManager(own,teamName, "xxx", userName,email,permissionBooleanMap);
    }

    @Test
    /**
     * The following 2 test are testing U.C 6.1.2
     * tests asset deletion
     * asset is one of the following options: Player/Coach/Manager
     * assumes that the user have chosen the button "RoleHolder-> chosen role from above)
     * you need to change the creation of the RoleHolder datatype to which you want to check according to the chosen data type
     * from above
     */
    public void testOwnerDeleteAsset() {

        testOwnerAddPlayer(); // player Exists in the system
        RoleHolder toDelete = new Player(u1,"GoalKeeper",team,"David",null,p);

        String userName = "alonalas";
        String email = "a@b@c";
        String teamName = "Hapoel";

        try {
            os.deleteRoleHolder(own,teamName,userName,email,toDelete);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertTrue(team.getPlayerList().size() == 0);
        assertNull(team.getRoleHolder(userName,email));

    }

    @Test
    /**
     * test stadium deletion
     * assumes that the user have chosen the button "stadium"
     */
    public void testOwnerDeleteStadium() {

        initializeSystem();

        String teamName = "Hapoel";
        String stadium = "Blumfield";

        try {
            os.deleteStadium(own,teamName,stadium);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(team.getStadium(),"NO_STADIUM");

    }


    @Test
    /**
     * The following test is for UC 6.1.3
     * Assumes that the user selected an existing entity to change, selected its attributes to be changed
     * and inserted an alternative attribute
     * @throws IOException in the following cases:
     * -team does not exist in the owner's teamList
     * -selected user does not exist in the team's roleHolder list
     * -one of the selected attribute does not exist in the selected entity
     */
    public void testOwnerUpdateAsset() {

        testOwnerAddPlayer(); // player Exists in the system
        // u1 = ("a@b@c","1234","alonalas");
        RoleHolder toUpdate = team.getRoleHolder(u1.getUserName(),u1.getEmail());

        String teamName = "Hapoel";

        Map<String, String> attributes = new HashMap<>();
        String newAttributeName = "Ballerina";
        String attributeName = "position";
        attributes.put(attributeName,newAttributeName);

        try {
            os.updateRoleHolder(own,teamName, toUpdate, attributes);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(team.getPlayer(u1).getPosition(), newAttributeName);

        attributes.clear();
        attributes.put("harta","barta");

        try {
            os.updateRoleHolder(own,teamName, toUpdate, attributes);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(),"Invalid attribute selected: harta");
        }

        testOwnerAddCoach();
        toUpdate = team.getRoleHolder(u2.getUserName(),u2.getEmail());
        attributes.clear();
        attributes.put("Qualification","Harry Potter expert");

        try {
            os.updateRoleHolder(own,teamName, toUpdate, attributes);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(team.getCoach(u2).getQualification(), "Harry Potter expert");

    }


    ////////////////////////////////////////////////////////////////uc2

    /**
     * test U.C 6.2
     * current owner nominates another owner as an addition owner of a requiered team
     * @throws IOException in the following cases:
     * -chosen owner is allrady nominated
     * -chosen user does not exist in the system
     * -owner does not owe the requeierd team.
     */
    @Test
    public void testOwnerNominateOwner() {

        initializeSystem();
        String name = "newOnwerChen";

        try {
            os.nominateNewOwner(own,team.getName(),u2,name);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertNotNull(team.getOwner(u2));
        assertTrue(u2.getRoles().size() == 1);
        assertTrue(team.getOwnerList().size() == 2);

        try {
            os.nominateNewOwner(own,team.getName(),u2,name);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(),"User is allready nominated as owner in this team");
        }

    }

    ///////////////////////////////////////////// uc3

    /**
     *Test UC 6.3
     */
    @Test
    public void testRemoveOwnership() {

        testOwnerNominateOwner(); // the owner "own" nominated the user u2 to be a new owner of Team team
        int roles = 0;
        try {
            os.insertNewPlayer(own,team.getName(),"harry","seeker",1,2,1987,u2.getUserName(),u2.getEmail());
            roles = u2.getRoles().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Role role : u2.getRoles()) {
            if (role instanceof Owner) {
                Owner nominatedOwner = (Owner)role;
                try {
                    os.removeOwnership(own,nominatedOwner, team.getName());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        assertEquals(team.getOwnerList().size() , 1);
        assertNotEquals(roles,u2.getRoles().size());
    }

    @Test
    /**
     * test UC 6.5
     */
    public void testRemoveManagerNomination() {

        initializeSystem();
        testOwnerAddManager();
        testOwnerNominateOwner(); // u1
        Map<Manager.Permission,Boolean> permissionBooleanMap = new HashMap<>();
        try {
            os.insertNewManager(own,team.getName(),"Prof. snape",u3.getUserName(),u3.getEmail(),permissionBooleanMap);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //now there are 2 managers to team
        try {
            Owner anotherOwner = team.getOwner(u2);
            os.deleteRoleHolder(anotherOwner,team.getName(),u3.getUserName(),u3.getEmail(),team.getRoleHolder(u3.getUserName(),u3.getEmail()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(),"The selected manager was not nominated by you");
        }
        try {
            os.deleteRoleHolder(own,team.getName(),u3.getUserName(),u3.getEmail(),team.getRoleHolder(u3.getUserName(),u3.getEmail()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(team.getManagerList().size(),1);
    }

    @Test
    /**
     * The 2 following tests are testing UC6.6.1 and 6.6.2
     */
    public void closeTeamActivity(){

        //after this there are 2 roleholders in the team
        initializeSystem();
        testOwnerNominateOwner();
        testOwnerAddManager();

        try {
            os.closeTeamActivity(own,team);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(dataManager.getAlerts().get(u2).size(), 1);
        assertEquals(dataManager.getAlerts().get(u1).size(),1);
        assertEquals(dataManager.getAlerts().get(u1).get(0).getDescription(), "The team: " + team.getName() + " is closed temporarily");
    }

    @Test
    public void renewTeamActivity(){

        closeTeamActivity();

        try {
            os.renewTeamActivity(own,team);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(dataManager.getAlerts().get(u2).size(), 2);
        assertEquals(dataManager.getAlerts().get(u1).size(),2);
        assertEquals(dataManager.getAlerts().get(u1).get(1).getDescription(), "The team: " + team.getName() + " is open");
    }

    private void initializeSystem() {

        controller.addUser(ownerUser);
        dataManager.addUser(ownerUser);
        dataManager.addUser(u4);
        dataManager.addUser(u3);
        dataManager.addUser(u1);
        dataManager.addUser(u2);
        ownerUser.setRole(own);
        own.addTeam(team);
        team.addOwner(own);

    }

}
