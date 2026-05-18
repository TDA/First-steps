package OpenAI_Interviews;

// Problem Statement: Infection After K Rounds
//Overview
//
//You are given a rectangular grid where each cell is one of:
//
//    0 for an empty space
//    1 for a healthy person
//    2 for an infected person
//
//Task
//
//During each round, every infected cell spreads the infection to its healthy neighbors in the four cardinal directions: up, down, left, and right.
//
//All spreading in a round happens simultaneously. A cell that becomes infected during the current round does not spread until the next round.
//
//Return the grid state after exactly k rounds.
//Constraints
//
//    The grid may be large.
//    k may be zero.
//    Only the values 0, 1, and 2 appear in the input.
//    Empty cells never change state.
//    Infection spreads only in the four cardinal directions.
//    The result must represent the state after exactly k rounds.
//
//Example
//
//Input:
//
//grid = [[1,1,0,1],[1,2,1,0],[0,1,1,1]]
//k = 2
//
//One valid output:
//
//[[2,2,0,1],[2,2,2,0],[0,2,2,1]]
//
//Explanation:
//
//    After round 1, the healthy cells adjacent to the original infected cell become infected.
//    Those newly infected cells do not spread until round 2.
//    Empty cells remain unchanged.

public class Infection {

    // NOTE: this has a tighter / tougher version @src/OpenAI_Interviews/Contagion.java
    // Contagion covers Infection plus extra timing complexity
    // Refer to that and remove the #recoverytime logic to get the solution for this
}
