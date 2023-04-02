package test;

import java.util.function.Supplier;

import org.junit.Test;

import cn.hutool.log.Log;

/**
 * Lambda表达式 和 对象的方法引用可能并不完全等价，
 * 方法引用要求对象是非空的！！
 *
 * 因此注意！！不要无脑的直接立即使用方法引用代替Lambda表达式！！
 */
public class LambdaTest {

    @Test
    public void testLambda() {
        User user = null;
        System.out.println(getOrDefault(() -> user.getName(), "default"));
    }

    @Test
    public void testFunctionRef() {
        User user = null;
        // 会失败！！ 因为方法引用要求 user 是非空的！！
        // 都不会到 getOrDefault 方法里面去执行，异常不会被 getOrDefault 捕获到！！所以不会返回默认值！！
        // 是先计算出 方法引用的值，再进入 getOrDefault 方法的！！艹！！
        System.out.println(getOrDefault(user::getName, "default"));
    }

    @Test
    public void testFunctionRef2() {
        User user = null;
        // 方法引用无法连续调用？？？直接编译就不行了！！
        // System.out.println(getOrDefault(user::getSubUser::getAge, "default"));
    }

    @Test
    public void testFunctionRef4() {
        User user = null;
        // 直接调用方法当然可以连续，不会有编译问题
        System.out.println(getOrDefault(() -> user.getSubUser().getName(), "default"));
    }

    private static <T> T getOrDefault(Supplier<T> supplier, T defaultValue) {
        try {
            T value = supplier.get();
            if (value == null) {
                return defaultValue;
            }
            return value;
        } catch (Exception e) {
            // 发生异常后，也返回默认值
            Log.get().error("发生异常，返回默认值", e);
            return defaultValue;
        }
    }

    class User {
        private String name;
        private int age;

        private User subUser;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public User getSubUser() {
            return subUser;
        }

        public void setSubUser(User subUser) {
            this.subUser = subUser;
        }
    }

}
