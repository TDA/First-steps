package dp_recursion;

import java.util.Arrays;

public class ProbabilityOfWinning {
    double winCalc(double playerBProb, int turns, int target) {
        double[][] dp = new double[turns + 1][target + 1];
        for (double[] row : dp) {
            Arrays.fill(row, -1);
        }
        dp[1][1] = playerBProb;

        for (int i = 1; i < turns; i++) {
            for (int j = 1; j < target; j++) {
                dp[i][j] = dp[i-1][j] * (1-playerBProb) + dp[i-1][j-1] * playerBProb;
            }
        }

        return dp[turns][target];
    }

    double winCalc2(double playerProbability, int turns, int target) {
        double[][] dp = new double[turns+1][target+1];
        dp[0][0] = 1;

        for (int i = 1; i <= turns; i++) {
            for (int j = 1; j <= Math.min(target, turns); j++) {
                dp[i][j] += (dp[i-1][j] * (1-playerProbability)) + (dp[i-1][j-1] * playerProbability);
                // P10, 10 = P9, 10 * LOSE + P 9, 9 * WIN
            }
        }

        double result = 0;
        for (int totalRound = target; totalRound <= turns; totalRound++) {
            result += dp[totalRound][target];
        }
        return result;
    }

    public static void main(String[] args){
        ProbabilityOfWinning probabilityOfWinning = new ProbabilityOfWinning();
        System.out.println(probabilityOfWinning.winCalc(0.4, 1, 1));
        System.out.println(probabilityOfWinning.winCalc(0.4, 1, 11));
        System.out.println(probabilityOfWinning.winCalc(0.4, 1, 11));
        System.out.println(probabilityOfWinning.winCalc(0.4, 1, 11));
        System.out.println(probabilityOfWinning.winCalc(0.4, 1, 11));
        System.out.println(probabilityOfWinning.winCalc(0.4, 11, 1));
        System.out.println("===============");
        System.out.println(probabilityOfWinning.winCalc2(0.4, 1, 1));
        System.out.println(probabilityOfWinning.winCalc2(0.4, 2, 1));
        System.out.println(probabilityOfWinning.winCalc2(0.4, 4, 3));
        System.out.println(probabilityOfWinning.winCalc2(0.58, 40, 21));
        //3 round 0.4*0.4*0.4 = 0.064
        // 4 round 0.4*3*0.4*0.4*0.6
    }
}
