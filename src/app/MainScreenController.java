package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    @FXML
    private Label welcomeText;

    @FXML
    private Button createNewChampionshipButton;

    @FXML
    private Button loadExistingChampionshipButton;

    @FXML
    protected void handleCreateNewChampionship(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/personal/app_football_championship/view/new-championship-screen.fxml"));
        Parent newChampionshipRoot = fxmlLoader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(newChampionshipRoot, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handleLoadExistingChampionship(ActionEvent event) {
        welcomeText.setText("Carregar Campeonato Existente selecionado!");

    }
}
