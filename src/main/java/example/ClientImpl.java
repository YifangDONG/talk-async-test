package example;

public class ClientImpl implements Client {
    private Service service;

    public ClientImpl() {
    }

    @Override
    public void subscribe(Service service) {
        this.service = service;
        this.service.subscribe(this);
    }

    @Override
    public void onNotify(String notification) {
        // refresh screen and show the notification
    }

    @Override
    public void sendRequest(String command) {
        if (service == null) {
            throw new IllegalStateException("Service is not subscribed");
        }
        service.executeCommand(command);
    }
}
