# 第17章 网络编程
## 17.1 网络编程的基础知识
## 17.2 Java的基本网络支持

## 17.3 基于TCP协议的网络编程
    17.3.7 使用NIO实现非阻塞Socket通信
        - 主要是使用Selector、SelectionKey、Channel来实现的，使用有点麻烦，需要熟练
            真是使用麻烦的API
            
    17.3.8 使用Java 7的AIO实现非阻塞通信
        繁杂、又是一个使用麻烦的API
        NIO.2提供了一系列以Asynchronous开头的接口和类，用于提供AIO功能