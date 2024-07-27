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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


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
        String userSearch = (searchBar.getText()).replaceAll(" ","+");
        System.out.println(userSearch);
        try {
            // add userSearch to end of URL soon
            URL url = new URL("https://api.tvmaze.com/search/shows?q=girls");
            HttpsURLConnection apiConnection = (HttpsURLConnection) url.openConnection();
            apiConnection.setRequestMethod("GET");
            apiConnection.connect();

            if(apiConnection.getResponseCode() != 200){
                System.out.println(apiConnection.getResponseCode());
            }else if(apiConnection.getResponseCode() == 200){
                System.out.println("Connection Made.");
            }
            BufferedReader showInfoReader = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
            String showInfo = showInfoReader.readLine();
            System.out.println(showInfo);

            JSONParser showNameParser = new JSONParser();
            Object rawShowInfo = showNameParser.parse(showInfo);
            JSONObject parsedShowInfo = (JSONObject) rawShowInfo;

            String showNames =  String.valueOf(parsedShowInfo.get("name"));
            //System.out.println(showNames);

            showInfoReader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
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