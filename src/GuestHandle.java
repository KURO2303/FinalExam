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

public class GuestHandle {//OPERATE WP.fxml AND ADD, REJECT FUNCTIONS IN MI.fxml
    @FXML private TextField AddressAR;
    @FXML private TextField CityAR;
    @FXML private TextField EmailAR;
    @FXML private TextField GenderAR;
    @FXML private TextField IDAR;
    @FXML private TextField NameAR;
    @FXML private TextField PhoneAR;
    @FXML private TextField TimeAR;
    @FXML private TextField DesAR;

    @FXML
    void EmployeeClicked(ActionEvent event) throws IOException {//OPEN EI.fxml
        Parent employeeInterface = FXMLLoader.load(getClass().getResource("EI.fxml"));
        Scene employeeScene = new Scene(employeeInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(employeeScene);
        window.show();
    }

    @FXML
    void GuestClicked(ActionEvent event) throws IOException {//CHECK TO OPEN Sorry.fxml OR GI.fxml
        if(Rejected()){
            Parent root = FXMLLoader.load(getClass().getResource("Sorry.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Parent GuestInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
            Scene GuestScene = new Scene(GuestInterface); 
            Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
            window.setScene(GuestScene);
            window.show();
        } else{
            Parent GuestInterface = FXMLLoader.load(getClass().getResource("GI.fxml"));
            Scene GuestScene = new Scene(GuestInterface); 
            Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
            window.setScene(GuestScene);
            window.show();
        }
    }

    @FXML
    void OK3Clicked(ActionEvent event) throws IOException {//TO CLOSE Sorry.fxml
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void Clicked(ActionEvent event) throws IOException {//FILL GUEST INFO TO ADD OR REJECT THEIR BOOKING
        if(MissInfo()){
            Parent root = FXMLLoader.load(getClass().getResource("NoInfo.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }else{
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
}

    @FXML
    void X3Clicked(ActionEvent event) throws IOException {//RETURN TO MI.fxml FROM AR.fxml
        Parent mainInterface = FXMLLoader.load(getClass().getResource("MI.fxml"));
        Scene mainScene = new Scene(mainInterface); 
        Stage window = (Stage)((Button) event.getSource()).getScene().getWindow(); 
        window.setScene(mainScene);
        window.show();
    }

    @FXML
    void AddGuestClicked(ActionEvent event) throws IOException {//ADD GUEST INORMATION FROM HotelData.dat TO GuestData.dat
        if(MissInfo()){
        Parent root = FXMLLoader.load(getClass().getResource("NoInfo.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }else{
        try (BufferedReader reader = new BufferedReader(new FileReader("HotelData.dat"));
         BufferedWriter writer = new BufferedWriter(new FileWriter("GuestData.dat"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
        }
    } catch (IOException e) {}
    }
    }

    @FXML
    void RejectClicked(ActionEvent event) throws IOException {//REJECT GUEST BOOKING AND DELETE THAT GUEST INFORMATION IN HotelData.dat
        if(NoGuest()||MissInfo()){
            Parent root = FXMLLoader.load(getClass().getResource("NoInfo.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }else{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("HotelData.dat"))) {
            String newData = "cleared";
            writer.write(newData);
        } catch (IOException e) {}
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

    private boolean Rejected() {//HELP TO OPEN Sorry.fxml
        try (BufferedReader reader = new BufferedReader(new FileReader("HotelData.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("cleared")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean NoGuest(){//CHECK IF GUEST ADDED OR NOT
        try (BufferedReader reader = new BufferedReader(new FileReader("HotelData.dat"))) {
            return reader.readLine() == null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean MissInfo() {//CHECK IF THE GUEST INFORMATION ADDED OR NOT
        return NameAR.getText().isEmpty() &&
        AddressAR.getText().isEmpty() &&
        CityAR.getText().isEmpty() &&
        GenderAR.getText().isEmpty() &&
        IDAR.getText().isEmpty() &&
        EmailAR.getText().isEmpty() &&
        PhoneAR.getText().isEmpty() &&
        TimeAR.getText().isEmpty() &&
        DesAR.getText().isEmpty();     
    }
}