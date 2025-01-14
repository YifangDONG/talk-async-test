
/**
 *  Copyright Murex S.A.S., 2003-2025. All Rights Reserved.
 *
 *  This software program is proprietary and confidential to Murex S.A.S and its affiliates ("Murex") and, without limiting the generality of the foregoing reservation of rights, shall not be accessed, used, reproduced or distributed without the
 *  express prior written consent of Murex and subject to the applicable Murex licensing terms. Any modification or removal of this copyright notice is expressly prohibited.
 */
package example;

import java.util.concurrent.Executors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;


public class SessionLayerMockitoTest {
    @Test
    void mockito_solution() {
        Service service = new SessionLayer(Executors.newFixedThreadPool(1));
        Client client = Mockito.mock(Client.class);
        service.subscribe(client);

        doAnswer(
            (Answer<Void>) invocation -> {
                String command = (String) invocation.getArguments()[0];
                service.executeCommand(command);
                return null;
            }).when(client).sendRequest(anyString());

        client.sendRequest("Calculate");

        verify(client, timeout(1000)).onNotify("Finished to execute command Calculate");
    }
}
