package heller.java8.exercises;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Ch03Exercises {

    int addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, Integer::sum);
    }

    // 6. 计算一个字符串中小写字母的个数
    public static int countLowercaseLetters(String str) {
        return (int) Optional.of(str).orElse("").chars()
                .filter(Character::isLowerCase)
                .count();
    }

    // 7. 找出包含最多小写字母的字符串
    public static Optional<String> findStr(List<String> strs) {
        return Optional.of(strs).orElse(new ArrayList<>())
                .stream()
                .max(Comparator.comparing(Ch03Exercises::countLowercaseLetters));
    }

}
