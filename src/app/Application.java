package app;

import javafx.stage.Stage;
import java.io.IOException;


public abstract class Application {
    public abstract void start(Stage stage) throws IOException;

    public static void main(String[] args) {

    }
}