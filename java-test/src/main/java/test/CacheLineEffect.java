package test;

/**
 * 缓存行对齐效应
 */
public class CacheLineEffect {
    // 考虑一般缓存行大小是 64 字节，一个 long 类型占 8 字节
    static long[][] arr;

    public static void main(String[] args) {
        arr = new long[1024 * 1024][];
        for (int i = 0; i < 1024 * 1024; i++) {
            arr[i] = new long[8]; // 64 个字节，刚好占一个缓存行
            for (int j = 0; j < 8; j++) {
                arr[i][j] = 0L;
            }
        }
        long num = 0L;
        // 可以看到，第一种遍历方式比第二种遍历方式要快很多，倍数级别的快
        long marked = System.currentTimeMillis();
        for (int i = 0; i < 1024 * 1024; i += 1) {
            for (int j = 0; j < 8; j++) {// 8 个 long 类型，刚好占满一个缓存行
                num = arr[i][j];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        for (int j = 0; j < 8; j += 1) {
            for (int i = 0; i < 1024 * 1024; i++) {
                num = arr[i][j];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");
    }
}
