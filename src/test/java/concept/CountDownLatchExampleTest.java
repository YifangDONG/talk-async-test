package concept;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;


public class CountDownLatchExampleTest {

    @Test
    void count_down_latch_example() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Service("service 1", latch));
        executorService.submit(new Service("service 2", latch));
        executorService.submit(new Service("service 3", latch));
        latch.await();
        System.out.println("all services finished initialization");
    }

    static class Service implements Runnable {

        private final String serviceName;
        private final CountDownLatch latch;

        Service(String serviceName, CountDownLatch latch) {
            this.serviceName = serviceName;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.printf("Service %s finished initialization\n", serviceName);
            latch.countDown();
        }
    }
}
