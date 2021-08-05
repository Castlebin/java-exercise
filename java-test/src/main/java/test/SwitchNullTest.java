package test;

public class SwitchNullTest {
    public static void main(String[] args) {
        /** switch 表达式条件值 不能为 null，会直接抛出异常 */
        System.out.println(mapDesc(null));
    }

    public static String mapDesc(Integer value) {
        switch (value) {
            case 1:
                return "正常";
            case 2:
                return "异常";
            default:
                return "未知";
        }
    }
}
