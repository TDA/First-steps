const neighborLetter = (board: string[][], i: number, j: number, nextLetter: number, words: string, visitedBoard: boolean[][]): boolean => {
    console.log("At ", i, j, words[nextLetter])
    if (nextLetter == words.length) return true;
    if (i < 0 || j < 0 || i >= board.length || j >= board[i].length || visitedBoard[i][j] === true) return false;
    if (words[nextLetter] !== board[i][j]) return false;

    // Mark visited
    visitedBoard[i][j] = true;
    // check if any DFS works
    if (neighborLetter(board, i, j-1, nextLetter + 1, words, visitedBoard) || 
            neighborLetter(board, i-1, j, nextLetter + 1, words, visitedBoard) ||
            neighborLetter(board, i, j+1, nextLetter + 1, words, visitedBoard) ||
            neighborLetter(board, i+1, j, nextLetter + 1, words, visitedBoard)) return true;
    // backtrack so set not visited
    visitedBoard[i][j] = false;
    return false;
}
var exist = function(board, word) {
    let nextLetter = word[0];

    const startPoints = findPotentialStartPoints(board, nextLetter);
    console.log(startPoints)

    for(let [i, j] of startPoints) {
        let visitedBoard = JSON.parse(JSON.stringify(board))
        if (neighborLetter(board, i, j, 0, word, visitedBoard)) {
            return true;
        }
    }
    return false;
};

function findPotentialStartPoints(board: any, nextLetter: any): Array<Array<number>> {
    // returns list of i,j's
    const startPoints = [];
    for (let i = 0; i < board.length; i++) {
        const row = board[i];
        let jPoints = row.map((e, i) => e == nextLetter ? i : -1).filter(e => e !== -1);
        jPoints.forEach(j => {
            startPoints.push([i, j])
        });
    }
    return startPoints;
}

let board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
console.log(exist(board, word))

let word2 = "SEE"
console.log(exist(board, word2))

let board2 = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word3 = "ABCB"
console.log(exist(board2, word3))