package servlet;

import java.util.Optional;

public class Configuration {
    public static int boardSize() {
        return 20;
    }

    public static int serverPort() {
        return Optional.ofNullable(System.getenv("PORT")).map(Integer::parseInt).orElse(8080);
    }
}
