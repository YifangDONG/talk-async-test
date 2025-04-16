package example;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class SessionLayerCompletableFutureTest {

    @Test
    public void completable_future_solution() {

        var service = new SessionLayer();
        var client = new SynchronizedClient();
        client.subscribe(service);

        client.sendRequest("Calculate");

        assertEquals("Finished to execute command Calculate", client.getLastNotification());
    }

    static class SynchronizedClient implements Client {
        private Service service;
        private CompletableFuture<String> lastNotification;

        public SynchronizedClient() {
        }

        @Override
        public void subscribe(Service service) {
            this.service = service;
            this.service.subscribe(this);
        }

        @Override
        public void onNotify(String aggregates) {
            lastNotification.complete(aggregates);
        }

        @Override
        public void sendRequest(String command) {
            lastNotification = new CompletableFuture<>();
            service.executeCommand(command);
        }

        public String getLastNotification() {
            return lastNotification.join();
        }
    }
}
