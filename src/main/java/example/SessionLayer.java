
/**
 *  Copyright Murex S.A.S., 2003-2025. All Rights Reserved.
 *
 *  This software program is proprietary and confidential to Murex S.A.S and its affiliates ("Murex") and, without limiting the generality of the foregoing reservation of rights, shall not be accessed, used, reproduced or distributed without the
 *  express prior written consent of Murex and subject to the applicable Murex licensing terms. Any modification or removal of this copyright notice is expressly prohibited.
 */
package example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


public class SessionLayer implements Service {
    private final Executor executor;
    private final List<Client> clients = new ArrayList<>();

    public SessionLayer(Executor executor1) {
        executor = executor1;
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