import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;

public class ReceiptPrinter {
    @FXML 
    private TextField AddressR;
    @FXML 
    private TextField EmailR;
    @FXML 
    private TextField NameR;
    @FXML 
    private TextField PhoneR;
    @FXML 
    private TextField RoomR;
    @FXML 
    private TextField Time1R;
    @FXML 
    private TextField Time2R;

    @FXML
    void AddClicked(ActionEvent event) {
        
    }

    @FXML
    void CancelClicked3(ActionEvent event) throws IOException {
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("WP.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
    }

    @FXML
    void PrintClicked(ActionEvent event) throws IOException {
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("Receipt.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
    }

    @FXML
    void FillClicked(ActionEvent event) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("HotelData.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 8) {
                    NameR.setText(binaryToString(fields[1])+" "+binaryToString(fields[0]));
                    PhoneR.setText(binaryToString(fields[9]));
                    EmailR.setText(binaryToString(fields[6]));
                    Time1R.setText(binaryToString(fields[10]));
                    RoomR.setText(binaryToString(fields[14]));
                    Time2R.setText(binaryToString(fields[11]));
                    AddressR.setText(binaryToString(fields[3]));
                    break; 
                } else {
                    System.err.println("Insufficient fields in the binary data");
                }
            }
        } catch (IOException e) {}
    }
    
    private String binaryToString(String binary) {
        StringBuilder text = new StringBuilder();
        String[] binaryArray = binary.split(" ");
        for (String binaryChar : binaryArray) {
            int ascii = Integer.parseInt(binaryChar, 2);
            text.append((char) ascii);
        }
        return text.toString();
    }
}
