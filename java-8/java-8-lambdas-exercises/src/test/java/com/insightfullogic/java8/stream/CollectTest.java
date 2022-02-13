package com.insightfullogic.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

public class CollectTest {

    /**
     * collect toMap key冲突会抛出异常
     *
     * Function.identity() 表示保持原样， num -> num 不做转换
     */
    @Test(expected = IllegalStateException.class)
    public void testCollectToMap() {
        List<String> nums = Arrays.asList("1", "2", "1", "3");
        Map<String, String> map = nums.stream()
                .collect(Collectors.toMap(num -> num, Function.identity()));
    }

    /**
     * collect toSet 重复并不会有问题！
     */
    @Test
    public void testCollectToSet() {
        List<String> nums = Arrays.asList("1", "2", "1", "3");
        Set<String> set = nums.stream()
                .collect(Collectors.toSet());
    }

}
