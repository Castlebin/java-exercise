## Java 动态代理

Java 动态代理主要由 Proxy 和 InvocationHandler 两个类来实现。
Java 语言自身的动态代理只支持对 接口（ interface ） 的代理。

Proxy.newProxyInstance 内部使用了字节码操作技术，生成代理类的字节码，并且实例化一个代理对象。
从而实现动态代理

目录下 $Proxy0.class 是 为示例中 People 类 的 生成的字节码（默认并不生成文件）
通过 IDE 自带的反编译，可以查看生成的 Java 代码


