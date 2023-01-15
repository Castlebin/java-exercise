package com.heller.bytecode;

import java.util.function.Consumer;

public class TestLambda {
    public static void main(String[] args) {
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("testStrForLambda");
    }
}

/**
 使用 javap -c -v -p xxx.class  即可生成对应的字节码文件

 -p 表示可以显示 private 信息（所以是所有的类和成员信息）

 */
