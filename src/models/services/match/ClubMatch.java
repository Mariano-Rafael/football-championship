package models.services.match;

import models.Club;

public class ClubMatch {
    private Club homeClub;
    private Club awayClub;
    private int homeScore;
    private int awayScore;
    private boolean finished;

    public ClubMatch(Club homeClub, Club awayClub) {
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.homeScore = -1;
        this.awayScore = -1;
        this.finished = false;
    }

    public Club getHomeClub() {
        return homeClub;
    }

    public Club getAwayClub() {
        return awayClub;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.finished = true;
    }


    @Override
    public String toString() {
        return homeClub.name() + " " + (finished ? homeScore : "") + " x " + (finished ? awayScore : "") + " " + awayClub.name();
    }

}
