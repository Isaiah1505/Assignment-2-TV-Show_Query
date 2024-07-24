module ca.georgiancollege.assignment_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens ca.georgiancollege.assignment_2 to javafx.fxml;
    exports ca.georgiancollege.assignment_2;
}