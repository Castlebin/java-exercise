package T20250324.武汉织云;

import java.util.Random;

public class MarkString {
    
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        MarkString test = new MarkString();
        String s = test.makeString("[10-52]xxx-AC-xx");
        System.out.println(s);

        System.out.println(test.makeString("xxx"));
        System.out.println(test.makeString("[10-52]xxx"));
        System.out.println(test.makeString("M[C-Z][33-66]"));
        System.out.println(test.makeString("[0-8][0-7]xxxx"));
        System.out.println(test.makeString("M[3-4]C [0-1]C[1-4]"));
    }

    public String makeString(String template) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index < template.length()) {
            char c = template.charAt(index);
            if (c == 'x') {
                sb.append(generateOneNumStr());
                index++;
            } else if (c == '[') {
                int endIndex = template.indexOf(']', index);
                String range = template.substring(index + 1, endIndex);
                String[] pair = range.split("-");
                String first = pair[0];
                String last = pair[1];
                char firstChar = first.charAt(0);
                if (firstChar >= '0' && firstChar <= '9') {
                    sb.append(generateNumStr(first, last));
                } else {
                    sb.append(generateCharStr(first, last));
                }
                index = endIndex + 1;
            } else {
                sb.append(c);
                index++;
            }
        }
        return sb.toString();
    }

    private String generateOneNumStr() {
        return String.valueOf(RANDOM.nextInt(10));
    }
    private String generateNumStr(String first, String last) {
        int start = Integer.parseInt(first);
        int end = Integer.parseInt(last);
        return String.valueOf(RANDOM.nextInt(end - start + 1) + start);
    }
    private String generateCharStr(String first, String last) {
        char start = first.charAt(0);
        char end = last.charAt(0);
        return String.valueOf((char) (RANDOM.nextInt(end - start + 1) + start));
    }

}

/*
根据 字符串模板表达式 随机生成一个字符串实例

例如:
模板 xxx 可生成 000~999
模板 [10-52]xxx 可生成 10000~52999
模板 M[C-Z][33-66] 可生成 MC33~MZ66

模板规则说明:
x: 0-9 随机数字
[n1-n2]: 数字范围占位符 生成 [n1,n2] 区间内的任意一个正整数
[A-Z]: 字母范围占位符 随机生成 A-Z 任意字母
其他字符: 原样保留

 假定输入模板肯定符合格式要求
无需处理格式异常

应用场景 - 生成全球各国邮编：
德国 xxxxx
西班牙 [10-52]xxx
中国 [0-8][0-7]xxxx
加拿大 M[3-4]C [0-1]C[1-4]
**/

/*
 * @param template 字符串模板
 * @return 根据字符串模板生成的字符串实例
 */
/*

    public String makeString(String template) {
        // TODO
    }
*/
/*
调用方法：

    makeString("[10-52]xxx-AC-xx");  // 返回 "15673-AC-82"

*/
