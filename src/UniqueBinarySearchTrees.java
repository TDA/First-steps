public class UniqueBinarySearchTrees {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        return extracted(n, dp);
    }

    private int extracted(int n, int[] dp) {
        if (n <= 1) return 1;
        if (dp[n] != 0) return dp[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            int right = n - i - 1;
            count += extracted(i, dp) * extracted(right, dp);
        }
        dp[n] = count;
        return count;
    }

    public static void main(String[] args) {

        System.out.println(new UniqueBinarySearchTrees().numTrees(1));
        System.out.println(new UniqueBinarySearchTrees().numTrees(2));
        System.out.println(new UniqueBinarySearchTrees().numTrees(3));
        System.out.println(new UniqueBinarySearchTrees().numTrees(4));
        System.out.println(new UniqueBinarySearchTrees().numTrees(5));
    }
}
