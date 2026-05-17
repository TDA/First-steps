package OpenAI_Interviews;

// Problem Statement: Contagion with Immunity
//Overview
//
//You are given a rectangular grid where each cell is one of:
//
//    0 for an empty space
//    1 for a healthy person
//    2 for an infected person
//    3 for an immune person
//
//You are also given:
//
//    rounds: how many rounds to simulate
//    recoveryTime: how many rounds a newly infected cell remains infected before becoming immune
//
//Task
//
//During each round:
//
//    Every infected cell spreads the infection to healthy neighbors in the four cardinal directions.
//    A cell that has been infected for recoveryTime rounds becomes immune after spreading in its final infectious round.
//    Immune cells cannot be infected again.
//
//All spreading in a round happens simultaneously. A cell that becomes infected during the current round does not spread until the next round.
//
//Return the grid state after exactly rounds rounds.
//Constraints
//
//    The grid may be large.
//    rounds may be zero. DONE
//    recoveryTime is positive. DONE
//    Only the values 0, 1, 2, and 3 appear in the grid.
//    Infection spreads only in the four cardinal directions.
//    Immune cells remain immune.
//    The result must represent the state after exactly the requested number of rounds.
//
//Example
//
//Input:
//
//grid = [[1,2,1,1]]
//rounds = 3
//recoveryTime = 2
//
//One valid output:
//
//[[3,3,3,2]]
//
//Explanation:
//
//    Round 1: the cells next to the original infected cell become infected.
//    Round 2: the original infected cell still gets its final infectious round, then becomes immune.
//    Round 3: the infection continues moving rightward while recovered cells stay immune.

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

record Cell(int row, int col, int recoveryAge) {}

public class Contagion {
    int[][] simulate(int[][] grid, int rounds, int recoveryTime) {
        int rows = grid.length;
        if (rows == 0) return grid;
        int cols = grid[rows - 1].length;
        if (cols == 0) return grid;
        if (rounds == 0 || recoveryTime == 0) return grid;

        // discuss if we want to shallow copy the input grid to avoid polluting the input params and keep this a clean function

        Deque<Cell> infectedQueue = new ArrayDeque<>();
        int infectionRound = 0;

        // get all infected cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 2) {
                    infectedQueue.add(new Cell(i, j, 0));
                }
            }
        }

        // might need a visited set to avoid extra compute

        // now start spreading
        while (!infectedQueue.isEmpty() && infectionRound < rounds) {
            // get previous round infected count
            var previousInfectedCount = infectedQueue.size();
            for (int i = 0; i < previousInfectedCount; i++) {
                var infected = infectedQueue.poll();
                if (infected == null) continue;
                // get grid co-ordinates to infect - 4 cardinals
                var row = infected.row();
                var col = infected.col();

                // same row - left
                checkInfection(grid, row, col - 1, infectedQueue, rows, cols);
                // same row - right
                checkInfection(grid, row, col + 1, infectedQueue, rows, cols);
                // same col - bottom
                checkInfection(grid, row - 1, col, infectedQueue, rows, cols);
                // same col - top
                checkInfection(grid, row + 1, col, infectedQueue, rows, cols);


                // last step - check if recoveryTime has been hit for this guy
                // recoveryTime starts at 0 for first round of infection, after each round +1
                if (infected.recoveryAge() + 1 >= recoveryTime) {
                    // this guy has recovered, update grid to immune
                    grid[row][col] = 3;
                } else {
                    // need to re-insert since this guy still spreads :(
                    infectedQueue.add(new Cell(row, col, infected.recoveryAge() + 1));
                }
            }
            infectionRound++;
        }

        // we made in-place changes to grid
        return grid;
    }

    private static void checkInfection(int[][] grid, int row, int col, Deque<Cell> infectedQueue, int maxRows, int maxCols) {
        // check boundary values first to make sure we don't go outside the grid
        if (row < 0 || row >= maxRows) return;
        if (col < 0 || col >= maxCols) return;
        // check if it's a healthy person, if so, we infect, else we cannot (0 - empty, 2 - already infected, 3 - immune)
        if (grid[row][col] == 1) {
            // can infect, add to queue and update grid
            infectedQueue.add(new Cell(row, col, 0));
            grid[row][col] = 2;
        }
    }

    public static void main(String[] args) {
        runTest("zero rounds returns original state", () -> {
            Contagion contagion = new Contagion();
            int[][] grid = {
                    {1, 2, 1},
                    {0, 1, 3}
            };

            assertGridEquals(new int[][]{
                    {1, 2, 1},
                    {0, 1, 3}
            }, contagion.simulate(grid, 0, 2));
        });

        runTest("spreads only to four cardinal neighbors after one round", () -> {
            Contagion contagion = new Contagion();
            int[][] grid = {
                    {1, 1, 1},
                    {1, 2, 1},
                    {1, 1, 1}
            };

            assertGridEquals(new int[][]{
                    {1, 2, 1},
                    {2, 2, 2},
                    {1, 2, 1}
            }, contagion.simulate(grid, 1, 3));
        });

        runTest("newly infected cells do not spread until next round", () -> {
            Contagion contagion = new Contagion();
            int[][] grid = {
                    {1, 2, 1, 1}
            };

            assertGridEquals(new int[][]{
                    {2, 2, 2, 1}
            }, contagion.simulate(grid, 1, 5));
        });

        runTest("sample recovers after final infectious round", () -> {
            Contagion contagion = new Contagion();
            int[][] grid = {
                    {1, 2, 1, 1}
            };

            assertGridEquals(new int[][]{
                    {3, 3, 3, 2}
            }, contagion.simulate(grid, 3, 2));
        });

        runTest("immune and empty cells are never infected", () -> {
            Contagion contagion = new Contagion();
            int[][] grid = {
                    {1, 3, 1},
                    {0, 2, 0},
                    {1, 3, 1}
            };

            assertGridEquals(new int[][]{
                    {1, 3, 1},
                    {0, 2, 0},
                    {1, 3, 1}
            }, contagion.simulate(grid, 1, 3));
        });

        runTest("recovery time one spreads once then immediately becomes immune", () -> {
            Contagion contagion = new Contagion();
            int[][] grid = {
                    {1, 2, 1}
            };

            assertGridEquals(new int[][]{
                    {3, 3, 3}
            }, contagion.simulate(grid, 2, 1));
        });

        runTest("infection reaches row and column zero boundaries", () -> {
            Contagion contagion = new Contagion();
            int[][] grid = {
                    {1, 1},
                    {1, 2}
            };

            assertGridEquals(new int[][]{
                    {1, 2},
                    {2, 2}
            }, contagion.simulate(grid, 1, 5));
        });
    }

    private static void assertGridEquals(int[][] expected, int[][] actual) {
        assertEquals(formatGrid(expected), formatGrid(actual));
    }

    private static String formatGrid(int[][] grid) {
        return Arrays.deepToString(grid);
    }
}
