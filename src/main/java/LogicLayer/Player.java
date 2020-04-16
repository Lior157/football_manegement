package LogicLayer;

import java.util.Objects;

public class Player extends RoleHolder{

    private String position;
    private Team team;
    private String name;
    String birthDate;
    private Page page;

    public Player(User user, String position, Team team, String name, String birthDate, Page page) {
        super(user);
        this.position = position;
        this.team = team;
        this.name = name;
        this.birthDate = birthDate;
        this.page = page;
    }

    public Player() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Player player = (Player) o;
        return Objects.equals(position, player.position) &&
                Objects.equals(team, player.team) &&
                Objects.equals(name, player.name) &&
                Objects.equals(birthDate, player.birthDate) &&
                Objects.equals(page, player.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), position, team, name, birthDate, page);
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
