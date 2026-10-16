
package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import model.DepartmentRecommendation;
import model.DisasterAssessment;
import model.DisasterReport;
import network.DRSClient;

public class OfficerDashboardController {

    @FXML
    private TableView<DisasterReport> disasterTable;

    @FXML
    private TableColumn<DisasterReport, Number> idColumn;

    @FXML
    private TableColumn<DisasterReport, String> typeColumn;

    @FXML
    private TableColumn<DisasterReport, String> locationColumn;

    @FXML
    private TableColumn<DisasterReport, Number> severityColumn;

    @FXML
    private TableColumn<DisasterReport, String> priorityColumn;

    @FXML
    private TableColumn<DisasterReport, String> statusColumn;

    @FXML
    private TableColumn<DisasterReport, String> departmentColumn;

    @FXML
    private ComboBox<String> priorityCombo;

    @FXML
    private ComboBox<String> departmentCombo;

    @FXML
    private ComboBox<String> statusCombo;

    @FXML
    private Label messageLabel;

    private final ObservableList<DisasterReport> reports =
            FXCollections.observableArrayList();

    private boolean busy = false;

    @FXML
    public void initialize() {

        priorityCombo.getItems().addAll(
                "Low", "Medium", "High", "Critical"
        );

        departmentCombo.getItems().addAll(
                "Fire and Emergency Services",
                "Hospital",
                "Law Enforcement",
                "Electricity",
                "Transportation",
                "Waste Management",
                "Water Supply",
                "Schools"
        );

        statusCombo.getItems().addAll(
                "Reported",
                "Assigned",
                "In Progress",
                "Completed"
        );

        idColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getDisasterId()
                )
        );

        typeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getDisasterType()
                )
        );

        locationColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getLocation()
                )
        );

        severityColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getSeverity()
                )
        );

        priorityColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getPriority()
                )
        );

        statusColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getStatus()
                )
        );

        departmentColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getAssignedDepartment()
                )
        );

        disasterTable.setItems(reports);

        // Load reports from the server when dashboard opens
        loadReports();
    }

    // Execute network requests without freezing JavaFX
    private void sendRequest(
            String request,
            java.util.function.Consumer<String> onSuccess) {

        if (busy) {
            messageLabel.setText(
                    "Please wait for the current request."
            );
            return;
        }

        busy = true;
        messageLabel.setText("Contacting server...");

        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                DRSClient client =
                        new DRSClient("localhost", 5000);

                return client.sendRequest(request);
            }
        };

        task.setOnSucceeded(event -> {
            busy = false;
            onSuccess.accept(task.getValue());
        });

        task.setOnFailed(event -> {
            busy = false;
            messageLabel.setText(
                    "Server error: "
                    + task.getException().getMessage()
            );
        });

        Thread thread = new Thread(
                task, "dashboard-network-request"
        );
        thread.setDaemon(true);
        thread.start();
    }

    // Retrieve reports from the server
    private void loadReports() {

        sendRequest("GET_REPORTS", response -> {

            if (!response.startsWith("REPORTS|")) {
                messageLabel.setText(
                        "Cannot load reports: " + response
                );
                return;
            }

            try {
                ObservableList<DisasterReport> loaded =
                        FXCollections.observableArrayList();

                String content = response.substring(8);

                if (!content.isEmpty()) {

                    // Temporary format:
                    // ID,TYPE,LOCATION,SEVERITY,PEOPLE,
                    // IMPACT,PRIORITY,STATUS,DEPARTMENT
                    // Multiple records separated by semicolons.
                    for (String record : content.split(";")) {

                        String[] fields =
                                record.split(",", -1);

                        if (fields.length != 9) {
                            throw new IllegalArgumentException(
                                    "Invalid report data"
                            );
                        }

                        DisasterReport report =
                                new DisasterReport(
                                        Integer.parseInt(fields[0]),
                                        fields[1],
                                        fields[2],
                                        LocalDateTime.now(),
                                        "",
                                        Integer.parseInt(fields[3]),
                                        Integer.parseInt(fields[4]),
                                        fields[5]
                                );

                        report.setPriority(fields[6]);
                        report.setStatus(fields[7]);
                        report.setAssignedDepartment(fields[8]);

                        loaded.add(report);
                    }
                }

                reports.setAll(loaded);

                messageLabel.setText(
                        "Loaded " + reports.size()
                        + " disaster report(s)."
                );

            } catch (RuntimeException e) {
                messageLabel.setText(
                        "Invalid server report format: "
                        + e.getMessage()
                );
            }
        });
    }

    // Assess and prioritise selected disaster
    @FXML
    private void handleAssess() {

        DisasterReport report =
                disasterTable.getSelectionModel()
                        .getSelectedItem();

        if (report == null) {
            messageLabel.setText(
                    "Please select a disaster report."
            );
            return;
        }

        DisasterAssessment assessment =
                new DisasterAssessment(
                        report.getSeverity(),
                        report.getPeopleAffected(),
                        report.getInfrastructureImpact()
                );

        String priority = assessment.determinePriority();

        String request =
                "UPDATE_PRIORITY|"
                + report.getDisasterId()
                + "|" + priority;

        sendRequest(request, response -> {

            if (response.startsWith("SUCCESS|")) {

                report.setPriority(priority);
                priorityCombo.setValue(priority);
                disasterTable.refresh();

                messageLabel.setText(
                        "Assessment completed. Risk Score: "
                        + assessment.getRiskScore()
                        + ", Priority: " + priority
                );

            } else {
                messageLabel.setText(
                        "Priority update failed: " + response
                );
            }
        });
    }

    // Existing department recommendation feature
    @FXML
    private void handleRecommend() {

        DisasterReport report =
                disasterTable.getSelectionModel()
                        .getSelectedItem();

        if (report == null) {
            messageLabel.setText(
                    "Please select a disaster report."
            );
            return;
        }

        DepartmentRecommendation recommendation =
                new DepartmentRecommendation();

        List<String> departments =
                recommendation.recommendDepartments(
                        report.getDisasterType()
                );

        if (!departments.isEmpty()) {
            departmentCombo.setValue(departments.get(0));
        }

        messageLabel.setText(
                "Recommended departments: "
                + String.join(", ", departments)
        );
    }

    // Assign department through server
    @FXML
    private void handleAssign() {

        DisasterReport report =
                disasterTable.getSelectionModel()
                        .getSelectedItem();

        String department = departmentCombo.getValue();

        if (report == null) {
            messageLabel.setText(
                    "Please select a disaster report."
            );
            return;
        }

        if (department == null) {
            messageLabel.setText(
                    "Please select a department."
            );
            return;
        }

        String request =
                "ASSIGN_DEPARTMENT|"
                + report.getDisasterId()
                + "|" + department;

        sendRequest(request, response -> {

            if (response.startsWith("SUCCESS|")) {

                report.setAssignedDepartment(department);
                report.setStatus("Assigned");

                statusCombo.setValue("Assigned");
                disasterTable.refresh();

                messageLabel.setText(
                        department
                        + " assigned to Disaster ID "
                        + report.getDisasterId()
                );

            } else {
                messageLabel.setText(
                        "Assignment failed: " + response
                );
            }
        });
    }

    // Update response status through server
    @FXML
    private void handleUpdateStatus() {

        DisasterReport report =
                disasterTable.getSelectionModel()
                        .getSelectedItem();

        String status = statusCombo.getValue();

        if (report == null) {
            messageLabel.setText(
                    "Please select a disaster report."
            );
            return;
        }

        if (status == null) {
            messageLabel.setText(
                    "Please select a response status."
            );
            return;
        }

        String request =
                "UPDATE_STATUS|"
                + report.getDisasterId()
                + "|" + status;

        sendRequest(request, response -> {

            if (response.startsWith("SUCCESS|")) {

                report.setStatus(status);
                disasterTable.refresh();

                messageLabel.setText(
                        "Response status for Disaster ID "
                        + report.getDisasterId()
                        + " updated to " + status
                );

            } else {
                messageLabel.setText(
                        "Status update failed: " + response
                );
            }
        });
    }

    @FXML
    private void handleLogout() throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("/view/login.fxml")
        );

        Stage stage = (Stage) disasterTable
                .getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("Disaster Response System");
        stage.sizeToScene();
        stage.centerOnScreen();
    }
}
