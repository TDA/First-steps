import java.util.ArrayList;
import java.util.List;

public class CanPlaceFlowers {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (flowerbed.length == 0) return false;
        if (flowerbed.length == 1) {
            return flowerbed[0] != 1;
        }
        List<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] != 0) continue;
            if (i - 1 < 0) {
                if (flowerbed[i + 1] == 0) {
                    emptySlots.add(i);
                }
                continue;
            }
            if (i + 1 == flowerbed.length) {
                if (flowerbed[i - 1] == 0) {
                    emptySlots.add(i);
                }
                continue;
            }

            if (flowerbed[i - 1] == 1 || flowerbed[i + 1] == 1) continue;
            emptySlots.add(i);
            flowerbed[i] = 1;
        }
        System.out.println(emptySlots);
        return emptySlots.size() >= n;
    }

    public static void main(String[] args) {
        int[] flowerbed = {1,0,0,0,0,1};;
        System.out.println(new CanPlaceFlowers().canPlaceFlowers(flowerbed, 1));
        System.out.println(new CanPlaceFlowers().canPlaceFlowers(flowerbed, 2));
        int[] flowerbed2 = {0,0,1,0,1};
        System.out.println(new CanPlaceFlowers().canPlaceFlowers(flowerbed2, 1));
    }
}
