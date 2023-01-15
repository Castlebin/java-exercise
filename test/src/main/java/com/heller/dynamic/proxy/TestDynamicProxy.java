package com.heller.dynamic.proxy;

import java.lang.reflect.Proxy;

public class TestDynamicProxy {
    public static void main(String[] args) {
        Student student = new Student();
        People people = (People) Proxy.newProxyInstance(student.getClass().getClassLoader(),
                new Class[]{People.class}, new PeopleInvocationHandler(student));
        people.sayHello();
    }
}
