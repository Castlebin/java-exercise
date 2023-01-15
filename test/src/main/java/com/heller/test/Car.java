package com.heller.test;

import lombok.*;

@Builder
public class Car {

    private String name;
    private String color;
    private int age;

}
/**
 查看反编译后的 class 文件，易知 Builder 模式实现方法
 */