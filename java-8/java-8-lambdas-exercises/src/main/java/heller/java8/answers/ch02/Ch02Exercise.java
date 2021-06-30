package heller.java8.answers.ch02;

import java.text.SimpleDateFormat;
import java.util.function.Predicate;

import javax.swing.text.DateFormatter;

public class Ch02Exercise {

    /*      // 无法做出类型推断
    public static void main(String[] args) {
        check(x -> x > 5);
    }*/

    public static boolean check(Predicate<Integer> predicate) {
        return true;
    }

    public static boolean check(IntPred predicate) {
        return true;
    }

}

class Question2 {

    public final static ThreadLocal<DateFormatter> DATE_FORMATTER_THREAD_LOCAL
            = ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("dd-MM-yyyy")));

}

interface IntPred {
    boolean test(Integer value);
}
