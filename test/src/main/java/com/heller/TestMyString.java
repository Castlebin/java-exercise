package com.heller;

/**
 * 运行参数:

    java -Xbootclasspath/p:/Users/heller/study-code/design-pattern/test/target/classes com.heller.TestMyString

 */
public class TestMyString {
    public static void main(String[] args) {
        String myString = new String("test");
        System.out.println(myString);
    }
}

/**
 有个问题是，Java 中，可不可以自己写一个 String 类，覆盖掉原生的 String 类。

 类加载机制中，父类加载器委派加载原则，决定了不可以，直接回报错。

 但是，java 本事其实提供了可以替代的方案，就是上面的命令

     # 指定新的bootclasspath，替换java.*包的内部实现
        java -Xbootclasspath: your_App
     # a意味着append，将指定目录添加到bootclasspath后面
        java -Xbootclasspath/a: your_App
     # p意味着prepend，将指定目录添加到bootclasspath前面
        java -Xbootclasspath/p: your_App


 在 JDK 9 中，由于 Jigsaw 项目引入了 Java 平台模块化系统（JPMS），Java SE 的源代码被划分为一系列模块。
 类加载器，类文件容器等都发生了非常大的变化，我这里总结一下：
    前面提到的 -Xbootclasspath 参数不可用了。API 已经被划分到具体的模块，
    所以上文中，利用“-Xbootclasspath/p”替换某个 Java 核心类型代码，实际上变成了对相应的模块进行的修补，
    可以采用下面的解决方案：首先，确认要修改的类文件已经编译好，并按照对应模块（假设是 java.base）结构存放，
    然后，给模块打补丁：

        java --patch-module java.base=your_patch yourApp

 */