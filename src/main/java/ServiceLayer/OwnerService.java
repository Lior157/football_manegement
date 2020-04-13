package ServiceLayer;

import LogicLayer.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OwnerService extends AUserService{

    public OwnerService(Controller control) {
        super(control);
    }

    /**
     * id: OwnerService@1
     * validates that the team is exist and connected to the owner
     * validates that the email and userName are attached to an existing account
     * @return User if exists
     * @param owner
     * @param teamName
     * @param email
     * @param userName
     * @throws IOException
     */
    public void validateExistingAssetType(Owner owner, String teamName,
                                          String email, String userName) throws IOException {
        Team team = owner.getTeam(teamName);
        if (team == null)
            throw new IOException("The chosen team does not exist, please choose a valid team");

        if (!(email.equals("X") && userName.equals("X"))) { // not a stadium
            boolean exists = owner.findUser(userName, email);
            if (!exists)
                throw new IOException("The chosen user does not exist, please insert valid inputs");
        }
    }

    /**
     * id: OwnerService@2
     * owner adds a new manager to a requested team
     * @param owner
     * @param teamName
     * @param managerName
     * @param userName
     * @param email
     */
    public void insertNewManager(Owner owner, String teamName, String managerName, String userName, String email) throws IOException {
        owner.insertNewManager(owner.getTeam(teamName),managerName,userName,email);
    }

    /**
     * id: OwnerService@3
     * owner adds a new coach to the requested team with the following parameters
     * @param owner
     * @param teamName
     * @param name
     * @param qualification
     * @param job
     * @param userName
     * @param email
     */
    public void insertNewCoach(Owner owner, String teamName, String name, String qualification, String job,String userName,
                               String email) {
        owner.insertNewCoach(owner.getTeam(teamName),name,qualification,job,userName,email);
    }

    /**
     * id: OwnerService@4
     * owner adds a new player to the selected team with the following parameters
     * @param owner
     * @param teamName
     * @param name
     * @param position
     * @param day
     * @param month
     * @param year
     * @param userName
     * @param email
     * @throws IOException
     */
    public void insertNewPlayer(Owner owner, String teamName, String name, String position, int day ,
                                int month, int year , String userName, String email) throws IOException {
        validateBirthDate(day,month,year);
        owner.insertNewPlayer(owner.getTeam(teamName),name,position,day,month,year,userName,email);

    }

    /**
     * id: OwnerService@5
     * owner adds a new stadium to a requested team
     * @param owner
     * @param teamName
     * @param stadiumName
     */
    public void insertNewStadium(Owner owner, String teamName, String stadiumName) {
        owner.insertNewStadium(owner.getTeam(teamName),stadiumName);
    }

    /**
     * id: OwnerService@6
     * validates the ranges of a player's birthday
     * @param day
     * @param month
     * @param year
     * @throws IOException
     */
    private void validateBirthDate(int day, int month, int year) throws IOException {

        if (!(year > 1900 && year < 2020 )) {
            throw new IOException("Year of birth is illegal, please insert a year between " +
                    "1900 and 2020");
        }
        if (!(month > 0 && month < 13 )) {
            throw new IOException("Month is illegal, please insert valid month between 1 and 12");
        }
        if (!(day > 0 && day < 32 )) {
            throw new IOException("Day is illegal, please insert valid day between 1 and 31");
        }

    }


    /**
     * id: OwnerService@7
     * deletes the requested asset (Player/Coach/Manager) from the owne'r team
     * @param own
     * @param teamName
     * @param userName
     * @param email
     * @param toDelete
     */
    public void deleteRoleHolder(Owner own, String teamName, String userName, String email, RoleHolder toDelete)
            throws IOException {
        switch (toDelete.getClass().getSimpleName().toLowerCase()) {
            case "player":
                own.deletePlayer(teamName,userName,email);
            case "manager":
                own.deleteManager(teamName,userName,email);
            case "coach":
                own.deleteCoach(teamName,userName,email);
        }
    }

    /**
     * id: OwnerService@8
     * deletes the requested stadium from the owner's team
     * @param owner
     * @param teamName
     * @param stadium
     */
    public void deleteStadium(Owner owner, String teamName, String stadium) {
        owner.deleteStadium(teamName,stadium);
    }

    /**
     * id: OwnerService@9
     * nominates an existing user to an additional owner of the provided team of the provided owner, iff he does not
     * owes this team allready
     * @param owner
     * @param teamName
     * @param user
     * @param name
     * @throws IOException
     */
    public void nominateNewOwner(Owner owner,String teamName, User user, String name) throws IOException {
        owner.nominateNewOwner(user,teamName,name);
    }

    ////////////////////////////////////////////////////// 6.1.3

    /**
     * id: OwnerService@10
     * updates a set of attributes that the owner chose for a team member of his
     * @param owner
     * @param teamName
     * @param roleHolder
     * @param attributes
     * @throws IOException
     */
    public void updateRoleHolder(Owner owner,String teamName, RoleHolder roleHolder,
                                 Map<String,String> attributes) throws IOException {
        owner.updateAssetAttributes(teamName,roleHolder,attributes);
    }

    /**
     * id: OwnerService@11
     * activates process of removing owner from all of his nominations in the selected grop
     * @param own
     * @param nominatedOwner
     * @param teamName
     * @throws IOException
     */
    public void removeOwnership(Owner own, Owner nominatedOwner, String teamName) throws IOException {
        own.removeOwnership(nominatedOwner,teamName);
    }
}

