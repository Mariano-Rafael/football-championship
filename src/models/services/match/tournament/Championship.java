package models.services.match.tournament;

import models.Club;
import models.ClubStatistics;
import models.services.match.ClubMatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Championship {

    private String name;
    private final List<Club> clubs;
    private String format;
    private int clubsPerGroup;
    private int qualifiersPerGroup;
    private final List<Group> groups;
    private final List<List<ClubMatch>> knockoutMatches;

    public Championship(String name) {
        this.name = name;
        this.clubs = new ArrayList<>();
        this.format = "";
        this.clubsPerGroup = 0;
        this.qualifiersPerGroup = 0;
        this.groups = new ArrayList<>();
        this.knockoutMatches = new ArrayList<>();
    }

    public List<Group> getGroups() {
        return groups;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void addClub(Club club) {
        this.clubs.add(club);
    }

    public void removeClub(Club club) {
        this.clubs.remove(club);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        if (format.equals("Knockout") || format.equals("GroupsAndKnockout")) {
            this.format = format;
            if (!format.equals("GroupsAndKnockout")) {
                this.clubsPerGroup = 0;
                this.qualifiersPerGroup = 0;
                this.groups.clear();
            }
        } else {
            throw new IllegalArgumentException("Format must be Knockout or GroupsAndKnockout");
        }
    }

    public int getClubsPerGroup() {
        return clubsPerGroup;
    }

    public void setTeamsPerGroup(int teamsPerGroup) {
        if (format.equals("GroupsAndKnockout") && teamsPerGroup > 0) {
            this.clubsPerGroup = teamsPerGroup;
        } else if (!format.equals("GroupsAndKnockout")) {
            this.clubsPerGroup = 0;
        } else {
            throw new IllegalArgumentException("Number of clubs must be greater than zero!");
        }
    }

    public int getQualifiersPerGroup() {
        return qualifiersPerGroup;
    }

    public void setQualifiersPerGroup(int qualifiersPerGroup) {
        if (format.equals("GroupsAndKnockout") && qualifiersPerGroup > 0) {
            this.qualifiersPerGroup = qualifiersPerGroup;
        } else if (!format.equals("GroupsAndKnockout")) {
            this.qualifiersPerGroup = 0;
        } else {
            throw new IllegalArgumentException("Number of qualifiers must be greater than zero for 'GroupsAndKnockout' format!");
        }
    }

    public void createGroups() {
        if (!format.equals("GroupsAndKnockout")) {
            throw new IllegalArgumentException("Groups can only be created for 'GroupsAndKnockout' format!");
        }
        if (clubs.size() % clubsPerGroup != 0) {
            throw new IllegalArgumentException("Number of clubs must be a multiple of clubs per group!");
        }
        if (clubs.isEmpty() || clubsPerGroup < 1) {
            throw new IllegalArgumentException("Cannot create groups with no clubs or zero clubs per group!");
        }

        groups.clear();
        List<Club> shuffledClubs = new ArrayList<>(clubs);
        Collections.shuffle(shuffledClubs);

        int numberOfGroups = clubs.size() / clubsPerGroup;

        for (int i = 0; i < numberOfGroups; i++) {
            String groupId = String.valueOf((char) ('A' + i));
            Group group = new Group(groupId);

            for (int x = 0; x < clubsPerGroup; x++) {
                group.addClub(shuffledClubs.get(i * clubsPerGroup + x));
            }
            this.groups.add(group);
        }
    }

    public List<Club> getQualifiers() {
        if (!format.equals("GroupsAndKnockout")) {
            throw new IllegalStateException("Cannot get group winners for '" + format + "' format.");
        }
        if (groups.isEmpty()) {
            System.out.println("No groups created!");
            return new ArrayList<>();
        }
        if (qualifiersPerGroup < 1) {
            System.out.println("Number of qualifying clubs per group must be greater than zero!");
            return new ArrayList<>();
        }

        List<Club> qualifiers = new ArrayList<>();
        for (Group group : groups) {
            List<ClubStatistics> classification = group.getClassification();
            for (int i = 0; i < Math.min(qualifiersPerGroup, classification.size()); i++) {
                qualifiers.add(classification.get(i).getClub());
            }
        }
        return qualifiers;
    }

    public List<List<ClubMatch>> getKnockoutMatches() {
        return knockoutMatches;
    }

    public void startKnockoutMatches(List<Club> qualifiers) {
        if (format.equals("Knockout") || (format.equals("GroupsAndKnockout") && !qualifiers.isEmpty())) {
            if (knockoutMatches.isEmpty()) {
                List<Club> shuffledQualifiers = new ArrayList<>(qualifiers);
                Collections.shuffle(shuffledQualifiers, new Random());

                List<ClubMatch> firstRound = new ArrayList<>();
                for (int i = 0; i < shuffledQualifiers.size(); i += 2) {
                    if (i + 1 < shuffledQualifiers.size()) {
                        firstRound.add(new ClubMatch(shuffledQualifiers.get(i), shuffledQualifiers.get(i + 1)));
                    } else {
                        System.out.println("Team" + shuffledQualifiers.get(i).name() + " has a bye.");
                    }
                }
                if (!firstRound.isEmpty()) {
                    knockoutMatches.add(firstRound);
                }
            } else {
                System.out.println("Knockout matches have already been started!");
            }
        } else {
            throw new IllegalStateException("Cannot start knockout matches for '" + format + "' format.");
        }
    }

    public void startKnockoutMatches() {
        if (format.equals("Knockout")) {
            startKnockoutMatches(this.clubs);
        } else if (format.equals("GroupsAndKnockout")) {
            List<Club> qualifiers = getQualifiers();
            startKnockoutMatches(qualifiers);
        } else {
            throw new IllegalStateException("Cannot start knockout matches for '" + format + "' format.");
        }
    }

    public void advanceKnockoutMatches() {
        if (!knockoutMatches.isEmpty()) {
            List<ClubMatch> currentRound = knockoutMatches.getLast();

            List<Club> winners = new ArrayList<>();
            boolean allFinished = true;
            for (ClubMatch match : currentRound) {
                if (!match.isFinished()) {
                    allFinished = false;
                    break;
                }
                if (match.getHomeScore() > match.getAwayScore()) {
                    winners.add(match.getHomeClub());
                } else {
                    winners.add(match.getAwayClub());
                }
            }

            if (allFinished) {
                if (winners.size() > 1 && winners.size() % 2 == 0) {
                    List<ClubMatch> nextRound = new ArrayList<>();
                    for (int i = 0; i < winners.size(); i += 2) {
                        nextRound.add(new ClubMatch(winners.get(i), winners.get(i + 1)));
                    }
                    knockoutMatches.add(nextRound);
                }
                else if (winners.size() == 1) {
                    System.out.println("Winner: " + winners.getFirst().name());
                }
                else {
                    System.out.println("Knockout matches are over!");
                }
            }
        }

    }
}
