package example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


class SessionLayerBasicTest {

    @Test
    public void the_basic_test() {
        Service service = new SessionLayer();
        MyTestClient client = new MyTestClient();
        client.subscribe(service);

        client.sendRequest("Calculate");

        assertEquals("Finished to execute command Calculate", client.getLastNotification());
    }

    @Test
    public void thread_sleep_solution() throws InterruptedException {
        Service service = new SessionLayer();
        MyTestClient client = new MyTestClient();
        client.subscribe(service);

        client.sendRequest("Calculate");

        Thread.sleep(1000);
        assertEquals("Finished to execute command Calculate", client.getLastNotification());
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