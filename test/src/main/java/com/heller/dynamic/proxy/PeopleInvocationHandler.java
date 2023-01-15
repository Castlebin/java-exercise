package com.heller.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PeopleInvocationHandler implements InvocationHandler {

    /**
     * 被代理的对象，必须在 InvocationHandler 的构造函数中传入，
     * 这样才知道，代理了哪个对象的职能
     */
    private People people;

    public PeopleInvocationHandler(People people) {
        this.people = people;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Begin...");
        method.invoke(people, args);
        System.out.println("End.");
        return null;
    }

}
