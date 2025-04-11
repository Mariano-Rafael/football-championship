package models.services.match.tournament;

import models.Club;
import models.ClubStatistics;
import models.services.match.ClubMatch;

import java.util.*;

public class Group {
    private String groupId;
    private List<Club> clubs;
    private Map<Club, ClubStatistics> clubStats;
    private List<ClubMatch> matches;

    public Group(String groupId) {
        this.groupId = groupId;
        this.clubs = new ArrayList<>();
        this.clubStats = new HashMap<>();
        this.matches = new ArrayList<>();
    }

    public String getGroupId() {
        return groupId;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void addClub(Club club) {
        this.clubs.add(club);
        this.clubStats.put(club, new ClubStatistics(club));
    }

    public void removeClub(Club club) {
        this.clubs.remove(club);
        this.clubStats.remove(club);
    }

    public Map<Club, ClubStatistics> getClubStats() {
        return clubStats;
    }

    public List<ClubMatch> getMatches() {
        return matches;
    }

    public void addMatch(ClubMatch match) {
        this.matches.add(match);
    }

    public void generateMatches() {
        if (clubs.size() < 2) {
            System.out.println("Not enough teams in group " + groupId + " to generate matches.");
            return;
        }
        this.matches.clear();
        for (int i = 0; i < clubs.size(); i++) {
            for (int x = i + 1; x < clubs.size(); x++) {
                Club homeClub = clubs.get(i);
                Club awayClub = clubs.get(x);
                this.matches.add(new ClubMatch(homeClub, awayClub));
            }
        }
    }

    public void registerResults(ClubMatch match, int homeScore, int awayScore) {
        if (!matches.contains(match)) {
            System.out.println("Match not found in this group!");
            return;
        }
        if (match.isFinished()) {
            System.out.println("Match already finished!");
            return;
        }

        match.setScore(homeScore, awayScore);
        Club homeClub = match.getHomeClub();
        Club awayClub = match.getAwayClub();

        ClubStatistics statsHome = clubStats.get(homeClub);
        ClubStatistics statsAway = clubStats.get(awayClub);

        if (statsHome != null && statsAway != null) {
            statsHome.setGoalsFor(statsHome.getGoalsFor() + homeScore);
            statsHome.setGoalsAgainst(statsHome.getGoalsAgainst() + awayScore);
            statsAway.setGoalsFor(statsAway.getGoalsFor() + awayScore);
            statsAway.setGoalsAgainst(statsAway.getGoalsAgainst() + homeScore);

            if (homeScore > awayScore) {
                statsHome.setPoints(statsHome.getPoints() + 3);
            } else if (awayScore > homeScore) {
                statsAway.setPoints(statsHome.getPoints() + 3);
            } else {
                statsHome.setPoints(statsHome.getPoints() + 1);
                statsAway.setPoints(statsAway.getPoints() + 1);
            }
        }
    }

    public List<ClubStatistics> getClassification() {
        List<ClubStatistics> statistics = new ArrayList<>(clubStats.values());

        statistics.sort(Comparator.comparing(ClubStatistics::getPoints).reversed()
                .thenComparing(ClubStatistics::getGoalsDifference).reversed()
                .thenComparing(ClubStatistics::getGoalsFor).reversed()
                .thenComparing(stats -> stats.getClub().name()));

        return statistics;
    }
}
