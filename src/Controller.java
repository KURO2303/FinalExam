import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
    @FXML private TextField hiUser;
    @FXML private PasswordField hiPass;
    @FXML private Button loginButton;

    @FXML
    void LoginClicked(ActionEvent event) {
        String username = hiUser.getText();
        String password = hiPass.getText();
        if (username.equals("DucK123") && password.equals("123456")){
            try {
                Parent mainInterface = FXMLLoader.load(getClass().getResource("MI.fxml"));
                Scene mainScene = new Scene(mainInterface); 
                Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
                window.setScene(mainScene);
                window.show();
            } catch (IOException e) {}
        } else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Invalid.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {}
        }
    }

    @FXML
    void CancelClicked1(ActionEvent event) throws IOException {
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("WP.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
    }

    @FXML
    void OK1Clicked(ActionEvent event) throws IOException {
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("EI.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
    }

    @FXML
    void BOOKClicked(ActionEvent event) throws IOException {
        Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("NewGuest.fxml"));
        Scene NewGuestScene = new Scene(NewGuestInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewGuestScene);
        window.show();
    }

    
    @FXML
    void ListClicked(ActionEvent event) throws IOException {
        Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("RL.fxml"));
        Scene NewGuestScene = new Scene(NewGuestInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewGuestScene);
        window.show();
    }

    @FXML
    void OK4Clicked(ActionEvent event) throws IOException {
        Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
        Scene NewGuestScene = new Scene(NewGuestInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewGuestScene);
        window.show();
    }

    
    @FXML
    void ViewClicked(ActionEvent event) throws IOException {
        Parent ViewInterface = FXMLLoader.load(getClass().getResource("RoomList.fxml"));
        Scene NewViewScene = new Scene(ViewInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewViewScene);
        window.show();
    }

    @FXML
    void XClicked(ActionEvent event) throws IOException {
        Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
        Scene NewGuestScene = new Scene(NewGuestInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewGuestScene);
        window.show();
    }
}

