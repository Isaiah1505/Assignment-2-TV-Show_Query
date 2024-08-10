module ca.georgiancollege.assignment_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;


    opens ca.georgiancollege.assignment_2 to javafx.fxml, org.json;
    exports ca.georgiancollege.assignment_2;
}