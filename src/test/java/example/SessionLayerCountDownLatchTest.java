
/**
 *  Copyright Murex S.A.S., 2003-2025. All Rights Reserved.
 *
 *  This software program is proprietary and confidential to Murex S.A.S and its affiliates ("Murex") and, without limiting the generality of the foregoing reservation of rights, shall not be accessed, used, reproduced or distributed without the
 *  express prior written consent of Murex and subject to the applicable Murex licensing terms. Any modification or removal of this copyright notice is expressly prohibited.
 */
package example;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class SessionLayerCountDownLatchTest {

    @Test
    public void count_down_latch_solution() throws InterruptedException {
        Service service = new SessionLayer();
        SynchronizedClient client = new SynchronizedClient();
        client.subscribe(service);

        client.sendRequest("Calculate");

        assertEquals("Finished to execute command Calculate", client.getLastNotification());
    }

    static class SynchronizedClient implements Client {

        private Service service;
        private CountDownLatch countDownLatch;
        private String lastNotification;

        public SynchronizedClient() {
        }

        @Override
        public void subscribe(Service service) {
            this.service = service;
            this.service.subscribe(this);
        }

        @Override
        public void onNotify(String notification) {
            lastNotification = notification;
            countDownLatch.countDown();
        }

        @Override
        public void sendRequest(String command) {
            countDownLatch = new CountDownLatch(1);
            service.executeCommand(command);
        }

        public String getLastNotification() throws InterruptedException {
            countDownLatch.await();
            return lastNotification;
        }
    }
}
