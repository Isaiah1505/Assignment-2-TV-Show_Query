package ca.georgiancollege.assignment_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


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
    private Hyperlink urlField;



    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;

    JSONObject grabbedShow;

    // method receives all jsonobjects in a list from user search and the selected model from the user
    public void initialize(ArrayList<JSONObject> showObjArray, String modelSelection){
        // loops through objects to find matching name w/user's selection
        for(int i = 0; i<showObjArray.size(); i++){
            grabbedShow = showObjArray.get(i);
            if(grabbedShow.getJSONObject("show").getString("name").equals(modelSelection)) {
                break;
            }
        }
        // once the correct show is found, the info page is populated
        populateInfoPage();
    }
    // adds all info to the infoPage, using the show object
    public void populateInfoPage() throws NumberFormatException {
        // each value is checked to see if it's null before adding, to avoid errors
        genre.setText("");
        infoPageTitle.setWrapText(true);
        if(grabbedShow.getJSONObject("show").getString("name") != null){
            infoPageTitle.setText(grabbedShow.getJSONObject("show").getString("name"));
        }
        if(grabbedShow.getJSONObject("show").getString("premiered") != null){
            startDate.setText(grabbedShow.getJSONObject("show").getString("premiered"));
        }
        if(grabbedShow.getJSONObject("show").getString("ended") != null){
            endDate.setText(grabbedShow.getJSONObject("show").getString("ended"));
        }
        // loops through genres to add them into genre section one by one
        for(int i = 0; i<grabbedShow.getJSONObject("show").getJSONArray("genres").length(); i++){
            if(genre.getText()+grabbedShow.getJSONObject("show").getJSONArray("genres").getString(i) != null){
                genre.setText(genre.getText()+grabbedShow.getJSONObject("show").getJSONArray("genres").getString(i)+", ");
            }
        }
        // removes extra characters at the end
        genre.setText(genre.getText().substring(0, genre.getText().lastIndexOf(", ")));
        genre.setWrapText(true);
        if(String.valueOf(grabbedShow.getJSONObject("show").getJSONObject("rating").getFloat("average")) != null){
            rating.setText(String.valueOf(grabbedShow.getJSONObject("show").getJSONObject("rating").getFloat("average")));
        }
        if(grabbedShow.getJSONObject("show").getString("url") != null){
            urlField.setText(grabbedShow.getJSONObject("show").getString("url"));
        }
        if(grabbedShow.getJSONObject("show").getString("summary") != null){
            summary.setText(grabbedShow.getJSONObject("show").getString("summary").replaceAll("<p>","").replaceAll("</p>",""));
        }
        summary.setWrapText(true);
        if(grabbedShow.getJSONObject("show").getJSONObject("image").getString("medium") != null){
            showImage.setImage(new Image(grabbedShow.getJSONObject("show").getJSONObject("image").getString("medium")));
        }

    }
    // allows user to click link on app and bring up a browser window/new tab if window is open
    public void toShowSite(ActionEvent event) throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI(urlField.getText()));
    }
    //switches back to search page, by way of change the view
    public void switchToSearchPage(ActionEvent event) throws IOException {
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Search-Page.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("TV Show Search");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
}
