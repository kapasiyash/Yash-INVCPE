import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class InvokeAll {

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(5);
        
        FutureTask<String> futureOne = new FutureTask<String>(new Task1<String>());
        FutureTask<String> futureTwo = new FutureTask<String>(new Task2<String>());
        service.execute(futureOne);
        service.execute(futureTwo);

        System.out.println(futureTwo.get());
        System.out.println(futureOne.get());
    }

    private static class Task1<String> implements Callable<String>{

        @Override
        public String call() throws Exception {
//            Thread.sleep(1000 * 10);
            return (String) "1000 * 5";
        }

    }

    private static class Task2<String> implements Callable<String>{

        @Override
        public String call() throws Exception {
//            Thread.sleep(1000 * 2);
            int i=3;
            return (String) "1000 * 2";
        }

    }
}