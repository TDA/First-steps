package fb_recent;

import java.util.Arrays;

class TicTacToe {
    int[][] gameBoard;
    /** Initialize your data structure here. */
    public TicTacToe(int n) {
        gameBoard = new int[n][n];
    }

    /** Player {player} makes a move at ({row}, {col}).
     @param row The row of the board.
     @param col The column of the board.
     @param player The player, can be either 1 or 2.
     @return The current winning condition, can be either:
     0: No one wins.
     1: Player 1 wins.
     2: Player 2 wins. */
    public int move(int row, int col, int player) {
        boolean playerWins;

        gameBoard[row][col] = player;
        playerWins = checkIfPlayerWon(row, col, player);

        // only current player can win
        if (playerWins) {
            return player;
        } else {
            return 0;
        }
    }

    private boolean checkIfPlayerWon(int row, int col, int player) {
        // if player needs to win on this turn, he needs the placement on row, col to have given a victory condition
        // check diagonals
        boolean diagonalMatch = true;
        for (int i = 0; i < gameBoard.length; i++) {
            diagonalMatch = diagonalMatch && gameBoard[i][i] == player;
        }
        if (diagonalMatch) {
            return true;
        } else {
            // reset and check the other diagonal
            diagonalMatch = true;
        }
        for (int i = 0; i < gameBoard.length; i++) {
            diagonalMatch = diagonalMatch && gameBoard[gameBoard.length - i - 1][i] == player;
        }
        if (diagonalMatch) {
            return true;
        }

        // check the straights on each row and col
        boolean straightMatch = true;
        for (int i = 0; i < gameBoard.length; i++) {
            straightMatch = straightMatch && gameBoard[row][i] == player;
        }
        if (straightMatch) {
            return true;
        } else {
            // reset and check columns
            straightMatch = true;
        }
        for (int i = 0; i < gameBoard.length; i++) {
            straightMatch = straightMatch && gameBoard[i][col] == player;
        }

        return straightMatch;
    }

    @Override
    public String toString() {
        return "TicTacToe{" +
                "gameBoard=" + Arrays.deepToString(gameBoard) +
                '}';
    }

    public static void main(String[] args){
        TicTacToe ticTacToe = new TicTacToe(3);
        System.out.println(ticTacToe.move(0, 0, 1)); // return 0 (no one wins)
        System.out.println(ticTacToe);

        System.out.println(ticTacToe.move(0, 2, 2)); // return 0 (no one wins)
        System.out.println(ticTacToe);

        System.out.println(ticTacToe.move(2, 2, 1)); // return 0 (no one wins)
        System.out.println(ticTacToe);
        System.out.println(ticTacToe.move(1, 1, 2)); // return 0 (no one wins)
        System.out.println(ticTacToe);
        System.out.println(ticTacToe.move(2, 0, 1)); // return 0 (no one wins)
        System.out.println(ticTacToe);
        System.out.println(ticTacToe.move(1, 0, 2)); // return 0 (no one wins)
        System.out.println(ticTacToe);
        System.out.println(ticTacToe.move(2, 1, 1)); // return 1 (player 1 wins)
        System.out.println(ticTacToe);

        System.out.println("-------------------------------------------");

        TicTacToe ticTacToe2 = new TicTacToe(2);
        System.out.println(ticTacToe2.move(0, 0, 2));
        System.out.println(ticTacToe2);

        System.out.println(ticTacToe2.move(1, 1, 1));
        System.out.println(ticTacToe2);

        System.out.println(ticTacToe2.move(0, 1, 2));
        System.out.println(ticTacToe2);
    }
}

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */