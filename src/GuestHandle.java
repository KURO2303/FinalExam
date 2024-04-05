import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class GuestHandle {
    @FXML private TextField AddressAR;
    @FXML private TextField CityAR;
    @FXML private TextField EmailAR;
    @FXML private TextField GenderAR;
    @FXML private TextField IDAR;
    @FXML private TextField NameAR;
    @FXML private TextField PhoneAR;
    @FXML private TextField TimeAR;
    @FXML private TextField DesAR;
    private boolean Rejected = false;

    @FXML
    void EmployeeClicked(ActionEvent event) throws IOException {
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("EI.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
    }

    @FXML
    void GuestClicked(ActionEvent event) throws IOException {
        if (Rejected==true) {
            Parent sorryInterface = FXMLLoader.load(getClass().getResource("Sorry.fxml"));
            Scene sorryScene = new Scene(sorryInterface);
            Stage window = (Stage)((Button) event.getSource()).getScene().getWindow();
            window.setScene(sorryScene);
            window.show();
        } else {
            Parent guestInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
            Scene guestScene = new Scene(guestInterface);
            Stage window = (Stage)((Button) event.getSource()).getScene().getWindow();
            window.setScene(guestScene);
            window.show();
        }
    }

    @FXML
    void OK3Clicked(ActionEvent event) throws IOException {
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
    }

    @FXML
    void Clicked(ActionEvent event) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("HotelData.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 8) {
                    NameAR.setText("Name: "+binaryToString(fields[1])+" "+binaryToString(fields[0]));
                    PhoneAR.setText("Phone: "+binaryToString(fields[9]));
                    IDAR.setText("ID: "+binaryToString(fields[4]));
                    GenderAR.setText("Gender: "+binaryToString(fields[2]));
                    EmailAR.setText("Email: "+binaryToString(fields[6]));
                    CityAR.setText("City: "+binaryToString(fields[5]));
                    AddressAR.setText("Address: "+binaryToString(fields[3]));
                    TimeAR.setText("Stay duration: "+binaryToString(fields[15])+" nights from "+binaryToString(fields[10])+" to "+binaryToString(fields[11]));
                    DesAR.setText("Description: "+binaryToString(fields[7])+" adults and "+binaryToString(fields[8])+" childs stay in "+binaryToString(fields[14])+" room.");
                    break; 
                } else {
                    System.err.println("Insufficient fields in the binary data");
                }
            }
        } catch (IOException e) {}
    }

    @FXML
    void X3Clicked(ActionEvent event) throws IOException {
        Parent mainInterface = FXMLLoader.load(getClass().getResource("MI.fxml"));
        Scene mainScene = new Scene(mainInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(mainScene);
        window.show();
    }

    
    @FXML
    void AddGuestClicked(ActionEvent event) {

    }

    
    @FXML
    void RejectClicked(ActionEvent event) throws IOException {//REJECT GUEST BOOKING
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("HotelData.dat"))) {
            String newData = "  ";
            writer.write(newData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Rejected = true;
        Parent sorryInterface = FXMLLoader.load(getClass().getResource("SorryG.fxml"));
        Scene sorryScene = new Scene(sorryInterface);
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow();
        window.setScene(sorryScene);
        window.show();
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