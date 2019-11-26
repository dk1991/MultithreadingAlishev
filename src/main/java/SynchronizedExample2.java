import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchronizedExample2 {

    public static void main(String[] args) throws InterruptedException {
        new Worker().main();
    }
}

class Worker {
    private Random random = new Random();

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    private List<Integer> list1 = new ArrayList<Integer>();
    private List<Integer> list2 = new ArrayList<Integer>();

    public void addToList1() {
        synchronized (lock1) { // блок синхронизации
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list1.add(random.nextInt(100));
        }
    }

    public  void addToList2() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list2.add(random.nextInt(100));
        }
    }

    public void work() {
        for (int i = 0; i < 1000; i++) {
            addToList1();
            addToList2();
        }
    }

    public void main() {
        long before = System.currentTimeMillis();

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                work();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                work();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long after = System.currentTimeMillis();
        System.out.println("Program was running for " + (after - before) + " ms");

        System.out.println("List1 " + list1.size());
        System.out.println("List2 " + list2.size());
    }
}
