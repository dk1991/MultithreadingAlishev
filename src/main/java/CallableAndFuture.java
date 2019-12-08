import java.util.Random;
import java.util.concurrent.*;

public class CallableAndFuture {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Starting");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Finished");
                return 5;
            }
        });
        // тоже самое что и выше call(), т.к. есть оператор return. Java сама это распознает
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("Starting");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished");
            Random random = new Random();
            int randomValue = random.nextInt(10);
            if (randomValue < 5) {
                throw new Exception("Something bad happend");
            }
            return randomValue;
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from thread");
            }
        });
        // тоже самое что и выше run, т.к. ничего не возвращаем
        executorService.submit(() -> System.out.println("Hello from thread"));


        executorService.shutdown();

        try {
            int result = future.get(); // get() дожидается окончания выпполнения потока. Получаем значение
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) { // здесь ловим исключение метода call()
            Throwable ex = e.getCause(); // получить исключение
            System.out.println(ex.getMessage()); // вывести сообщение исключения
        }
    }
}
