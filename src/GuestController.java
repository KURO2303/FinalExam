import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuestController {
    @FXML
    private TextField Address1;
    @FXML
    private TextField Address2;
    @FXML
    private TextField City;
    @FXML
    private TextField FirstName;
    @FXML
    private TextField LastName;
    @FXML
    private TextField Zip;
    @FXML
    private TextField Email;
    @FXML
    private TextField NAdults;
    @FXML
    private TextField NChild;
    @FXML
    private TextField Phone;
    @FXML
    private TextField Time1;
    @FXML
    private TextField Time2;

    @FXML
    void RoomReservationClicked(ActionEvent event) throws IOException {
        String[] guestInfo = new String[13];
        guestInfo[0] = FirstName.getText();
        guestInfo[1] = LastName.getText();
        guestInfo[2] = Zip.getText();
        guestInfo[3] = Address1.getText();
        guestInfo[4] = Address2.getText();
        guestInfo[5] = City.getText();
        guestInfo[6] = Email.getText();
        guestInfo[7] = NAdults.getText();
        guestInfo[8] = NChild.getText();
        guestInfo[9] = Phone.getText();
        guestInfo[10] = Time1.getText();
        guestInfo[11] = Time2.getText();
        saveToFile(guestInfo);

        Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("WP.fxml"));
        Scene NewGuestScene = new Scene(NewGuestInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewGuestScene);
        window.show();
    }

    private void saveToFile(String[] guestInfo){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("HotelData.dat", true))) {
            for (String info : guestInfo) {
                writer.write(info);
            }
        } catch (IOException e){}
    }
    
}



