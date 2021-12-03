package Square;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class RankAlgorithm {

    class Team implements Comparable<Team> {
        Map<Integer, Integer> voteBank = new HashMap<>();
        String name;

        public String getName() {
            return name;
        }

        public Team(String teamName) {
            name = teamName;
        }

        public void addVote(int rank) {
            voteBank.put(rank, voteBank.getOrDefault(rank, 0) + 1);
        }

        @Override
        public int compareTo(Team bTeam) {
            int minRank = Integer.MAX_VALUE;
            int maxRank = Integer.MIN_VALUE;

            for (Integer integer : this.voteBank.keySet()) {
                minRank = Math.min(minRank, integer);
                maxRank = Math.max(maxRank, integer);
            }
            int i = minRank;
            while (i <= maxRank) {
                if (this.voteBank.getOrDefault(i, 0) == bTeam.voteBank.getOrDefault(i, 0)) {
                    // equal, move to next rank check
                    i++;
                } else {
                    if (this.voteBank.getOrDefault(i, 0) > bTeam.voteBank.getOrDefault(i, 0)) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
            return this.name.compareTo(bTeam.name);
        }

        @Override
        public String toString() {
            return "Team{" +
                    "name='" + name + '\'' +
                    "votes='" + voteBank + '\'' +
                    '}';
        }
    }

    public List<String> rankTeams(String[] votes) {
        Map<String, Team> teamMap = new HashMap<>();
        for (String vote : votes) {
            String[] split = vote.split(" - ");
            String teamName = split[0];
            String voteRank = split[1];
            Team team = teamMap.getOrDefault(teamName, new Team(teamName));
            int rank = Integer.parseInt(voteRank);
            team.addVote(rank);
            teamMap.put(teamName, team);
        }
        List<Team> results = new ArrayList<>(teamMap.values());

        Collections.sort(results);

        return results.stream().map(Team::getName).collect(Collectors.toList());
    }

    boolean checkPalindrome(String inputString) {
        TreeMap<Integer, Integer> saisaMap = new TreeMap<>();
        for (char c : inputString.toCharArray()) {
            saisaMap.put(1, 2);

        }

        return saisaMap.keySet()
                .stream()
                .filter(a -> a == 1)
                .collect(Collectors.toList()).size() == 1;

    }


    public static void main(String[] args){
        RankAlgorithm rankAlgorithm = new RankAlgorithm();
        String[] votes = {
                "Team A - 1",
                "Team A - 1",
                "Team M - 1",
                "Team M - 1",
                "Team M - 3",
                "Team C - 1",
                "Team D - 1",
                "Team D - 1",
                "Team M - 2",
                "Team M - 2",
                "Team C - 2",
                "Team A - 1",
                "Team C - 2",
                "Team C - 1",
                "Team E - 2",
                "Team M - 3",
                "Team D - 2",
                "Team C - 3",
                "Team C - 3",
        }; // expected A -> B -> C -> D -> E
        System.out.println(rankAlgorithm.rankTeams(votes));
    }
}
