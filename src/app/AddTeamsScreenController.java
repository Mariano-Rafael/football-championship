package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Club;
import models.services.match.tournament.Championship;

import java.io.IOException;

public class AddTeamsScreenController {

    @FXML
    private TextField teamNameTextField;

    @FXML
    private ListView<String> teamsListView;

    private final ObservableList<String> teamNames = FXCollections.observableArrayList();
    private Championship championship;

    @FXML
    public void initialize() {
        teamsListView.setItems(teamNames);
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    @FXML
    protected void handleAddTeam(ActionEvent event) {
        String teamName = teamNameTextField.getText().trim();
        if (!teamName.isEmpty()) {
            championship.addClub(new Club(teamName));
            teamNames.add(teamName);
            teamNameTextField.clear();
        }
    }

    @FXML
    protected void handleCreateGroups(ActionEvent event) throws IOException {
        if (championship.getFormat().equals("GroupsAndKnockout") && championship.getClubs().size() >= championship.getClubsPerGroup()) {
            championship.createGroups();

            loadViewGroupsScreen(event);
        } else if (championship.getFormat().equals("Knockout") && championship.getClubs().size() >= 2) {
            championship.startKnockoutMatches();
            loadKnockoutStageScreen(event);
        } else {
            System.out.println("Número insuficiente de times para o formato selecionado.");

        }
    }

    private void loadViewGroupsScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/personal/app_football_championship/view/view-groups-screen.fxml"));
        Parent viewGroupsRoot = fxmlLoader.load();

        ViewGroupsScreenController controller = fxmlLoader.getController();
        controller.setChampionship(championship);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(viewGroupsRoot, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void loadKnockoutStageScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/personal/app_football_championship/view/knockout-stage-screen.fxml"));
        Parent knockoutStageRoot = fxmlLoader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(knockoutStageRoot, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}