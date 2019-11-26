public class SynchronizedExample {
    private int counter;

    public static void main(String[] args) throws InterruptedException {
        SynchronizedExample test = new SynchronizedExample();
        test.doWork();
    }

    private synchronized void increment() {
        counter++;
    }

    private void increment2() {
        synchronized (this) { // блок синхронизации
            counter++;
        }
    }

    /*
        1 thread: 100 -> 101 -> 101 -> 102 -> 102 -> 103 -> 103
        2 thread: 100 -> 101 -> 101

     */
    private void doWork() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i ++) {
                    increment();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i ++) {
                    increment();
                    //counter = counter + 1; // counter++ - не атомарная операция (не выполняется за 1 шаг)
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter);
    }
}

