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
//    rounds may be zero.
//    recoveryTime is positive.
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

public class Contagion {
}
