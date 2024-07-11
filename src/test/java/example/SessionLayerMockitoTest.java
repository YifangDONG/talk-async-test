package example;

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
        Service service = new SessionLayer();
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
