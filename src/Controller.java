import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class Controller {//OPERATE MOST OF THE FUNCTIONS IN GUEST INTERFACE
    @FXML private TextField hiUser;
    @FXML private PasswordField hiPass;
    @FXML private Button loginButton;
    @FXML private Label DoubleBedRoom;
    @FXML private Label NormalRoom;
    @FXML private Label VipRoom;
    @FXML public ChoiceBox<String> RoomID;

    @FXML
    void LoginClicked(ActionEvent event) {//LOGIN CHECK
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
    void OK1Clicked(ActionEvent event) throws IOException {//RETURN TO EI.fxml FROM Invalid.fxml
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("EI.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
    }

    @FXML
    void OKClose1Clicked(ActionEvent event) throws IOException {//CLOSE NoNewGuest.fxml
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void OKClose2Clicked(ActionEvent event) throws IOException {//TO CLOSE SorryG.fxml
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void OKClose3Clicked(ActionEvent event) throws IOException {//TO CLOSE InvalidGuest.fxml
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void OKClose4Clicked(ActionEvent event) throws IOException {//CLOSE Added.fxml AND OPEN MI.fxml
        Parent mainInterface = FXMLLoader.load(getClass().getResource("MI.fxml"));
        Scene mainScene = new Scene(mainInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(mainScene);
        window.show();
    }
    
    @FXML
    void OKClose5Clicked(ActionEvent event) {//TO CLOSE NoInfo.fxml
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void SureNoClicked(ActionEvent event) {//CLOSE YouSure.fxml
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void SureYesClicked(ActionEvent event) throws IOException {//CLOSE YouSure.fxml AND DELETE GUEST INFO, OPEN SorryG.fxml
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("HotelData.dat"))) {
            String newData = "";
            writer.write(newData);
        } catch (IOException e) {}
        Parent root = FXMLLoader.load(getClass().getResource("SorryG.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        Stage ThisStage = (Stage)((Button) event.getSource()).getScene().getWindow();
        ThisStage.close();
    }

    @FXML
    void OK4Clicked(ActionEvent event) throws IOException {//OPEN GI.fxml
        Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
        Scene NewGuestScene = new Scene(NewGuestInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewGuestScene);
        window.show();
    }

    @FXML
    void CancelClicked1(ActionEvent event) throws IOException {//RETURN TO WP.fxml from EI.fxml
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("WP.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
    }

    @FXML
    void ViewClicked(ActionEvent event) throws IOException {//OPEN RoomList.fxml
        Parent ViewInterface = FXMLLoader.load(getClass().getResource("RoomList.fxml"));
        Scene NewViewScene = new Scene(ViewInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewViewScene);
        window.show();
    }

    @FXML
    void CancelBookingClicked(ActionEvent event) throws IOException {//CHECKING FOR CANCEL BOOKING
        if(CancelBooking()){
            Parent root = FXMLLoader.load(getClass().getResource("NoNewGuest.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("YouSure.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    void XClicked(ActionEvent event) throws IOException {//RETURN TO GI.fxml
        Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
        Scene NewGuestScene = new Scene(NewGuestInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewGuestScene);
        window.show();
    }

    @FXML
    void ReClicked(ActionEvent event){//SHOW NUMBER OF ROOM AVAILABLE AT THE MOMENT
        int NormalN = 5; 
        int DoubleBedN = 7; 
        int VipN = 2; 
    try (BufferedReader reader = new BufferedReader(new FileReader("HotelData.dat"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] guestInfo = line.split(",");
            if (guestInfo.length >= 15) { 
                String roomType = binaryToString(guestInfo[14]);
                switch (roomType) {
                    case "Normal":
                        NormalN--;
                        break;
                    case "Double Bed":
                        DoubleBedN--;
                        break;
                    case "V.I.P.":
                        VipN--;
                        break;
                    default:
                        break;
                }
            }
        }
    } catch (IOException e) {}
    NormalRoom.setText(String.valueOf(NormalN) + " rooms");
    DoubleBedRoom.setText(String.valueOf(DoubleBedN) + " rooms");
    VipRoom.setText(String.valueOf(VipN) + " rooms");
    }

    @FXML
    void BOOKClicked(ActionEvent event) throws IOException {//OPEN NewGuest.fxml
        Parent NewGuestInterface = FXMLLoader.load(getClass().getResource("NewGuest.fxml"));
        Scene NewGuestScene = new Scene(NewGuestInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(NewGuestScene);
        window.show();
    }

    private boolean CancelBooking() {//HELP TO OPEN YouSure.fxml
        try (BufferedReader reader = new BufferedReader(new FileReader("HotelData.dat"))) {
            return reader.readLine() == null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    String binaryToString(String binary) {//BINARY READER
        StringBuilder result = new StringBuilder();
        for (String binaryChar : binary.split(" ")) {
            if (!binaryChar.isEmpty()) { 
                int charCode = Integer.parseInt(binaryChar, 2);
                result.append((char) charCode);
            }
        }
        return result.toString();
    }
}

