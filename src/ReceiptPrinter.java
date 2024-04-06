import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ReceiptPrinter {
    @FXML private TextField AddressR;
    @FXML private TextField EmailR;
    @FXML private TextField NameR;
    @FXML private TextField PhoneR;
    @FXML private TextField RoomR;
    @FXML private TextField Time1R;
    @FXML private TextField Time2R;
    @FXML private TextField DateR;
    @FXML private TextField MoneyR1;
    @FXML private Label MoneyR2;
    @FXML private Label MoneyR3;
    @FXML private TextField NR;
    @FXML private TextField DescriptionR;
    @FXML private TextField NumberR;
    @FXML private TextField PriceR;
    @FXML private TextField TotalR;
    @FXML private Label SubTotal1;
    @FXML private Label SubTotal2;
    @FXML private Label TaxR;

    @FXML
    void AddClicked(ActionEvent event) throws IOException {//OPEN AR.fxml 
        Parent AddInterface = FXMLLoader.load(getClass().getResource("AR.fxml"));
        Scene AddScene = new Scene(AddInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(AddScene);
        window.show();
    }

    @FXML
    void CancelClicked2(ActionEvent event) throws IOException{//RETURN TO MI.fxml FROM Receipt.fxml
        Parent mainInterface = FXMLLoader.load(getClass().getResource("MI.fxml"));
        Scene mainScene = new Scene(mainInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(mainScene);
        window.show();
    }

    @FXML
    void CancelClicked3(ActionEvent event) throws IOException {//RETURN TO WP.fxml FROM MI.fxml
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("WP.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
    }

    @FXML
    void PrintClicked(ActionEvent event) throws IOException {//TO OPEN RECEIPT.FXML
        if(NoGuest()){
            Parent root = FXMLLoader.load(getClass().getResource("NoInfo.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }else{
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("Receipt.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
        }
    }

    @FXML
    void FillClicked(ActionEvent event) throws IOException {//FILL GUEST INFORMATION USING DATA IN GuestData.dat
        try (BufferedReader reader = new BufferedReader(new FileReader("GuestData.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 8) {
                    NameR.setText("Name: "+binaryToString(fields[1])+" "+binaryToString(fields[0]));
                    PhoneR.setText("Phone: "+binaryToString(fields[9]));
                    EmailR.setText("Email: "+binaryToString(fields[6]));
                    RoomR.setText("Room: "+binaryToString(fields[14]));
                    AddressR.setText("Address: "+binaryToString(fields[3]));
                    DescriptionR.setText(binaryToString(fields[14])+" room stay in for "+binaryToString(fields[15])+" nights.");
                    NumberR.setText("1");
                    PriceR.setText("$"+binaryToString(fields[17]));
                    MoneyR1.setText("$"+binaryToString(fields[19]));
                    TotalR.setText("$"+binaryToString(fields[18]));
                    SubTotal1.setText("$"+binaryToString(fields[18]));
                    SubTotal2.setText("$"+binaryToString(fields[18]));
                    MoneyR2.setText("$"+binaryToString(fields[19]));
                    MoneyR3.setText("$"+binaryToString(fields[19]));
                    TaxR.setText("$"+binaryToString(fields[16]));
                    break; 
                } else {
                    System.err.println("Insufficient guest information, please check if guest is added or not");
                }
            }
        } catch (IOException e) {}

        Timeline timeline = new Timeline(//UPDATE TIME
                new KeyFrame(Duration.seconds(1), e -> {
                    LocalDate currentDate = LocalDate.now();
                    String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    DateR.setText(formattedDate);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    void FinalClicked(ActionEvent event) throws IOException {//RETURN TO MI.fxml, DELETE DATA IN HotelData.dat AND DONE 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("HotelData.dat"))) {
            String newData = "New Guest Added";
            writer.write(newData);
        } catch (IOException e) {}
        Parent mainInterface = FXMLLoader.load(getClass().getResource("Added.fxml"));
        Scene mainScene = new Scene(mainInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(mainScene);
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

    private boolean NoGuest(){
        try (BufferedReader reader = new BufferedReader(new FileReader("GuestData.dat"))) {
            return reader.readLine() == null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
