package org.example.java;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends Application {

    private ObservableList<CaseRecord> caseHistory = FXCollections.observableArrayList();
    private ListView<CaseRecord> historyListView;

    // Crime types (20 most common crimes)
    private final String[] crimes = {
            "Theft/Burglary", "Assault", "Fraud", "Vandalism", "Drug Offense",
            "Domestic Violence", "Robbery", "Cybercrime", "Traffic Violation", "Harassment",
            "Identity Theft", "Shoplifting", "Embezzlement", "Stalking", "Arson",
            "Kidnapping", "Sexual Assault", "Money Laundering", "Extortion", "Public Disorder"
    };

    // Emergency numbers
    private final String[][] emergencyNumbers = {
            {"Police", "999"},
            {"Fire Service", "998"},
            {"Ambulance", "997"},
            {"Women & Child Helpline", "109"},
            {"Anti-Terrorism", "322"}
    };

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Crime Reporting Dashboard");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #f5f5f5;");

        // Header with toggle
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: linear-gradient(to right, #2c3e50, #3498db); -fx-padding: 10;");

        ToggleButton toggleButton = new ToggleButton("☰");
        toggleButton.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: transparent;");
        labelStyle(toggleButton);

        Label title = new Label("Crime Reporting Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);
        HBox.setMargin(title, new Insets(0, 0, 0, 10));

        header.getChildren().addAll(toggleButton, title);
        mainLayout.setTop(header);

        // Sidebar buttons
        Button reportBtn = createSidebarButton("Report Crime");
        Button historyBtn = createSidebarButton("Case History");
        Button emergencyBtn = createSidebarButton("Emergency");
        Button helpBtn = createSidebarButton("Help & FAQ");

        VBox sidebar = new VBox(10, reportBtn, historyBtn, emergencyBtn, helpBtn);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #34495e;");
        sidebar.setPrefWidth(200);

        // Main content tabs with references
        TabPane contentPane = new TabPane();
        contentPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab reportTab = new Tab("Report Crime", createReportCrimePane());
        Tab historyTab = new Tab("Case History", createCaseHistoryPane());
        Tab emergencyTab = new Tab("Emergency", createEmergencyPane());
        Tab helpTab = new Tab("Help & FAQ", createHelpPane());

        contentPane.getTabs().addAll(reportTab, historyTab, emergencyTab, helpTab);
        mainLayout.setCenter(contentPane);

        // Link sidebar buttons to tabs
        reportBtn.setOnAction(e -> contentPane.getSelectionModel().select(reportTab));
        historyBtn.setOnAction(e -> contentPane.getSelectionModel().select(historyTab));
        emergencyBtn.setOnAction(e -> contentPane.getSelectionModel().select(emergencyTab));
        helpBtn.setOnAction(e -> contentPane.getSelectionModel().select(helpTab));

        // Toggle sidebar
        toggleButton.setOnAction(e -> mainLayout.setLeft(toggleButton.isSelected() ? sidebar : null));

        // Create scene
        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add sample data
        addSampleData();
    }




    private ScrollPane createReportCrimePane() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: white;");

        // Title
        Label title = new Label("Select Crime Type");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.DARKBLUE);

        // Crime grid
        GridPane crimeGrid = new GridPane();
        crimeGrid.setHgap(15);
        crimeGrid.setVgap(15);
        crimeGrid.setPadding(new Insets(20));

        int row = 0, col = 0;
        for (String crime : crimes) {
            Button crimeBtn = new Button(crime);
            crimeBtn.setPrefSize(180, 60);
            crimeBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                    "-fx-font-size: 12px; -fx-background-radius: 10;");
            crimeBtn.setOnMouseEntered(e -> crimeBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; " +
                    "-fx-font-size: 12px; -fx-background-radius: 10;"));
            crimeBtn.setOnMouseExited(e -> crimeBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                    "-fx-font-size: 12px; -fx-background-radius: 10;"));

            crimeBtn.setOnAction(e -> Crime.reportCrime(crime));

            crimeGrid.add(crimeBtn, col, row);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        content.getChildren().addAll(title, crimeGrid);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");
        return scrollPane;
    }

    private ScrollPane createCaseHistoryPane() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: white;");

        // Title
        Label title = new Label("Case History");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.DARKBLUE);

        // Search bar
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        TextField searchField = new TextField();
        searchField.setPromptText("Search cases...");
        searchField.setPrefWidth(300);

        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        searchBox.getChildren().addAll(new Label("Search: "), searchField, searchBtn);

        // Case list
        historyListView = new ListView<>();
        historyListView.setPrefHeight(400);
        historyListView.setCellFactory(listView -> new CaseListCell());
        historyListView.setItems(caseHistory);

        content.getChildren().addAll(title, searchBox, historyListView);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");
        return scrollPane;
    }

    private ScrollPane createEmergencyPane() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: white;");

        // Title
        Label title = new Label("Emergency Services");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.DARKRED);

        // Emergency warning
        Label warning = new Label("⚠️ For immediate emergencies, call directly!");
        warning.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        warning.setTextFill(Color.RED);
        warning.setStyle("-fx-background-color: #ffebee; -fx-padding: 10; -fx-background-radius: 5;");

        // Emergency numbers grid
        GridPane emergencyGrid = new GridPane();
        emergencyGrid.setHgap(20);
        emergencyGrid.setVgap(20);
        emergencyGrid.setPadding(new Insets(20));

        int row = 0;
        for (String[] emergency : emergencyNumbers) {
            VBox emergencyBox = new VBox(10);
            emergencyBox.setAlignment(Pos.CENTER);
            emergencyBox.setStyle("-fx-background-color: #ffcdd2; -fx-padding: 20; -fx-background-radius: 10;");
            emergencyBox.setPrefSize(200, 120);

            Label serviceName = new Label(emergency[0]);
            serviceName.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            serviceName.setTextFill(Color.DARKRED);

            Label number = new Label(emergency[1]);
            number.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            number.setTextFill(Color.RED);

            Button callBtn = new Button("Call Now");
            callBtn.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold;");
            callBtn.setOnAction(e -> simulateCall(emergency[1]));

            emergencyBox.getChildren().addAll(serviceName, number, callBtn);
            emergencyGrid.add(emergencyBox, row % 3, row / 3);
            row++;
        }

        content.getChildren().addAll(title, warning, emergencyGrid);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");
        return scrollPane;
    }

    private ScrollPane createHelpPane() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: white;");

        // Title
        Label title = new Label("Help & Frequently Asked Questions");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.DARKBLUE);

        // FAQ Accordion
        Accordion faqAccordion = new Accordion();

        String[][] faqs = {
                {"How do I report a crime?", "Click on the 'Report Crime' tab and select the appropriate crime type from the list. Fill in the required details and submit your report."},
                {"How can I track my case?", "Go to the 'Case History' tab to view all your reported cases and their current status."},
                {"What information do I need to provide?", "You'll need to provide details about the incident, location, time, and any evidence or witnesses."},
                {"How long does it take to process a case?", "Processing time varies depending on the type of crime. You'll receive updates on your case status."},
                {"Can I report anonymously?", "Yes, you can choose to report anonymously, but this may limit follow-up investigations."},
                {"What if it's an emergency?", "For immediate emergencies, use the Emergency tab to call the appropriate service directly."},
                {"How do I contact support?", "You can contact support through the help section or call our support hotline at 111."},
                {"Can I edit my report after submission?", "Contact support to make changes to your submitted report."}
        };

        for (String[] faq : faqs) {
            TitledPane pane = new TitledPane();
            pane.setText(faq[0]);
            pane.setStyle("-fx-font-weight: bold;");

            Label answer = new Label(faq[1]);
            answer.setWrapText(true);
            answer.setStyle("-fx-padding: 10;");

            pane.setContent(answer);
            faqAccordion.getPanes().add(pane);
        }

        // Contact section
        VBox contactSection = new VBox(10);
        contactSection.setStyle("-fx-background-color: #e3f2fd; -fx-padding: 20; -fx-background-radius: 10;");

        Label contactTitle = new Label("Need More Help?");
        contactTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        contactTitle.setTextFill(Color.DARKBLUE);

        Label contactInfo = new Label("Support Hotline: 111\nEmail: support@crimereport.gov\nWebsite: www.crimereport.gov");
        contactInfo.setStyle("-fx-text-fill: #1976d2;");

        contactSection.getChildren().addAll(contactTitle, contactInfo);

        content.getChildren().addAll(title, faqAccordion, contactSection);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");
        return scrollPane;
    }

    private void reportCrime(String crimeType) {
        // Create crime reporting dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Report " + crimeType);
        dialog.setHeaderText("Please provide details about the incident");

        // Create form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField locationField = new TextField();
        locationField.setPromptText("Location of incident");

        DatePicker datePicker = new DatePicker();

        TextField timeField = new TextField();
        timeField.setPromptText("Time (e.g., 14:30)");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Detailed description of the incident");
        descriptionArea.setPrefRowCount(4);

        grid.add(new Label("Location:"), 0, 0);
        grid.add(locationField, 1, 0);
        grid.add(new Label("Date:"), 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(new Label("Time:"), 0, 2);
        grid.add(timeField, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descriptionArea, 1, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType submitButton = new ButtonType("Submit Report", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButton) {
                return "submitted";
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if ("submitted".equals(result)) {
                // Add to case history
                CaseRecord newCase = new CaseRecord(
                        "CR" + System.currentTimeMillis(),
                        crimeType,
                        locationField.getText(),
                        LocalDateTime.now(),
                        "Under Investigation"
                );
                caseHistory.add(0, newCase);

                // Show confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Report Submitted");
                alert.setHeaderText("Your report has been submitted successfully");
                alert.setContentText("Case ID: " + newCase.getCaseId() + "\nYou can track the progress in Case History.");
                alert.showAndWait();
            }
        });
    }

    private void simulateCall(String number) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Emergency Call");
        alert.setHeaderText("Calling " + number);
        alert.setContentText("In a real application, this would initiate a call to " + number);
        alert.showAndWait();
    }

    private void addSampleData() {
        caseHistory.addAll(
                new CaseRecord("CR001", "Theft/Burglary", "123 Main St", LocalDateTime.now().minusDays(5), "Closed"),
                new CaseRecord("CR002", "Assault", "456 Oak Ave", LocalDateTime.now().minusDays(3), "Under Investigation"),
                new CaseRecord("CR003", "Fraud", "789 Pine Rd", LocalDateTime.now().minusDays(1), "Evidence Collection")
        );
    }

    // Case Record class
    public static class CaseRecord {
        private String caseId;
        private String crimeType;
        private String location;
        private LocalDateTime reportDate;
        private String status;

        public CaseRecord(String caseId, String crimeType, String location, LocalDateTime reportDate, String status) {
            this.caseId = caseId;
            this.crimeType = crimeType;
            this.location = location;
            this.reportDate = reportDate;
            this.status = status;
        }

        // Getters
        public String getCaseId() { return caseId; }
        public String getCrimeType() { return crimeType; }
        public String getLocation() { return location; }
        public LocalDateTime getReportDate() { return reportDate; }
        public String getStatus() { return status; }

        @Override
        public String toString() {
            return caseId + " - " + crimeType + " (" + status + ")";
        }
    }

    // Custom cell for case history
    private class CaseListCell extends ListCell<CaseRecord> {
        @Override
        protected void updateItem(CaseRecord item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                VBox cellBox = new VBox(5);
                cellBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 10; -fx-background-radius: 5;");

                Label caseIdLabel = new Label("Case ID: " + item.getCaseId());
                caseIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

                Label crimeTypeLabel = new Label("Crime: " + item.getCrimeType());
                Label locationLabel = new Label("Location: " + item.getLocation());
                Label dateLabel = new Label("Date: " + item.getReportDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

                Label statusLabel = new Label("Status: " + item.getStatus());
                statusLabel.setStyle("-fx-text-fill: " + getStatusColor(item.getStatus()) + "; -fx-font-weight: bold;");

                cellBox.getChildren().addAll(caseIdLabel, crimeTypeLabel, locationLabel, dateLabel, statusLabel);
                setGraphic(cellBox);
            }
        }

        private String getStatusColor(String status) {
            switch (status.toLowerCase()) {
                case "closed": return "#4caf50";
                case "under investigation": return "#ff9800";
                case "evidence collection": return "#2196f3";
                default: return "#757575";
            }
        }
    }

    // Utility to style sidebar buttons
    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(160);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setTextFill(Color.WHITE);
        btn.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        btn.setStyle("-fx-background-color: transparent;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #3d5769;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent;"));
        return btn;
    }

    private void labelStyle(Control control) {
        control.setOnMouseEntered(e -> control.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;"));
        control.setOnMouseExited(e -> control.setStyle("-fx-background-color: transparent; -fx-text-fill: white;"));
    }

    private TabPane createMainContent() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab reportTab = new Tab("Report Crime", createReportCrimePane());
        Tab historyTab = new Tab("Case History", createCaseHistoryPane());
        Tab emergencyTab = new Tab("Emergency", createEmergencyPane());
        Tab helpTab = new Tab("Help & FAQ", createHelpPane());

        tabPane.getTabs().addAll(reportTab, historyTab, emergencyTab, helpTab);
        return tabPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}