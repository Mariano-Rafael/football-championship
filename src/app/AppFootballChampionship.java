package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application; // Importe a classe correta do JavaFX

import java.io.IOException;

public class AppFootballChampionship extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/personal/app_football_championship/view/main-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // Define o tamanho da janela
        stage.setTitle("Gerenciador de Campeonato");
        stage.setScene(scene);
        stage.show();

        System.out.println("Executou");
    }

    public static void main(String[] args) {
        launch();
    }
}