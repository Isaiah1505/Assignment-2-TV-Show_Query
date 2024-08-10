package ca.georgiancollege.assignment_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


public class SearchPageController {
    @FXML
    private Label AppDescription;

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
    private Parent root;

    // Arrays to store show names and the show JSONObjects
    ArrayList<String> showNameArray = new ArrayList<String>();
    ArrayList<JSONObject> showObjArray = new ArrayList<JSONObject>();

    // gets number of results and adds it to label to start off
    public void initialize(){
        numOfResults.setText(String.valueOf(showList.getItems().size()));
    }
    // Connects and sends a response to API, then receives a response in the form of a JSONArray
    // Parses through array to find show names and display them in listview for user
    public void searchTVShow(ActionEvent event){
        // clears listview and arrays in case the user does multiple searches
        showList.getItems().clear();
        showNameArray.clear();
        showObjArray.clear();
        // replaces spaces w/+ so it's a valid URL
        String userSearch = (searchBar.getText()).replaceAll(" ","+");
        // checks if the search is blank before trying anything
        if(userSearch.isBlank()){
            AppDescription.setText("Search failed because searchbar was empty.");
        }else {
            AppDescription.setText("Searched For :" + userSearch);
            try {
                // creates a connection, appending user search on the end of URL
                URL url = new URL("https://api.tvmaze.com/search/shows?q=" + userSearch);
                HttpsURLConnection apiConnection = (HttpsURLConnection) url.openConnection();
                apiConnection.setRequestMethod("GET");
                apiConnection.connect();

                // reader grabs the JSONObject from the stream, then starts to parse the objects and arrays
                BufferedReader showInfoReader = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
                String showInfo = "{\"search\": {\"all\":" + showInfoReader.readLine() + "}}";
                JSONObject queriedShows = new JSONObject(showInfo);
                JSONArray showArray = queriedShows.getJSONObject("search").getJSONArray("all");
                JSONObject showObj;

                // each loop, an JSONObject is added to the JSONObject list, same with the show name
                // also a show name is added to the listview
                for (int i = 0; i < showArray.length(); i++) {
                    showObj = showArray.getJSONObject(i);
                    showObjArray.add(showObj);
                    showNameArray.add(showObj.getJSONObject("show").getString("name"));
                    showList.getItems().add(showObj.getJSONObject("show").getString("name"));

                }

                showInfoReader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // After results from search are found
        // updates the number of results
        numOfResults.setText(String.valueOf(showList.getItems().size()));
    }

    // switches to info page,only when an item is selected, via switching the scene
    // other controller object is created and initialize function is used to pass
    // the JSONObject array and the selected show name from the list view to other controller
    public void switchToInfoPage(ActionEvent event) throws IOException {
        if(showList.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Info-Page.fxml"));
            root = loader.load();
            String selectedItem = showList.getSelectionModel().getSelectedItem();
            InfoPageController infoPageController = loader.getController();
            infoPageController.initialize(showObjArray, selectedItem);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Show Detailed Page");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        }
    }


}