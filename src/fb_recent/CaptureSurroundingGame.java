package fb_recent;

import java.util.Arrays;

public class CaptureSurroundingGame {
    public void solve(char[][] board) {
        if (board.length == 0 || board[0].length == 0) return;
        int first = 0;
        int last = board.length - 1;
        for (int i = 0; i < board[0].length; i++) {
            if (board[first][i] == 'O') {
                helper(board, first, i);
            }
            if (board[last][i] == 'O') {
                helper(board, last, i);
            }
        }
        System.out.println(Arrays.deepToString(board));
        for (int i = 1; i < board.length - 1; i++) {
            if (board[i][first] == 'O') {
                helper(board, i, first);
            }
            if (board[i][board[i].length - 1] == 'O') {
                helper(board, i, board[i].length - 1);
            }
        }
        System.out.println(Arrays.deepToString(board));

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'O')
                    board[i][j] = 'X';
            }
        }
        System.out.println(Arrays.deepToString(board));
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'V')
                    board[i][j] = 'O';
            }
        }

        System.out.println(Arrays.deepToString(board));
    }

    void helper(char[][] board, int row, int col) {
        if (row >= board.length || row < 0 || col < 0 || col >= board[0].length) return;
        if (board[row][col] == 'X' || board[row][col] == 'V') return;
        board[row][col] = 'V';
        helper(board, row + 1, col);
        helper(board, row - 1, col);
        helper(board, row, col + 1);
        helper(board, row, col - 1);
    }

    public static void main(String[] args){
        CaptureSurroundingGame captureSurroundingGame = new CaptureSurroundingGame();
        char[][] board = {
                {'X','X','X','X' },
        {'X','O','O','X' },
        {'X','X','O','X' },
        {'X','O','X','X'}
        };
//        captureSurroundingGame.solve(board);

        char[][] board2 = {{'X','O','X'},{'O','X','O'},{'X','O','X'}};
//        captureSurroundingGame.solve(board2);

        char[][] board3 = {{'X','O','X','O','X','O'},{'O','X','O','X','O','X'},{'X','O','X','O','X','O'},{'O','X','O','X','O','X'}};
        captureSurroundingGame.solve(board3);
    }
}
