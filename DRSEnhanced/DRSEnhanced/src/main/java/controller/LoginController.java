package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {

        roleComboBox.getItems().addAll(
                "Public User",
                "Disaster Response Officer"
        );
    }

    @FXML
    private void handleLogin() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty()
                || password.isEmpty()
                || role == null) {

            messageLabel.setText(
                    "Please complete all fields."
            );
            return;
        }

        try {

            if ("Public User".equals(role)) {

                openScreen(
                        "/view/disasterReport.fxml",
                        "Report a Disaster"
                );

            } else if ("Disaster Response Officer".equals(role)) {

                openScreen(
                        "/view/officerDashboard.fxml",
                        "Disaster Response Officer Dashboard"
                );

            } else {

                messageLabel.setText(
                        "Administrator functions are not included "
                        + "in this initial prototype."
                );
            }

        } catch (IOException e) {

            messageLabel.setText(
                    "Unable to open the selected screen."
            );

            e.printStackTrace();
        }
    }

    private void openScreen(String fxml,
                            String title) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource(fxml)
        );

        Stage stage =
                (Stage) usernameField.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.sizeToScene();
        stage.centerOnScreen();
    }
}