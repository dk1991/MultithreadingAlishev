import java.util.Scanner;

public class VolatileExample {
    public static void main(String[] args) {
        MyThread1 myThread = new MyThread1();
        myThread.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        myThread.shutDown();
    }
}

class MyThread1 extends Thread {
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("Hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDown() {
        this.running = false;
    }
}