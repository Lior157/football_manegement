package UnitTests;

import DataLayer.dataManager;
import LogicLayer.*;
import ServiceLayer.Controller;
import ServiceLayer.ManagerService;
import ServiceLayer.OwnerService;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ManagerTest {

    DataLayer.dataManager dataManager = new dataManager();
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

    ManagerService ms = new ManagerService(controller);



    @Test
    /**
     * tests uc 6.4 in the in the aspect of manager permissions.
     * testing the exceptions thrown either.
     * assumes that in the GUI the manager selects a player/coach to delete and the Player/Coach are given already
     */
    public void testPermissionsOfManager() {

        initializeSystem();
        Map<Manager.Permission,Boolean> permissionBooleanMap = new HashMap<>();
        permissionBooleanMap.put(Manager.Permission.playerDeletion,true);
        permissionBooleanMap.put(Manager.Permission.coachDeletion, true);
        permissionBooleanMap.put(Manager.Permission.coachAddition, true);
        permissionBooleanMap.put(Manager.Permission.playerAddition, true);
        Manager m= null;

        try {
            os.insertNewManager(own,team.getName(),"Ruper",u2.getUserName(),u2.getEmail(),permissionBooleanMap);
            m = team.getManager(u2);
            ms.insertNewPlayer(m,team.getName(),"chen","harta",1,2,1990,u1.getUserName(),u1.getEmail());
            ms.insertNewCoach(m,team.getName(),"Harry","Sheker kollshehu","seeker",u3.getUserName(),u3.getEmail());
            assertEquals(team.getRoleHolders().size(),3);
            assertTrue(team.getPlayerList().contains(u1.getRoles().get(0)));
            assertTrue(team.getCoachList().contains(u3.getRoles().get(0)));
            RoleHolder toDelete1 = team.getRoleHolder(u1.getUserName(),u1.getEmail());
            ms.deleteRoleHolder(m,team.getName(),u1.getUserName(),u1.getEmail(),toDelete1);
            assertEquals(team.getRoleHolders().size(),2);
            assertEquals(team.getPlayerList().size(),0);
            assertEquals(u1.getRoles().size(),0);
            RoleHolder toDelete2 = team.getRoleHolder(u3.getUserName(),u3.getEmail());
            ms.deleteRoleHolder(m,team.getName(),u3.getUserName(),u3.getEmail(),toDelete2);

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(),"Cannot remove the last coach of the team");
        }
        permissionBooleanMap.replace(Manager.Permission.playerDeletion,false);
        try {
            os.insertNewPlayer(own,team.getName(),"hagrid","groundKeeper",1,2,1998,u4.getUserName(),u4.getEmail());
            m.deletePlayer(team.getName(),u4.getUserName(),u4.getEmail());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(),"This manager is not permitted to delete a player from the team");
        }

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
