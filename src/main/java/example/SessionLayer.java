package example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class SessionLayer implements Service {
    private final Executor executor = Executors.newFixedThreadPool(1);
    private final List<Client> clients = new ArrayList<>();

    @Override
    public void executeCommand(String command) {
        clients.forEach(observer ->
            executor.execute(
                () -> observer.onNotify("Finished to execute command %s".formatted(command)))
        );
    }

    @Override
    public void subscribe(Client client) {
        this.clients.add(client);
    }
}