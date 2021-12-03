const {prettyMatrix} = require("./prettyMatrix")
const setZeroes = function(matrix = [[]]) {
    for (let i = 0; i < matrix.length; i++) {
        for (let j = 0; j < matrix[i].length; j++) {
            if (matrix[i][j] === 0) {
                // set both the forward and reverse items to -99999
                for (let k = 0; k < matrix[i].length; k++) {
                    if (matrix[i][k] !== 0)
                        matrix[i][k] = -99999;
                    
                }
                for (let k = 0; k < matrix.length; k++) {
                    if (matrix[k][j] !== 0)
                        matrix[k][j] = -99999;
                    
                }
            }
        }
    }
    for (let i = 0; i < matrix.length; i++) {
        for (let j = 0; j < matrix[i].length; j++) {
            if (matrix[i][j] === -99999) {
                matrix[i][j] = 0;
            }
        }
    }
    return matrix;
};

let matrix = [[1,1,1],[1,0,1],[1,1,1]]
console.log(prettyMatrix(setZeroes(matrix)));
console.log(prettyMatrix([[1,0,1],[0,0,0],[1,0,1]]));
