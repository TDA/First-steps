package dp_recursion;

public class JumpingGame {
    public boolean canJump(int[] nums) {
        if (nums.length < 2) return true;
        int maxReachable = 0;
        for (int i = 0; i < nums.length; i++) {
            // if at any point the index is greater than maxReachable, we have reached an unreachable state
            if (i > maxReachable) return false;
            maxReachable = Math.max(maxReachable, i + nums[i]);
        }
        return false;
    }

    public static void main(String[] args){
        JumpingGame jumpingGame = new JumpingGame();
        int[] nums = {2,3,1,1,4};
        System.out.println(jumpingGame.canJump(nums));
        int[] nums2 = {3,2,1,0,4};
        System.out.println(jumpingGame.canJump(nums2));
    }
}
