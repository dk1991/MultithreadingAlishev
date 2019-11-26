import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {
    public static void main(String[] args) throws InterruptedException {
        // создание пула потоков с 2 потоками
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Work(i)); // отправить задачу на выполнение
        }

        // останавливаем прием заданий и начинаем выполнение заданий переданных методу submit()
        executorService.shutdown(); // запустили выполнение заданий и вышли из метода shutdown()
        System.out.println("All tasks submitted");

        // ждем выполнение всех заданий в течение заданного времени
        // поток main здесь останавливается и ждет окончания работы
        executorService.awaitTermination(1, TimeUnit.HOURS);
    }
}

class Work implements Runnable {
    private int id;

    public Work(int id) {
        this.id = id;
    }

    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Work " + id + " was completed");
    }
}
