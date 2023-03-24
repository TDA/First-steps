package Graphs;

public enum Directions {
    LEFT(0, -1),
    RIGHT(0, 1),
    TOP(-1, 0),
    BOTTOM(1, 0);
    public int x, y;
    Directions(int i, int j) {
        this.x = i;
        this.y = j;
    }
}
