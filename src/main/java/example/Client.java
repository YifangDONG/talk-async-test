package example;

public interface Client {

    void subscribe(Service service);

    void onNotify(String notification);

    void sendRequest(String command);
}
