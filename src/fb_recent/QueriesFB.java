package fb_recent;

import java.util.ArrayList;
import java.util.Arrays;

public class QueriesFB {
    static class Query {
        public int type;
        public int index;

        public Query(int type, int index) {
            this.type = type;
            this.index = index;
        }
    }

    int[] answerQueries(ArrayList<Query> queries, int N) {
        int numOfGets = (int) queries.stream().filter(q -> q.type == 2).count(); // this needs to be GET length
        boolean[] dataset = new boolean[N];
        int[] results = new int[numOfGets];
        int counter = 0;

        for (Query query : queries) {
            if (query.type == 2) {
                results[counter++] = indexOfFirstTrue(dataset, query.index);
            } else if (query.type == 1) {
                dataset[query.index] = true;
            }
        }
        System.out.println(Arrays.toString(dataset));

        return results;
    }


    int indexOfFirstTrue(boolean[] arr, int startIndex) {
        // we need a modified BS here to improve TC
//        for (int i = startIndex; i < arr.length; i++) {
//            if (arr[i]) return i;
//        }
//        return -1;

        return modifiedSearch(arr, startIndex, arr.length - 1);
    }

    private int modifiedSearch(boolean[] arr, int startIndex, int endIndex) {
        int mostRecentSpotting = -1;
        if (arr[startIndex]) return startIndex;
        while (startIndex <= endIndex) {
            int mid = (startIndex + endIndex) / 2;
            if (arr[mid]) {
                // we need to move left after recording the mostrecent
                mostRecentSpotting = mid;
                endIndex = mid - 1;
            } else {
                // not yet seen
                startIndex = mid + 1;
            }
        }
        return mostRecentSpotting;
    }

    public static void main(String[] args){
        QueriesFB queriesFb = new QueriesFB();
        int [][] queries = {{2, 3}, {1, 2}, {2, 1}, {2, 3}, {2, 2}};
        ArrayList<Query> queryList = new ArrayList<>();
        for (int[] query : queries) {
            queryList.add(new Query(query[0], query[1]));
        }
        System.out.println(Arrays.toString(queriesFb.answerQueries(queryList, 5)));
    }
}
