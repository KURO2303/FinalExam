import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate; 
import java.time.temporal.ChronoUnit; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuestController {

    ObservableList<String> RegionList = FXCollections.observableArrayList("Asia", "Europe", "North America");
    ObservableList<String> CountryList = FXCollections.observableArrayList("VietNam", "France", "America");
    ObservableList<String> RoomIDList = FXCollections.observableArrayList("Normal", "V.I.P.", "Double Bed");

    @FXML private TextField Address1;
    @FXML private TextField Address2;
    @FXML private TextField City;
    @FXML private TextField FirstName;
    @FXML private TextField LastName;
    @FXML private TextField Zip;
    @FXML private TextField Email;
    @FXML private TextField NAdults;
    @FXML private TextField NChild;
    @FXML private TextField Phone;
    @FXML private DatePicker Time1;
    @FXML private DatePicker Time2;
    @FXML private ChoiceBox<String> Region;
    @FXML private ChoiceBox<String> RoomID;
    @FXML private ChoiceBox<String> Country;

    @FXML
    private void initialize() {
        Region.setItems(RegionList);
        RoomID.setItems(RoomIDList);
        Country.setItems(CountryList);
    }

    @FXML
    void RoomReservationClicked(ActionEvent event) throws IOException {
        LocalDate startDate = Time1.getValue();
        LocalDate endDate = Time2.getValue();

        long stayDuration = calculateStayDuration(startDate, endDate);
        String[] guestInfo = new String[17];
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
        guestInfo[10] = Time1.getValue().toString();
        guestInfo[11] = Time2.getValue().toString();
        guestInfo[12] = Region.getValue();
        guestInfo[13] = Country.getValue();
        guestInfo[14] = RoomID.getValue();
        guestInfo[15] = String.valueOf(stayDuration);
        saveToFile(guestInfo);

        Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
        Scene NewGuestScene = new Scene(NewGuestInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewGuestScene);
        window.show();
    }

    private void saveToFile(String[] guestInfo) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("HotelData.dat", true)))) {
            for (String info : guestInfo) {
                if (info != null) {
                    String binaryString = stringToBinary(info);
                    writer.write(binaryString + ","); 
                }
            }
            writer.newLine();
            writer.flush(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private String stringToBinary(String str) {
        StringBuilder binary = new StringBuilder();
        for (char c : str.toCharArray()) {
            String binaryChar = Integer.toBinaryString(c);
            binary.append(binaryChar).append(" ");
        }
        return binary.toString();
    }
    
    private long calculateStayDuration(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}