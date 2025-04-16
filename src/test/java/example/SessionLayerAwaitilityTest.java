package example;

import static java.util.concurrent.TimeUnit.MINUTES;

import static org.awaitility.Awaitility.await;
import org.junit.jupiter.api.Test;


public class SessionLayerAwaitilityTest {
    @Test
    public void the_basic_test_with_awaitility() {
        Service service = new SessionLayer();
        MyTestClient client = new MyTestClient();
        client.subscribe(service);

        client.sendRequest("Calculate");

        await()
            .atMost(1, MINUTES)
            //            .pollInterval(Durations.ONE_HUNDRED_MILLISECONDS) // default value = ONE_HUNDRED_MILLISECONDS
            .until(() ->
                "Finished to execute command Calculate".equals(client.getLastNotification())
            );
    }

    static class MyTestClient implements Client {

        private Service service;
        private String lastNotification;

        public MyTestClient() {
        }

        @Override
        public void subscribe(Service service) {
            this.service = service;
            this.service.subscribe(this);
        }

        @Override
        public void onNotify(String aggregates) {
            lastNotification = aggregates;
        }

        @Override
        public void sendRequest(String command) {
            service.executeCommand(command);
        }

        public String getLastNotification() {
            return lastNotification;
        }
    }
}
