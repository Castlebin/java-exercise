package com.heller.dynamic.proxy;

public class Student implements People {
    @Override
    public void sayHello() {
        System.out.println("I'm a student.");
    }
}
