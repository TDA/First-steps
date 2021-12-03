const { prettyMatrix } = require("./prettyMatrix");

const reverseMatrix = function(matrix) {
    for (let i = 0; i < matrix.length; i++) {
        for (let j = 0, k = matrix[i].length - 1; j <= k; j++, k--) {
            // swap i, j and i, k in-place with temp var
            let temp = matrix[i][j];
            matrix[i][j] = matrix[i][k]
            matrix[i][k] = temp;
        }
    }
    return matrix;
}

var rotate = function(matrix) {
    for (let i = 0; i < matrix.length; i++) {
        for (let j = i+1; j < matrix[i].length; j++) {
            // swap i, j in-place with temp var
            let temp = matrix[i][j];
            matrix[i][j] = matrix[j][i]
            matrix[j][i] = temp;
        }
    }
    return matrix;
};

const rotateByX = (matrix, degree) => {
    // These rotations need topdown flipping as well, but is relatively easy to do: https://github.com/TDA/First-steps/blob/master/src/TransposeMatrix.java
    switch (degree) {
        case 90: 
            return reverseMatrix(rotate(matrix));
        case 180: 
            return rotate(matrix);
        case 270: 
            return rotate(reverseMatrix(matrix));
        default: 
            return matrix;
    }
}

let matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
let Output = [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]

prettyMatrix(rotateByX(matrix, 0))
console.log("-------------------")
prettyMatrix(rotateByX(matrix, 90))
console.log("-------------------")
prettyMatrix(rotateByX(matrix, 180))
console.log("-------------------")
prettyMatrix(rotateByX(matrix, 270))
console.log("-------------------")
// prettyMatrix(Output)
