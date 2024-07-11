package concept;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class CompletableFutureExampleTest {

    @Test
    void create_completable_future() {
        var future = new CompletableFuture<String>();
        assertFalse(future.isDone());

        future.complete("Application is ready to use");

        assertTrue(future.isDone());
        assertEquals("Application is ready to use", future.join());
    }

    @Test
    void execute_completable_future_in_separate_thread() {
        var future = CompletableFuture.runAsync(() -> {
            // Simulate time consuming execution
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("execute other task in main thread");
        var start = System.nanoTime();
        future.join();
        var elapsed = System.nanoTime() - start;
        System.out.printf("Finished executing after after %d ms\n", elapsed / 1000000);
    }

    @Test
    void complete_completable_future_in_separate_thread() {
        var future = CompletableFuture.supplyAsync(() -> {
            // Simulate time consuming execution
            try {
                Thread.sleep(1000);
                return "Hello " + Thread.currentThread().getName();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        var start = System.nanoTime();
        var result = future.join();
        var elapsed = System.nanoTime() - start;
        System.out.println("result: " + result);
        System.out.printf("get result after %d ms\n", elapsed / 1000000);
    }

    @Test
    void chain_operation_on_complete_completable_future() {
        var future = CompletableFuture.
            supplyAsync(() -> {
                // Simulate time consuming execution
                try {
                    Thread.sleep(1000);
                    return "First task is finished executing in " + Thread.currentThread().getName() + "\n";
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, Executors.newSingleThreadScheduledExecutor())
            .thenApply(result -> {
                try {
                    Thread.sleep(1000);
                    return result + "Second task is finished executing in " + Thread.currentThread().getName() + "\n";
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            })
            .exceptionally(exception -> "task is fail because " + exception);
        var start = System.nanoTime();
        var result = future.join();
        var elapsed = System.nanoTime() - start;
        System.out.printf("result: %s", result);
        System.out.printf("get result after %d ms\n", elapsed / 1000000);
    }

    @Test
    void handle_exception() {
        var executorService = Executors.newFixedThreadPool(1);
        var future = CompletableFuture
            .supplyAsync(() -> {
                // Simulate time consuming execution
                try {
                    Thread.sleep(1000);
                    return "First task is finished executing in " + Thread.currentThread().getName() + "\n";
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, executorService)
            .exceptionally(exception -> "task is fail because " + exception.getMessage());
        executorService.shutdownNow();

        var result = future.join();
        System.out.printf("result: %s", result);
    }
}
