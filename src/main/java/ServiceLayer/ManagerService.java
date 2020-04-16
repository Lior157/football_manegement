package ServiceLayer;

import LogicLayer.Manager;
import LogicLayer.RoleHolder;

import java.io.IOException;

public class ManagerService extends AUserService{

    public ManagerService(Controller control) {
        super(control);
    }


    /**
     * id: ManagerService@1
     * inserts a new player to the selected team
     * @param manager
     * @param teamName
     * @param name
     * @param position
     * @param day
     * @param month
     * @param year
     * @param userName
     * @param email
     * @throws IOException in the following cases:
     * user does not exists
     * team does not exist
     * manager has no permission to insert a new player
     */
    public void insertNewPlayer(Manager manager, String teamName, String name, String position, int day, int month,
                                int year, String userName, String email) throws IOException {

        manager.insertNewPlayer(teamName,name,position,day,month,year,userName,email);
    }

    /**
     * id: ManagerService@2
     * insert a new coach to the selected team
     * @param manager
     * @param teamName
     * @param name
     * @param qualification
     * @param job
     * @param userName
     * @param email
     * @throws IOException in the following cases:
     * user does not exists
     * team does not exist
     * manager has no permission to insert a new player
     */
    public void insertNewCoach(Manager manager, String teamName, String name, String qualification,
                               String job, String userName, String email) throws IOException {
        manager.insertNewCoach(teamName,name,qualification,job,userName,email);
    }

    /**
     * id: ManagerService@3
     * deletes the requested asset (Player/Coach) from the manager's team
     * @param manager
     * @param teamName
     * @param userName
     * @param email
     * @param toDelete
     * @throws IOException in the following cases:
     * the manager has no permission to delete a player or coach.
     * the user does not exist
     * the team does not exist
     */
    public void deleteRoleHolder(Manager manager, String teamName, String userName, String email,
                                 RoleHolder toDelete) throws IOException {
        switch (toDelete.getClass().getSimpleName().toLowerCase()) {
            case "player":
                manager.deletePlayer(teamName,userName,email);
                break;
            case "coach":
                manager.deleteCoach(teamName,userName,email);
                break;
        }
    }
}
