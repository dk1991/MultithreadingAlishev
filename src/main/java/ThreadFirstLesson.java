public class ThreadFirstLesson {
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start(); // запустить отдельный поток

        MyThread myThread2 = new MyThread();
        myThread2.start(); // запустить отдельный поток

        Thread thread = new Thread(new Runner());
        thread.start();
    }
}

class Runner implements Runnable {
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello from Runner " + i);
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello from MyThread " + i);
        }
    }
}
