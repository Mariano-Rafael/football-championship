package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Club;
import models.services.match.tournament.Championship;
import models.services.match.tournament.Group;

import java.io.IOException;

public class ViewGroupsScreenController {

    @FXML
    private VBox groupsContainer;

    private Championship championship;

    public void setChampionship(Championship championship) {
        this.championship = championship;
        displayGroups();
    }

    public void displayGroups() {
        if (championship != null && championship.getGroups() != null) {
            for (Group grupo : championship.getGroups()) {
                VBox groupVBox = new VBox(5);
                Label groupLabel = new Label("Grupo " + grupo.getGroupId());
                groupVBox.getChildren().add(groupLabel);
                for (Club club : grupo.getClubs()) {
                    Label timeLabel = new Label("- " + club.name());
                    groupVBox.getChildren().add(timeLabel);
                }
                groupsContainer.getChildren().add(groupVBox);
            }
        }
    }

    @FXML
    protected void handleSimulateGroupStage(javafx.event.ActionEvent event) throws IOException {
        // Lógica para simular a fase de grupos e navegar para a próxima tela
        System.out.println("Simular Fase de Grupos selecionado!");
        loadSimulateGroupStageScreen(event);
    }

    private void loadSimulateGroupStageScreen(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/personal/app_football_championship/view/simulate-group-stage-screen.fxml"));
        Parent simulateGroupStageRoot = fxmlLoader.load();
        // Passar o campeonato para o próximo controlador se necessário
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(simulateGroupStageRoot, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}