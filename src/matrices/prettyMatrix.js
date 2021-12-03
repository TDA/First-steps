const prettyMatrix = (matrix = [[]]) => {
    for (let i = 0; i < matrix.length; i++) {
        for (let j = 0; j < matrix[0].length; j++) {
            process.stdout.write(matrix[i][j] + " ");
        }
        process.stdout.write("\n");
    }
};

module.exports = {
    prettyMatrix
}