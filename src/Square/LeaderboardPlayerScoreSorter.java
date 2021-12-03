package Square;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Data;

// 1. coding: At the end of the game, we get an array of scoring events This is a time series of scores that players have earned at various points in the game
// Given an array with tuples of (player and score), calculate.. ............... ..
//
//[(1, 5), (2, 4), (3, 6), (1, -2), (2, 2), (4, 1), (5, 5), (5, -1) ]
//
//First Q: Give a Playerid, print the score
//The second question: Give a Playerid, print the score and sort, left and right neighboring K SCORE
//EXAMPLE:
//-> Player A: 36
//-> Player B: 33
//-> Player C: 31
//-> Player P: 30
//-> Player x: 29
//-> Player Y: 20
//-> Player Z: 11
//
//Output for Showleaderboard (p, 2) -> [(B, 33), (C, 31), (P, 30), (x, 29), (Y, 20)]
//
//FOLLOW: What if the data is big?
public class LeaderboardPlayerScoreSorter {
    TreeMap<PlayerScore, Integer> leaderboard = new TreeMap<>();
    Map<String, Integer> playerScoreLookup = new HashMap<>();

    void addScores(List<PlayerScore> scores) {
        for (PlayerScore score: scores) {
            Integer currentScore = playerScoreLookup.getOrDefault(score.player, 0);
            playerScoreLookup.put(score.player, score.score + currentScore);
        }

        for (Map.Entry<String, Integer> entry: playerScoreLookup.entrySet()) {
            leaderboard.put(new PlayerScore(entry.getKey(), entry.getValue()), entry.getValue());
        }
        System.out.println(playerScoreLookup);
        System.out.println(leaderboard);
    }

    List<PlayerScore> Showleaderboard(String playerId, int k) {
        List<PlayerScore> modifiedLeaderboard = new ArrayList<>();
        int i = 0;
        PlayerScore score = new PlayerScore(playerId, playerScoreLookup.get(playerId));
        modifiedLeaderboard.add(score);
        while (i < k && leaderboard.lowerEntry(score) != null) {
            Map.Entry<PlayerScore, Integer> entry = leaderboard.lowerEntry(score);
            score = entry.getKey();
            modifiedLeaderboard.add(score);
            i++;
        }
        i = 0;
        score = new PlayerScore(playerId, playerScoreLookup.get(playerId));
        while (i < k && leaderboard.higherEntry(score) != null) {
            Map.Entry<PlayerScore, Integer> entry = leaderboard.higherEntry(score);
            score = entry.getKey();
            modifiedLeaderboard.add(score);
            i++;
        }
        Collections.sort(modifiedLeaderboard);
        return modifiedLeaderboard;
    }

    @Data
    private static class PlayerScore implements Comparable<PlayerScore> {
        final String player;
        final int score;

        public PlayerScore(String player, int score) {
            this.player = player;
            this.score = score;
        }

        @Override
        public int compareTo(PlayerScore playerScore) {
            return this.getScore() - playerScore.getScore();
        }
    }

    public static void main(String[] args){
        LeaderboardPlayerScoreSorter leaderboardPlayerScoreSorter = new LeaderboardPlayerScoreSorter();
        List<PlayerScore> scores = new ArrayList<>();
        scores.add(new PlayerScore("Sai", 5));
        scores.add(new PlayerScore("Sai", -3));
        scores.add(new PlayerScore("Li", 20));
        scores.add(new PlayerScore("Li", -9));
        scores.add(new PlayerScore("Sai", 10));
        scores.add(new PlayerScore("Li", 13));
        scores.add(new PlayerScore("Adam", 19));
        scores.add(new PlayerScore("Lucas", 22));
        scores.add(new PlayerScore("Wen", 28));
        scores.add(new PlayerScore("NewUser", 26));
        scores.add(new PlayerScore("NewUser1", 9));
        scores.add(new PlayerScore("NewUser2", 7));
        scores.add(new PlayerScore("NewUser3", 3));
        leaderboardPlayerScoreSorter.addScores(scores);
        System.out.println(leaderboardPlayerScoreSorter.getPlayerScore("Sai"));
        System.out.println(leaderboardPlayerScoreSorter.getPlayerScore("Li"));

        System.out.println(leaderboardPlayerScoreSorter.Showleaderboard("Adam", 3));
        System.out.println(leaderboardPlayerScoreSorter.Showleaderboard("Adam", 1));
        System.out.println(leaderboardPlayerScoreSorter.Showleaderboard("Sai", 1));
    }

    private int getPlayerScore(String playerId) {
        return playerScoreLookup.getOrDefault(playerId, 0);
    }
}
