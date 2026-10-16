package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DRSData {

    private static final ObservableList<DisasterReport> disasterReports
            = FXCollections.observableArrayList();

    private DRSData() {
    }

    public static ObservableList<DisasterReport> getDisasterReports() {
        return disasterReports;
    }

    public static void addDisasterReport(DisasterReport report) {
        disasterReports.add(report);
    }
}