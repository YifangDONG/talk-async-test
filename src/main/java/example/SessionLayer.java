package example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class SessionLayer implements Service {
    private final Executor executor;
    private final List<Client> clients = new ArrayList<>();

    public SessionLayer() {
        this.executor = Executors.newFixedThreadPool(10);
    }

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