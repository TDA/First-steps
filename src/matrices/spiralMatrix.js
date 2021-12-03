const {prettyMatrix} = require("./prettyMatrix")

const traverseRow = (matrix, direction) => {
    // direction = "ltr", "rtl" for a row
    // when you reach end of row, set direction for column traversal and then recurse mutually after cutting matrix
    if (matrix.length == 0) {
        return [];
    }
    if (matrix.length == 1 && matrix[0].length == 1) {
        // base case, return item
        return [matrix[0][0]];
    }
    let printOrder = []
    if (direction === "ltr") {
        printOrder.push(...matrix[0])
        matrix = cutMatrix(matrix, 0, undefined)
        printOrder.push(...traverseColumn(matrix, "topbottom"));
        return printOrder;
    } else {
        for (let i = matrix[0].length - 1; i >= 0; i--) {
            printOrder.push(matrix[matrix.length - 1][i])
        }
        matrix = cutMatrix(matrix, matrix.length - 1, undefined)
        printOrder.push(...traverseColumn(matrix, "bottomtop"));
        return printOrder;
    }
}

const traverseColumn = (matrix, direction) => {
    // direction = "topbottom", "bottomtop" for a column
    // when you reach end of column, set direction for row traversal and then recurse mutually after cutting matrix
    if (matrix.length == 0) {
        return [];
    }
    if (matrix.length == 1 && matrix[0].length == 1) {
        // base case, return item
        return [matrix[0][0]];
    }
    let printOrder = []
    if (direction === "topbottom") {
        for (let i = 0; i < matrix.length; i++) {
            printOrder.push(matrix[i][matrix[i].length - 1])
        }
        matrix = cutMatrix(matrix, undefined, matrix[0].length - 1)
        printOrder.push(...traverseRow(matrix, "rtl"));
        return printOrder;
    } else {
        for (let i = matrix.length - 1; i >= 0; i--) {
            printOrder.push(matrix[i][0])
        }
        matrix = cutMatrix(matrix, undefined, 0)
        printOrder.push(...traverseRow(matrix, "ltr"));
        return printOrder;
    }
}

const cutMatrix = (matrix, row, column) => {
    let m = matrix.filter((_, index) => index !== row);
    m = m.map(element => {
        return element.filter((_, aCol) => aCol !== column) 
    });
    return m;
}

const spiralOrder = function(matrix = [[]]) {
    const result = traverseRow(matrix, "ltr");
    return result.filter(a => a !== undefined)
};

let Output = [1,2,3,6,9,8,7,4,5]
let matrix = [[1,2,3],[4,5,6],[7,8,9]]
console.log(spiralOrder(matrix))
console.log(Output)
let Output2 = [1,2,3,4,8,12,11,10,9,8,5,6,7]
let matrix2 = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
console.log(spiralOrder(matrix2))
console.log(Output2)
let Output3 = [7,9, 6]
let matrix3 = [[7],[9],[6]]
console.log(spiralOrder(matrix3))
console.log(Output3)
