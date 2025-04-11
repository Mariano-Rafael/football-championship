package models;

public class ClubStatistics {
    private final Club club;
    private int points;
    private int goalsFor;
    private int goalsAgainst;

    public ClubStatistics(Club club) {
        this.club = club;
        this.points = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
    }

    public Club getClub() {
        return club;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getGoalsDifference() {
        return goalsFor - goalsAgainst;
    }

    @Override
    public String toString() {
        return club.name() + " Pontos: " + points + ", GM: " + goalsFor + ", GS: " + goalsAgainst;
    }
}
