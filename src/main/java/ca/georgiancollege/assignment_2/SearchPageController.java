package ca.georgiancollege.assignment_2;

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
    private FXMLLoader fxmlLoader;

    ArrayList<String> showNameArray = new ArrayList<String>();
    ArrayList<JSONObject> showObjArray = new ArrayList<JSONObject>();

    public void initialize(){
        // Test Code for ListView and numOfResults label
        numOfResults.setText(String.valueOf(showList.getItems().size()));
    }

    public void searchTVShow(ActionEvent event){
        showList.getItems().clear();
        showNameArray.clear();
        showObjArray.clear();
        String userSearch = (searchBar.getText()).replaceAll(" ","+");
        System.out.println(userSearch);
        if(userSearch.isBlank()){
            AppDescription.setText("Search failed because searchbar was empty.");
        }else {
            AppDescription.setText("Searched For :" + userSearch);
            try {
                // https://api.tvmaze.com/shows/1611 URL to use for specific search
                // (on TV show detailed view) 1611 is the show id
                URL url = new URL("https://api.tvmaze.com/search/shows?q=" + userSearch);
                HttpsURLConnection apiConnection = (HttpsURLConnection) url.openConnection();
                apiConnection.setRequestMethod("GET");
                apiConnection.connect();

                if (apiConnection.getResponseCode() != 200) {
                    System.out.println(apiConnection.getResponseCode());
                } else if (apiConnection.getResponseCode() == 200) {
                    System.out.println("Connection Made.");
                }
                BufferedReader showInfoReader = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
                String showInfo = "{\"search\": {\"all\":" + showInfoReader.readLine() + "}}";
                System.out.println(showInfo);
                JSONObject queriedShows = new JSONObject(showInfo);
                JSONArray showArray = queriedShows.getJSONObject("search").getJSONArray("all");
                JSONObject showObj;
                for (int i = 0; i < showArray.length(); i++) {
                    showObj = showArray.getJSONObject(i);
                    showObjArray.add(showObj);
                    System.out.println(showObj);
                    showNameArray.add(showObj.getJSONObject("show").getString("name"));
                    showList.getItems().add(showObj.getJSONObject("show").getString("name"));

                }

                System.out.println(showNameArray);
                System.out.println(showObjArray);

                showInfoReader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
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