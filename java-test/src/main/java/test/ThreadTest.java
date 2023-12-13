package test;

public class ThreadTest {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Sleep 1s"); // 正常输出
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Sleep 3s"); // 正常输出
        }).start();


        Thread daemonThread = new Thread(() -> {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("daemon Sleep 7s"); // 不会输出
        });
        daemonThread.setDaemon(true);
        daemonThread.start();

        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Sleep 5s"); // 正常输出
        }).start();


        System.out.println("Main"); // 正常输出
    }

}
