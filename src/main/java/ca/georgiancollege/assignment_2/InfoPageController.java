package ca.georgiancollege.assignment_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoPageController {

    @FXML
    private Button backToSearch;

    @FXML
    private Label endDate;

    @FXML
    private Label genre;

    @FXML
    private Label infoPageTitle;

    @FXML
    private Label rating;

    @FXML
    private ImageView showImage;

    @FXML
    private Label startDate;

    @FXML
    private Label summary;

    @FXML
    private Label url;


    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;

    public void switchToSearchPage(ActionEvent event) throws IOException {
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Search-Page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
}
