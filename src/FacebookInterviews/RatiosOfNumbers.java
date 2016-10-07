package FacebookInterviews;

import java.util.HashMap;

/**
 * Created by schandramouli on 10/4/16.
 */
public class RatiosOfNumbers {
    char num;
    char denom;
    float value;

    public RatiosOfNumbers(char num, char denom, float value) {
        this.num = num;
        this.denom = denom;
        this.value = value;
    }

    public static void main(String[] args) {
        // input is something like:
        // A B 1.0
        // B C 0.8
        // C D 0.5
        // o/p is to answer "What is A/D?"

        // naive approach here is to compare every pair of nums/denoms
        // lets start with that
        RatiosOfNumbers r1 = new RatiosOfNumbers('A', 'B', 1.0F);
        RatiosOfNumbers r2 = new RatiosOfNumbers('B', 'C', 0.8F);
        RatiosOfNumbers r3 = new RatiosOfNumbers('B', 'D', 0.2F);
        RatiosOfNumbers r4 = new RatiosOfNumbers('C', 'D', 0.5F);
        RatiosOfNumbers[] ratios = {r1, r2, r3, r4};

        // on second thought, a graph based approach seems almost perfect for
        // this problem, and it makes sense, cuz FB is huge into graphs
        // lets rep the ratios into an adj matrix
        // like so:
        //    A  B  C  D
        // A  0  1
        // B     0 0.8 0.2
        // C        0 0.5
        // D           0

        // we need some assumptions here: 0 means both unreachable, and same node.
        // any positive value means a connection, e.g. A,B -> 1
        // how do we model this? easy enough, we use a hashmap to store the node to col/row maps
        // best thing is our matrix is square, with a max width/height of 26

        HashMap<Character, Integer> rowColMaps = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            rowColMaps.put((char)(65 + i), i);
        }
        // eazy peezy huh?
        System.out.println(rowColMaps);

        float[][] adjMatrix = new float[26][26];
        // by first impression after seeing this program was a hashmap of subhashes
        // like so: {num -> (denoms)} where denoms = {denom1 -> (denoms1), denom2 -> (denoms2) + ..}
        // but thats basically a matrix LOL
        // by default, all will be init'd to 0
        // now lets iterate through each input and add them to the matrix (no pun intended)
        for (RatiosOfNumbers r : ratios) {
            // or however we get i/p, from cmdline would be easier tbh, no need for a stupid class then
            // but i created the class anyway so
            char num = r.num;
            char denom = r.denom;
            float val = r.value;
            // insert into col/row, nothing fancy, just use the mapping we defined above
            adjMatrix[rowColMaps.get(num)][rowColMaps.get(denom)] = val;
        }

        // lets check if we did it right
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                System.out.print(adjMatrix[i][j] + "\t");
            }
            System.out.println();
        }

        // cool now the bulk of the logic, tbh we can skip everything above for an interview
        // whats below is important, can move below into a hlper for clearer code, if needed
        System.out.println(computeRatio(rowColMaps.get('A'), rowColMaps.get('D'), adjMatrix));
    }

    private static float computeRatio(int num, int denom, float[][] adjMatrix) {
        int eventualDenom = denom;
        // check if we have a direct value
        if (adjMatrix[num][denom] == 0) {
            // means we need to check for further matching denoms
            // for each denom of num, we need to search for values, and
            // check if they in turn can reach eventualDenom
            // can do BFS OR DFS here, same width and height possibly so.
            // but im going with DFS cuz:
            //  1. searching for denoms in the same row is easier
            //  2. Intuition feels that there will be lesser checks on average
            for (int i = 0; i < 26; i++) {
                if (i == num || i == denom) {
                    continue;
                }
                if (adjMatrix[num][i] != 0) {
                    // recursively check if those can lead to eventualDenom
                    return adjMatrix[num][i] * computeRatio(i, eventualDenom, adjMatrix);
                }
            }

        } else {
            return adjMatrix[num][denom];
        }
        return 0;
    }

    @Override
    public String toString() {
        return "RatiosOfNumbers{" + num +
                "/" + denom +
                ", " + value +
                '}';
    }
}
