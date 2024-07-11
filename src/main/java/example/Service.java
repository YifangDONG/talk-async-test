package example;

public interface Service {

    void subscribe(Client client);

    void executeCommand(String command);

    /*
    void unSubscribe(Client client);
    void destroy();
    ...
     */
}
