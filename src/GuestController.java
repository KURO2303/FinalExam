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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class GuestController {

    ObservableList<String> RegionList = FXCollections.observableArrayList("Asia", "Europe", "North America");
    ObservableList<String> CountryList = FXCollections.observableArrayList("VietNam", "France", "America");
    ObservableList<String> RoomIDList = FXCollections.observableArrayList("Normal", "V.I.P.", "Double Bed");

    @FXML private TextField Address;
    @FXML private TextField ID;
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
    @FXML private RadioButton Female;
    @FXML private RadioButton Male;
    @FXML private RadioButton Other;
    
    private ToggleGroup toggleGroup;
    private int USD;

    @FXML
    private void initialize() {
        Region.setItems(RegionList);
        RoomID.setItems(RoomIDList);
        Country.setItems(CountryList);
        toggleGroup = new ToggleGroup();
        Female.setToggleGroup(toggleGroup);
        Male.setToggleGroup(toggleGroup);
        Other.setToggleGroup(toggleGroup);
    }

    @FXML
    void RoomReservationClicked(ActionEvent event) throws IOException {//SAVE Guestinfo to HotelData.dat
        LocalDate startDate = Time1.getValue();
        LocalDate endDate = Time2.getValue();
        double TotalPrice = 0;
        double FinalPrice = 0;
        if (!MissInfo(startDate, endDate)) {
            Parent root = FXMLLoader.load(getClass().getResource("InvalidGuest.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            String selectedRoomID = RoomID.getValue();
            if (selectedRoomID != null) {//TO GET VALUE OF MONEY DECIDE ON WHICH ROOM WAS CHOSEN
                if (selectedRoomID.equals("Normal")) {
                    USD = 100;
                } else if (selectedRoomID.equals("V.I.P.")) {
                    USD = 250; 
                } else if (selectedRoomID.equals("Double Bed")) {
                    USD = 150; 
                } else {
                    USD = 0; 
                }
            }
    
            long stayDuration = calculateStayDuration(startDate, endDate);//CACULATE MONEY
            TotalPrice = USD * stayDuration;
            double tax = 0.15; 
            double T = TotalPrice * tax;
            FinalPrice = TotalPrice + T;
    
            String[] guestInfo = new String[24];
            guestInfo[0] = FirstName.getText();
            guestInfo[1] = LastName.getText();
            guestInfo[2] = Gender();
            guestInfo[3] = Address.getText();
            guestInfo[4] = ID.getText();
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
            guestInfo[16] = String.valueOf(T);
            guestInfo[17] = String.valueOf(USD);
            guestInfo[18] = String.valueOf(TotalPrice);
            guestInfo[19] = String.valueOf(FinalPrice);
            guestInfo[20] = Zip.getText();
            saveToFile(guestInfo);
    
            Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
            Scene NewGuestScene = new Scene(NewGuestInterface); 
            Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
            window.setScene(NewGuestScene);
            window.show();
        }
    }

    private boolean MissInfo(LocalDate startDate, LocalDate endDate) {//CHECK IF THE GUEST INPUT THEIR INFORMATION CORRECTLY
        return !FirstName.getText().isEmpty() && 
               !LastName.getText().isEmpty() &&
               !Gender().isEmpty() &&
               !Address.getText().isEmpty() &&
               !ID.getText().isEmpty() &&
               !City.getText().isEmpty() &&
               !Email.getText().isEmpty() &&
               !NAdults.getText().isEmpty() &&
               !NChild.getText().isEmpty() &&
               !Phone.getText().isEmpty() &&
               startDate != null &&
               endDate != null &&
               Region.getValue() != null &&
               Country.getValue() != null &&
               RoomID.getValue() != null &&
               (Male.isSelected() || Female.isSelected() || Other.isSelected()) &&
               startDate.isBefore(endDate); 
    }

    @FXML
    void X2Clicked(ActionEvent event) throws IOException {//RETURN TO GI.fxml
        Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
        Scene NewGuestScene = new Scene(NewGuestInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewGuestScene);
        window.show();
    }

    private void saveToFile(String[] guestInfo) {//METHOD TO SAVE GUEST INFO TO HotelData.dat
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
    

    private String stringToBinary(String str) {//BINARY READER
        StringBuilder binary = new StringBuilder();
        for (char c : str.toCharArray()) {
            String binaryChar = Integer.toBinaryString(c);
            binary.append(binaryChar).append(" ");
        }
        return binary.toString();
    }
    
    private long calculateStayDuration(LocalDate startDate, LocalDate endDate) {//METHOD TO CACULATE HOW MANY NIGHTS THE GUEST DECIDED TO STAY
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    private String Gender() {//OPERATING RATIO BUTTON 
        if (Male.isSelected()) {
            return "Male";
    } else if (Female.isSelected()) {
            return "Female";
    } else if (Other.isSelected()) {
            return "Other";
    } else {
            return "";
    }
    }
}