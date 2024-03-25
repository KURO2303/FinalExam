import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class project {

    @FXML
    private TextField hiTest;

    @FXML
    void btnOKClicked(ActionEvent event) {
        Stage mainWindow =  (Stage) hiTest.getScene().getWindow();
        String title = hiTest.getText();
        mainWindow.setTitle(title);
    }

}
