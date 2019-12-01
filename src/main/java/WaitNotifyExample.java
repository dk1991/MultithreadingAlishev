import java.util.Scanner;

public class WaitNotifyExample {
    public static void main(String[] args) throws InterruptedException {
        final WaitAndNotify wn = new WaitAndNotify();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wn.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wn.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}

class WaitAndNotify {
    private Object lock = new Object();

    public void produce() throws InterruptedException {
        synchronized (lock) { // синхронизация на объекте wn
            System.out.println("Producer thread was started...");
            lock.wait(); // 1 - отдаём intrinsic lock (монитор синхр объекта), 2 - ждем, пока будет вызван notify().
            System.out.println("Producer thread was resumed...");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);
        Scanner scanner = new Scanner(System.in);

        synchronized (this) {
            System.out.println("Waiting for return key pressed");
            scanner.nextLine();
            this.notify(); // this можно опускать
            notify(); // пробуждает только 1 поток
//            notifyAll(); // пробуждает все потоки

            Thread.sleep(3000);
        }
    }
}

