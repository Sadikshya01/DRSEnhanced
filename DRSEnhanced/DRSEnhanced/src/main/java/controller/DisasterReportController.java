
package controller;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SafetyAdvice;
import network.DRSClient;

public class DisasterReportController {

    @FXML
    private ComboBox<String> disasterTypeCombo;

    @FXML
    private TextField locationField;

    @FXML
    private ComboBox<Integer> severityCombo;

    @FXML
    private TextField peopleAffectedField;

    @FXML
    private ComboBox<String> impactCombo;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Label resultLabel;

    @FXML
    private Label safetyAdviceLabel;

    // Prevent duplicate submissions while waiting for server
    private boolean submitting = false;

    @FXML
    public void initialize() {

        disasterTypeCombo.getItems().addAll(
                "Fire",
                "Earthquake",
                "Flood",
                "Hurricane"
        );

        severityCombo.getItems().addAll(
                1, 2, 3, 4, 5
        );

        impactCombo.getItems().addAll(
                "None",
                "Minor",
                "Moderate",
                "Severe"
        );
    }

    @FXML
    private void handleSubmit() {

        if (submitting) {
            return;
        }

        // Collect data from JavaFX controls
        String type = disasterTypeCombo.getValue();

        String location = locationField.getText().trim();

        Integer severity = severityCombo.getValue();

        String peopleText =
                peopleAffectedField.getText().trim();

        String impact = impactCombo.getValue();

        String description =
                descriptionArea.getText().trim();

        // Validate required fields
        if (type == null
                || location.isEmpty()
                || severity == null
                || peopleText.isEmpty()
                || impact == null
                || description.isEmpty()) {

            resultLabel.setText(
                    "Please complete all required fields."
            );
            return;
        }

        // Validate people affected
        int peopleAffected;

        try {

            peopleAffected =
                    Integer.parseInt(peopleText);

            if (peopleAffected < 0) {

                resultLabel.setText(
                        "People affected cannot be negative."
                );
                return;
            }

        } catch (NumberFormatException e) {

            resultLabel.setText(
                    "Enter a valid number for people affected."
            );
            return;
        }

        // Temporary pipe-separated communication protocol.
        // The server reads one line per request.
        // Reject characters that could break the message format.
        if (location.contains("|")
                || description.contains("|")
                || location.contains("\n")
                || location.contains("\r")
                || description.contains("\n")
                || description.contains("\r")) {

            resultLabel.setText(
                    "Location and description cannot contain "
                    + "| or line breaks."
            );
            return;
        }

        // Create request for the Java server
        String request =
                "REPORT_DISASTER|"
                + type + "|"
                + location + "|"
                + severity + "|"
                + peopleAffected + "|"
                + impact + "|"
                + description;

        submitting = true;

        resultLabel.setText(
                "Submitting disaster report to server..."
        );

        safetyAdviceLabel.setText("");

        // Run socket communication in a background task
        // so the JavaFX GUI does not freeze.
        Task<String> task = new Task<>() {

            @Override
            protected String call() throws Exception {

                DRSClient client =
                        new DRSClient("localhost", 5000);

                return client.sendRequest(request);
            }
        };

        // Handle successful network communication
        task.setOnSucceeded(event -> {

            submitting = false;

            String response = task.getValue();

            if (response != null
                    && response.startsWith("SUCCESS|")) {

                String disasterId =
                        response.substring(
                                "SUCCESS|".length()
                        );

                resultLabel.setText(
                        "Report received by server.\n"
                        + "Disaster ID: " + disasterId
                );

                // Display existing Safety Advice feature
                SafetyAdvice safetyAdvice =
                        new SafetyAdvice();

                safetyAdviceLabel.setText(
                        safetyAdvice.getAdvice(type)
                );

            } else {

                resultLabel.setText(
                        "Report submission failed: "
                        + response
                );
            }
        });

        // Handle server connection failures
        task.setOnFailed(event -> {

            submitting = false;

            String errorMessage =
                    task.getException() != null
                    ? task.getException().getMessage()
                    : "Unknown connection error";

            resultLabel.setText(
                    "Unable to connect to server.\n"
                    + errorMessage
            );
        });

        Thread thread = new Thread(
                task,
                "disaster-report-request"
        );

        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleClear() {

        if (submitting) {
            resultLabel.setText(
                    "Please wait for the current submission."
            );
            return;
        }

        disasterTypeCombo.setValue(null);
        locationField.clear();
        severityCombo.setValue(null);
        peopleAffectedField.clear();
        impactCombo.setValue(null);
        descriptionArea.clear();

        resultLabel.setText("");
        safetyAdviceLabel.setText("");
    }

    @FXML
    private void handleLogout() throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("/view/login.fxml")
        );

        Stage stage =
                (Stage) locationField.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("Disaster Response System");
        stage.sizeToScene();
        stage.centerOnScreen();
    }
}
