module ca.georgiancollege.assignment_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens ca.georgiancollege.assignment_2 to javafx.fxml, org.json;
    exports ca.georgiancollege.assignment_2;
}