package ca.georgiancollege.assignment_2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;


public class SearchPageController {
    @FXML
    private TextField searchBar;

    @FXML
    private Label numOfResults;

    @FXML
    private Button searchButton;

    @FXML
    private Button goToShow;

    @FXML
    private ListView<String> showList;

    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;

    public String[] foodList = {"apples","oranges","pears"};

    public void initialize(){
        // Test Code for ListView and numOfResults label
        showList.getItems().addAll(foodList);
        numOfResults.setText(String.valueOf(showList.getItems().size()));


    }

    public void searchTVShow(ActionEvent event){
        String userSearch = searchBar.getText();

        // After results from search are found
        numOfResults.setText(String.valueOf(showList.getItems().size()));
    }

    public void switchToInfoPage(ActionEvent event) throws IOException {
        if(showList.getSelectionModel().getSelectedItem() != null) {
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Info-Page.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        }
    }


}