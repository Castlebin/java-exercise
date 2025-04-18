package T20250418.武汉小米;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 假设一维坐标上有 M 个 米家，坐标放在 miHomes 数组中
 * 有 N 个 米粉，坐标放在 miFans 数组中
 * 求所有 米粉距离他最近的米家的距离 的和
 *
 * 例如：miHomes = [1, 10, 7, 6]
 *      miFans = [8, 10, 5]
 * 那么最近距离分别为  (8 - 7 = 1) 、(10 - 10 = 0) 、(6 - 5 = 1)
 * 返回值为： 1 + 0 + 1 = 2
 */
public class MiDistance {

    public int miDistance(int[] miHomes, int[] miFans) {
        Arrays.sort(miHomes);
        int sum = 0;
        for (int miFan : miFans) {
            sum += miDistanceOne(miHomes, miFan);
        }
        return sum;
    }

    /**
     * 二分法查，找出距离最近的米家
     */
    public int miDistanceOne(int[] miHomes, int miFan) {
        int left = 0, right = miHomes.length - 1;
        int midDistance = Integer.MAX_VALUE;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (miFan == miHomes[mid]) {
                return 0;
            } else {
                midDistance = Math.min(Math.abs(miHomes[mid] - miFan), midDistance);
                if (miHomes[mid] > miFan) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return midDistance;
    }

    @Test
    void test() {
        Assertions.assertEquals(2, miDistance(new int[] {1, 10, 7, 6}, new int[] {8, 10, 5}));
        Assertions.assertEquals(8, miDistance(new int[] {10, 20, 30, 5, 1, 2, 9}, new int[] {7, 9, 10, 21, 25}));
    }

}
