package virtual_thread;

// Java 的线程、虚拟线程 中发生异常，都只会导致当前线程退出，不会影响其他线程，更不会导致程序退出。
// （go 会，它需要在 goroutine 内部，采用 defer + recover 的方式去捕获并处理 panic,才不会导致程序整个退出）
public class ThreadPanicTest {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("p: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (i == 5) {
                    throw new RuntimeException("Panic");
                }
            }
        });
        t.start();

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("n: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t2.start();


        for (int i = 0; i < 100; i++) {
            System.out.println("m: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
