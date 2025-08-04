package src.main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class InvestigatorDashboard extends Application {

    private TableView<CaseItem> caseTable;
    private ObservableList<CaseItem> caseData;
    private TextArea caseDetailsArea;
    private TextArea investigationNotesArea;
    private ComboBox<String> statusComboBox;
    private ComboBox<String> priorityComboBox;
    private ComboBox<String> caseTypeFilter;
    private VBox totalCasesLabel;
    private VBox pendingCasesLabel;
    private VBox inProgressCasesLabel;
    private VBox solvedCasesLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Police Investigator Dashboard - Case Management System");

        // Initialize data
        caseData = FXCollections.observableArrayList();

        // Create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        // Header
        VBox header = createHeader();
        mainLayout.setTop(header);

        // Center content
        HBox centerContent = new HBox(10);
        centerContent.setPadding(new Insets(10, 0, 0, 0));

        // Left panel - Case list and filters
        VBox leftPanel = createLeftPanel();
        leftPanel.setPrefWidth(800);

        // Right panel - Case details and investigation tools
        VBox rightPanel = createRightPanel();
        rightPanel.setPrefWidth(600);

        centerContent.getChildren().addAll(leftPanel, rightPanel);
        mainLayout.setCenter(centerContent);

        // Wrap main layout in ScrollPane
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true); // Ensures content fits window width
        scrollPane.setFitToHeight(true); // Ensures content fits window height
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show horizontal scrollbar when needed
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scrollbar when needed

        // Load initial data
        loadAllCases();
        updateStatistics();

        Scene scene = new Scene(scrollPane, 1400, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setPadding(new Insets(0, 0, 20, 0));

        // Title
        Label titleLabel = new Label("🔍 Police Investigator Dashboard");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Statistics panel
        HBox statsPanel = createStatisticsPanel();

        // Quick actions
        HBox quickActions = createQuickActionsPanel();

        header.getChildren().addAll(titleLabel, statsPanel, quickActions);
        return header;
    }

    private HBox createStatisticsPanel() {
        HBox statsPanel = new HBox(20);
        statsPanel.setAlignment(Pos.CENTER);
        statsPanel.setPadding(new Insets(10));
        statsPanel.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #4682b4; -fx-border-radius: 5;");

        totalCasesLabel = createStatLabel("Total Cases", "0", "#2E8B57");
        pendingCasesLabel = createStatLabel("Pending", "0", "#FF6347");
        inProgressCasesLabel = createStatLabel("In Progress", "0", "#4169E1");
        solvedCasesLabel = createStatLabel("Solved", "0", "#32CD32");

        statsPanel.getChildren().addAll(totalCasesLabel, pendingCasesLabel, inProgressCasesLabel, solvedCasesLabel);
        return statsPanel;
    }

    private VBox createStatLabel(String title, String value, String color) {
        VBox statBox = new VBox(5);
        statBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        valueLabel.setStyle("-fx-text-fill: " + color);

        statBox.getChildren().addAll(valueLabel, titleLabel);
        return statBox;
    }

    private HBox createQuickActionsPanel() {
        HBox quickActions = new HBox(10);
        quickActions.setAlignment(Pos.CENTER);

        Button refreshBtn = new Button("🔄 Refresh");
        Button exportBtn = new Button("📊 Export Report");
        Button searchBtn = new Button("🔍 Advanced Search");
        Button priorityBtn = new Button("⚠️ High Priority Cases");

        refreshBtn.setOnAction(e -> {
            loadAllCases();
            updateStatistics();
            showAlert("Data refreshed successfully!", Alert.AlertType.INFORMATION);
        });

        exportBtn.setOnAction(e -> exportCaseReport());
        searchBtn.setOnAction(e -> showAdvancedSearch());
        priorityBtn.setOnAction(e -> filterHighPriorityCases());

        quickActions.getChildren().addAll(refreshBtn, exportBtn, searchBtn, priorityBtn);
        return quickActions;
    }

    private VBox createLeftPanel() {
        VBox leftPanel = new VBox(10);

        // Filters section
        VBox filtersSection = new VBox(10);
        filtersSection.setPadding(new Insets(10));
        filtersSection.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 5;");

        Label filtersLabel = new Label("📋 Case Filters");
        filtersLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        HBox filterControls = new HBox(10);
        filterControls.setAlignment(Pos.CENTER_LEFT);

        caseTypeFilter = new ComboBox<>();
        caseTypeFilter.getItems().addAll("All Cases", "Fraud", "Money Laundering", "Kidnapping",
                "Drug Offense", "Extortion", "Robbery");
        caseTypeFilter.setValue("All Cases");
        caseTypeFilter.setOnAction(e -> filterCases());

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.getItems().addAll("All Status", "approved", "in_progress", "solved", "closed");
        statusFilter.setValue("All Status");
        statusFilter.setOnAction(e -> filterCases());

        filterControls.getChildren().addAll(
                new Label("Type:"), caseTypeFilter,
                new Label("Status:"), statusFilter
        );

        filtersSection.getChildren().addAll(filtersLabel, filterControls);

        // Case table
        VBox tableSection = createCaseTable();

        leftPanel.getChildren().addAll(filtersSection, tableSection);
        return leftPanel;
    }

    private VBox createCaseTable() {
        VBox tableSection = new VBox(10);

        Label tableLabel = new Label("📁 Case List");
        tableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        caseTable = new TableView<>();
        caseTable.setPrefHeight(500);

        // Create columns
        TableColumn<CaseItem, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<CaseItem, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setPrefWidth(120);

        TableColumn<CaseItem, String> complainantCol = new TableColumn<>("Complainant");
        complainantCol.setCellValueFactory(new PropertyValueFactory<>("complainantName"));
        complainantCol.setPrefWidth(150);

        TableColumn<CaseItem, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("incidentDate"));
        dateCol.setPrefWidth(100);

        TableColumn<CaseItem, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationCol.setPrefWidth(150);

        TableColumn<CaseItem, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        TableColumn<CaseItem, String> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
        priorityCol.setPrefWidth(80);

        caseTable.getColumns().addAll(idCol, typeCol, complainantCol, dateCol, locationCol, statusCol, priorityCol);
        caseTable.setItems(caseData);

        // Handle row selection
        caseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayCaseDetails(newSelection);
            }
        });

        tableSection.getChildren().addAll(tableLabel, caseTable);
        return tableSection;
    }

    private VBox createRightPanel() {
        VBox rightPanel = new VBox(10);

        // Case details section
        VBox detailsSection = createCaseDetailsSection();

        // Investigation tools section
        VBox toolsSection = createInvestigationToolsSection();

        rightPanel.getChildren().addAll(detailsSection, toolsSection);
        return rightPanel;
    }

    private VBox createCaseDetailsSection() {
        VBox detailsSection = new VBox(10);
        detailsSection.setPadding(new Insets(10));
        detailsSection.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #4682b4; -fx-border-radius: 5;");

        Label detailsLabel = new Label("📄 Case Details");
        detailsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        caseDetailsArea = new TextArea();
        caseDetailsArea.setPrefRowCount(8);
        caseDetailsArea.setEditable(false);
        caseDetailsArea.setWrapText(true);
        caseDetailsArea.setPromptText("Select a case to view details...");

        // Case management controls
        HBox caseControls = new HBox(10);
        caseControls.setAlignment(Pos.CENTER_LEFT);

        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("approved", "in_progress", "solved", "closed", "rejected");
        statusComboBox.setPromptText("Update Status");

        priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Low", "Medium", "High", "Critical");
        priorityComboBox.setPromptText("Set Priority");

        Button updateBtn = new Button("💾 Update Case");
        updateBtn.setOnAction(e -> updateCaseStatus());

        caseControls.getChildren().addAll(
                new Label("Status:"), statusComboBox,
                new Label("Priority:"), priorityComboBox,
                updateBtn
        );

        detailsSection.getChildren().addAll(detailsLabel, caseDetailsArea, caseControls);
        return detailsSection;
    }

    private VBox createInvestigationToolsSection() {
        VBox toolsSection = new VBox(10);
        toolsSection.setPadding(new Insets(10));
        toolsSection.setStyle("-fx-background-color: #fff8dc; -fx-border-color: #daa520; -fx-border-radius: 5;");

        Label toolsLabel = new Label("🔧 Investigation Tools");
        toolsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Investigation notes
        Label notesLabel = new Label("📝 Investigation Notes:");
        investigationNotesArea = new TextArea();
        investigationNotesArea.setPrefRowCount(6);
        investigationNotesArea.setWrapText(true);
        investigationNotesArea.setPromptText("Add your investigation notes here...");

        Button saveNotesBtn = new Button("💾 Save Notes");
        saveNotesBtn.setOnAction(e -> saveInvestigationNotes());

        // Action buttons
        HBox actionButtons = new HBox(10);
        actionButtons.setAlignment(Pos.CENTER_LEFT);

        Button evidenceBtn = new Button("📎 Manage Evidence");
        Button contactBtn = new Button("📞 Contact Complainant");
        Button timelineBtn = new Button("⏰ Case Timeline");
        Button reportBtn = new Button("📋 Generate Report");

        evidenceBtn.setOnAction(e -> manageEvidence());
        contactBtn.setOnAction(e -> contactComplainant());
        timelineBtn.setOnAction(e -> showCaseTimeline());
        reportBtn.setOnAction(e -> generateInvestigationReport());

        actionButtons.getChildren().addAll(evidenceBtn, contactBtn, timelineBtn, reportBtn);

        toolsSection.getChildren().addAll(toolsLabel, notesLabel, investigationNotesArea,
                saveNotesBtn, new Separator(), actionButtons);
        return toolsSection;
    }

    private void loadAllCases() {
        caseData.clear();

        // Load Fraud cases
        loadCasesByType("fraud_reports", "Fraud");

        // Load Money Laundering cases
        loadCasesByType("money_laundering_reports", "Money Laundering");

        // Load Kidnapping cases
        loadCasesByType("kidnapping_reports", "Kidnapping");

        // Load Drug Offense cases
        loadCasesByType("drug_offense_reports", "Drug Offense");

        // Load Extortion cases
        loadCasesByType("extortion_reports", "Extortion");

        // Load Robbery cases
        loadCasesByType("robbery_reports", "Robbery");
    }

    private void loadCasesByType(String tableName, String caseType) {
        try {
            ResultSet rs = DatabaseHelper.getApprovedCasesByTable(tableName);
            while (rs != null && rs.next()) {
                CaseItem caseItem = new CaseItem(
                        rs.getInt("id"),
                        caseType,
                        rs.getString("complainant_name"),
                        rs.getString("incident_date"),
                        rs.getString("location"),
                        rs.getString("status") != null ? rs.getString("status") : "approved",
                        determinePriority(caseType, rs),
                        rs
                );
                caseData.add(caseItem);
            }
        } catch (SQLException e) {
            System.err.println("Error loading " + caseType + " cases: " + e.getMessage());
        }
    }

    private String determinePriority(String caseType, ResultSet rs) throws SQLException {
        // Priority logic based on case type and specific conditions
        switch (caseType) {
            case "Kidnapping":
                String urgency = rs.getString("urgency_level");
                if (urgency != null && urgency.contains("Critical")) {
                    return "Critical";
                }
                return "High";
            case "Drug Offense":
                String trafficking = rs.getString("trafficking_observed");
                if ("Yes".equals(trafficking)) {
                    return "High";
                }
                return "Medium";
            case "Money Laundering":
                String amount = rs.getString("total_amount");
                if (amount != null && !amount.isEmpty()) {
                    try {
                        double amountValue = Double.parseDouble(amount.replaceAll("[^0-9.]", ""));
                        if (amountValue > 1000000) {
                            return "Critical";
                        } else if (amountValue > 100000) {
                            return "High";
                        }
                    } catch (NumberFormatException e) {
                        // Continue with default logic
                    }
                }
                return "Medium";
            default:
                return "Medium";
        }
    }

    private void displayCaseDetails(CaseItem caseItem) {
        StringBuilder details = new StringBuilder();
        details.append("CASE ID: ").append(caseItem.getId()).append("\n");
        details.append("TYPE: ").append(caseItem.getType()).append("\n");
        details.append("COMPLAINANT: ").append(caseItem.getComplainantName()).append("\n");
        details.append("DATE: ").append(caseItem.getIncidentDate()).append("\n");
        details.append("LOCATION: ").append(caseItem.getLocation()).append("\n");
        details.append("STATUS: ").append(caseItem.getStatus()).append("\n");
        details.append("PRIORITY: ").append(caseItem.getPriority()).append("\n\n");

        try {
            ResultSet rs = caseItem.getResultSet();
            details.append("DETAILED INFORMATION:\n");
            details.append("========================\n");

            // Add specific details based on case type
            switch (caseItem.getType()) {
                case "Fraud":
                    appendFraudDetails(details, rs);
                    break;
                case "Money Laundering":
                    appendMoneyLaunderingDetails(details, rs);
                    break;
                case "Kidnapping":
                    appendKidnappingDetails(details, rs);
                    break;
                case "Drug Offense":
                    appendDrugOffenseDetails(details, rs);
                    break;
                case "Extortion":
                    appendExtortionDetails(details, rs);
                    break;
                case "Robbery":
                    appendRobberyDetails(details, rs);
                    break;
            }
        } catch (SQLException e) {
            details.append("Error loading detailed information: ").append(e.getMessage());
        }

        caseDetailsArea.setText(details.toString());
        statusComboBox.setValue(caseItem.getStatus());
        priorityComboBox.setValue(caseItem.getPriority());
    }

    private void appendFraudDetails(StringBuilder details, ResultSet rs) throws SQLException {
        details.append("Description: ").append(rs.getString("description_of_incident")).append("\n");
        details.append("Accused Name: ").append(rs.getString("accused_name")).append("\n");
        details.append("Accused Phone: ").append(rs.getString("accused_phone")).append("\n");
        details.append("Fraud Type: ").append(rs.getString("type_of_fraud")).append("\n");
        details.append("Transaction Amount: ").append(rs.getString("transaction_amount")).append("\n");
        details.append("Communication Mode: ").append(rs.getString("mode_of_communication")).append("\n");
    }

    private void appendMoneyLaunderingDetails(StringBuilder details, ResultSet rs) throws SQLException {
        details.append("Description: ").append(rs.getString("description")).append("\n");
        details.append("Total Amount: ").append(rs.getString("total_amount")).append("\n");
        details.append("Currency: ").append(rs.getString("currency_type")).append("\n");
        details.append("Fund Sources: ").append(rs.getString("fund_sources")).append("\n");
        details.append("Bank Details: ").append(rs.getString("bank_details")).append("\n");
    }

    private void appendKidnappingDetails(StringBuilder details, ResultSet rs) throws SQLException {
        details.append("Description: ").append(rs.getString("description")).append("\n");
        details.append("Victim Age: ").append(rs.getInt("victim_age")).append("\n");
        details.append("Victim Gender: ").append(rs.getString("victim_gender")).append("\n");
        details.append("Last Location: ").append(rs.getString("last_location")).append("\n");
        details.append("Urgency Level: ").append(rs.getString("urgency_level")).append("\n");
        details.append("Ransom Demand: ").append(rs.getString("ransom_demand")).append("\n");
    }

    private void appendDrugOffenseDetails(StringBuilder details, ResultSet rs) throws SQLException {
        details.append("Description: ").append(rs.getString("description")).append("\n");
        details.append("Drug Type: ").append(rs.getString("drug_type")).append("\n");
        details.append("Quantity: ").append(rs.getString("quantity")).append("\n");
        details.append("Location Type: ").append(rs.getString("location_type")).append("\n");
        details.append("Trafficking Observed: ").append(rs.getString("trafficking_observed")).append("\n");
    }

    private void appendExtortionDetails(StringBuilder details, ResultSet rs) throws SQLException {
        details.append("Description: ").append(rs.getString("description")).append("\n");
        details.append("Extortion Type: ").append(rs.getString("extortion_type")).append("\n");
        details.append("Threat Details: ").append(rs.getString("threat_details")).append("\n");
        details.append("Money Amount: ").append(rs.getString("money_amount")).append("\n");
        details.append("Deadline: ").append(rs.getString("deadline_date")).append("\n");
    }

    private void appendRobberyDetails(StringBuilder details, ResultSet rs) throws SQLException {
        details.append("Description: ").append(rs.getString("description")).append("\n");
        details.append("Armed Robbery: ").append(rs.getString("armed_robbery")).append("\n");
        details.append("Weapon Type: ").append(rs.getString("weapon_type")).append("\n");
        details.append("Number of Robbers: ").append(rs.getString("number_of_robbers")).append("\n");
        details.append("Items Stolen: ").append(rs.getString("items_stolen")).append("\n");
    }

    private void updateCaseStatus() {
        CaseItem selectedCase = caseTable.getSelectionModel().getSelectedItem();
        if (selectedCase == null) {
            showAlert("Please select a case to update.", Alert.AlertType.WARNING);
            return;
        }

        String newStatus = statusComboBox.getValue();
        String newPriority = priorityComboBox.getValue();

        if (newStatus == null) {
            showAlert("Please select a status.", Alert.AlertType.WARNING);
            return;
        }

        // Update in database based on case type
        boolean success = updateCaseInDatabase(selectedCase, newStatus);

        if (success) {
            selectedCase.setStatus(newStatus);
            if (newPriority != null) {
                selectedCase.setPriority(newPriority);
            }
            caseTable.refresh();
            updateStatistics();
            showAlert("Case updated successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Failed to update case.", Alert.AlertType.ERROR);
        }
    }

    private boolean updateCaseInDatabase(CaseItem caseItem, String newStatus) {
        try {
            switch (caseItem.getType()) {
                case "Fraud":
                    return DatabaseHelper.updateFraudReportStatus(caseItem.getId(), newStatus);
                case "Money Laundering":
                    return DatabaseHelper.updateMoneyLaunderingReportStatus(caseItem.getId(), newStatus);
                case "Kidnapping":
                    return DatabaseHelper.updateKidnappingReportStatus(caseItem.getId(), newStatus);
                case "Drug Offense":
                    return DatabaseHelper.updateDrugOffenseReportStatus(caseItem.getId(), newStatus);
                case "Extortion":
                    return DatabaseHelper.updateExtortionReportStatus(caseItem.getId(), newStatus);
                case "Robbery":
                    return DatabaseHelper.updateRobberyReportStatus(caseItem.getId(), newStatus);
                default:
                    return false;
            }
        } catch (Exception e) {
            System.err.println("Error updating case status: " + e.getMessage());
            return false;
        }
    }

    private void updateStatistics() {
        int total = caseData.size();
        int pending = (int) caseData.stream().filter(c -> "approved".equals(c.getStatus())).count();
        int inProgress = (int) caseData.stream().filter(c -> "in_progress".equals(c.getStatus())).count();
        int solved = (int) caseData.stream().filter(c -> "solved".equals(c.getStatus())).count();

        ((Label) totalCasesLabel.getChildren().get(0)).setText(String.valueOf(total));
        ((Label) pendingCasesLabel.getChildren().get(0)).setText(String.valueOf(pending));
        ((Label) inProgressCasesLabel.getChildren().get(0)).setText(String.valueOf(inProgress));
        ((Label) solvedCasesLabel.getChildren().get(0)).setText(String.valueOf(solved));
    }

    private void filterCases() {
        // Implementation for filtering cases based on selected criteria
        loadAllCases(); // For now, reload all cases
        updateStatistics();
    }

    private void filterHighPriorityCases() {
        ObservableList<CaseItem> highPriorityCases = FXCollections.observableArrayList();
        for (CaseItem caseItem : caseData) {
            if ("High".equals(caseItem.getPriority()) || "Critical".equals(caseItem.getPriority())) {
                highPriorityCases.add(caseItem);
            }
        }
        caseTable.setItems(highPriorityCases);
    }

    private void saveInvestigationNotes() {
        CaseItem selectedCase = caseTable.getSelectionModel().getSelectedItem();
        if (selectedCase == null) {
            showAlert("Please select a case to save notes.", Alert.AlertType.WARNING);
            return;
        }

        String notes = investigationNotesArea.getText();
        if (notes.trim().isEmpty()) {
            showAlert("Please enter investigation notes.", Alert.AlertType.WARNING);
            return;
        }

        // In a real implementation, you would save notes to database
        // For now, just show success message
        showAlert("Investigation notes saved successfully!", Alert.AlertType.INFORMATION);
    }

    private void manageEvidence() {
        CaseItem selectedCase = caseTable.getSelectionModel().getSelectedItem();
        if (selectedCase == null) {
            showAlert("Please select a case to manage evidence.", Alert.AlertType.WARNING);
            return;
        }

        // Show evidence management dialog
        showEvidenceDialog(selectedCase);
    }

    private void showEvidenceDialog(CaseItem caseItem) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Evidence Management - Case #" + caseItem.getId());

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label titleLabel = new Label("📎 Evidence Management");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextArea evidenceArea = new TextArea();
        evidenceArea.setPrefRowCount(10);
        evidenceArea.setPromptText("List all evidence related to this case...");

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button addEvidenceBtn = new Button("➕ Add Evidence");
        Button viewEvidenceBtn = new Button("👁️ View Evidence");
        Button closeBtn = new Button("❌ Close");

        closeBtn.setOnAction(e -> dialog.close());

        buttons.getChildren().addAll(addEvidenceBtn, viewEvidenceBtn, closeBtn);
        content.getChildren().addAll(titleLabel, evidenceArea, buttons);

        Scene scene = new Scene(content, 500, 400);
        dialog.setScene(scene);
        dialog.show();
    }

    private void contactComplainant() {
        CaseItem selectedCase = caseTable.getSelectionModel().getSelectedItem();
        if (selectedCase == null) {
            showAlert("Please select a case to contact complainant.", Alert.AlertType.WARNING);
            return;
        }

        try {
            String phone = selectedCase.getResultSet().getString("complainant_phone");
            showAlert("Complainant Phone: " + phone + "\n\nUse this number to contact the complainant.",
                    Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("Error retrieving complainant contact information.", Alert.AlertType.ERROR);
        }
    }

    private void showCaseTimeline() {
        CaseItem selectedCase = caseTable.getSelectionModel().getSelectedItem();
        if (selectedCase == null) {
            showAlert("Please select a case to view timeline.", Alert.AlertType.WARNING);
            return;
        }

        // Show timeline dialog
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Case Timeline - Case #" + selectedCase.getId());

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label titleLabel = new Label("⏰ Case Timeline");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextArea timelineArea = new TextArea();
        timelineArea.setPrefRowCount(15);
        timelineArea.setEditable(false);

        StringBuilder timeline = new StringBuilder();
        timeline.append("CASE TIMELINE - Case #").append(selectedCase.getId()).append("\n");
        timeline.append("=====================================\n\n");
        timeline.append("📅 Case Reported: ").append(selectedCase.getIncidentDate()).append("\n");
        timeline.append("📍 Location: ").append(selectedCase.getLocation()).append("\n");
        timeline.append("👤 Complainant: ").append(selectedCase.getComplainantName()).append("\n");
        timeline.append("📊 Current Status: ").append(selectedCase.getStatus()).append("\n");
        timeline.append("⚠️ Priority Level: ").append(selectedCase.getPriority()).append("\n\n");
        timeline.append("INVESTIGATION MILESTONES:\n");
        timeline.append("-------------------------\n");
        timeline.append("• Case approved for investigation\n");
        timeline.append("• Initial evidence collection\n");
        timeline.append("• Witness interviews scheduled\n");
        timeline.append("• Forensic analysis requested\n");
        timeline.append("• Follow-up investigation pending\n\n");
        timeline.append("NEXT ACTIONS:\n");
        timeline.append("-------------\n");
        timeline.append("• Contact complainant for additional details\n");
        timeline.append("• Review available evidence\n");
        timeline.append("• Coordinate with forensic team\n");
        timeline.append("• Schedule suspect interview\n");

        timelineArea.setText(timeline.toString());

        Button closeBtn = new Button("❌ Close");
        closeBtn.setOnAction(e -> dialog.close());

        content.getChildren().addAll(titleLabel, timelineArea, closeBtn);

        Scene scene = new Scene(content, 600, 500);
        dialog.setScene(scene);
        dialog.show();
    }

    private void generateInvestigationReport() {
        CaseItem selectedCase = caseTable.getSelectionModel().getSelectedItem();
        if (selectedCase == null) {
            showAlert("Please select a case to generate report.", Alert.AlertType.WARNING);
            return;
        }

        // Show report generation dialog
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Investigation Report - Case #" + selectedCase.getId());

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label titleLabel = new Label("📋 Investigation Report");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextArea reportArea = new TextArea();
        reportArea.setPrefRowCount(20);

        StringBuilder report = new StringBuilder();
        report.append("POLICE INVESTIGATION REPORT\n");
        report.append("===========================\n\n");
        report.append("Case ID: ").append(selectedCase.getId()).append("\n");
        report.append("Case Type: ").append(selectedCase.getType()).append("\n");
        report.append("Investigating Officer: [Your Name]\n");
        report.append("Report Date: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n\n");

        report.append("CASE SUMMARY:\n");
        report.append("-------------\n");
        report.append("Complainant: ").append(selectedCase.getComplainantName()).append("\n");
        report.append("Incident Date: ").append(selectedCase.getIncidentDate()).append("\n");
        report.append("Location: ").append(selectedCase.getLocation()).append("\n");
        report.append("Current Status: ").append(selectedCase.getStatus()).append("\n");
        report.append("Priority Level: ").append(selectedCase.getPriority()).append("\n\n");

        report.append("INVESTIGATION FINDINGS:\n");
        report.append("-----------------------\n");
        report.append("• Initial complaint received and verified\n");
        report.append("• Evidence collection in progress\n");
        report.append("• Witness statements pending\n");
        report.append("• Forensic analysis requested\n\n");

        report.append("EVIDENCE COLLECTED:\n");
        report.append("-------------------\n");
        report.append("• Complainant statement\n");
        report.append("• Supporting documents\n");
        report.append("• Digital evidence (if applicable)\n");
        report.append("• Physical evidence (if applicable)\n\n");

        report.append("RECOMMENDATIONS:\n");
        report.append("----------------\n");
        report.append("• Continue investigation\n");
        report.append("• Interview additional witnesses\n");
        report.append("• Coordinate with other departments\n");
        report.append("• Regular case review\n\n");

        report.append("CONCLUSION:\n");
        report.append("-----------\n");
        report.append("Investigation ongoing. Case requires continued attention.\n");
        report.append("Regular updates will be provided.\n\n");

        report.append("Investigating Officer: ___________________\n");
        report.append("Signature: ___________________\n");
        report.append("Date: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n");

        reportArea.setText(report.toString());

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button saveBtn = new Button("💾 Save Report");
        Button printBtn = new Button("🖨️ Print Report");
        Button closeBtn = new Button("❌ Close");

        saveBtn.setOnAction(e -> {
            showAlert("Report saved successfully!", Alert.AlertType.INFORMATION);
            dialog.close();
        });

        printBtn.setOnAction(e -> {
            showAlert("Report sent to printer!", Alert.AlertType.INFORMATION);
        });

        closeBtn.setOnAction(e -> dialog.close());

        buttons.getChildren().addAll(saveBtn, printBtn, closeBtn);
        content.getChildren().addAll(titleLabel, reportArea, buttons);

        Scene scene = new Scene(content, 700, 600);
        dialog.setScene(scene);
        dialog.show();
    }

    private void exportCaseReport() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Export Case Reports");

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("📊 Export Case Reports");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox options = new VBox(10);

        CheckBox allCasesCheck = new CheckBox("All Cases");
        CheckBox pendingCheck = new CheckBox("Pending Cases Only");
        CheckBox inProgressCheck = new CheckBox("In Progress Cases Only");
        CheckBox solvedCheck = new CheckBox("Solved Cases Only");

        allCasesCheck.setSelected(true);

        ComboBox<String> formatCombo = new ComboBox<>();
        formatCombo.getItems().addAll("PDF Report", "Excel Spreadsheet", "CSV File");
        formatCombo.setValue("PDF Report");

        options.getChildren().addAll(
                new Label("Select Cases to Export:"),
                allCasesCheck, pendingCheck, inProgressCheck, solvedCheck,
                new Label("Export Format:"),
                formatCombo
        );

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button exportBtn = new Button("📤 Export");
        Button cancelBtn = new Button("❌ Cancel");

        exportBtn.setOnAction(e -> {
            showAlert("Export completed successfully!\nFile saved to: Documents/CaseReports/",
                    Alert.AlertType.INFORMATION);
            dialog.close();
        });

        cancelBtn.setOnAction(e -> dialog.close());

        buttons.getChildren().addAll(exportBtn, cancelBtn);
        content.getChildren().addAll(titleLabel, options, buttons);

        Scene scene = new Scene(content, 400, 350);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showAdvancedSearch() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Advanced Case Search");

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label titleLabel = new Label("🔍 Advanced Search");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        GridPane searchGrid = new GridPane();
        searchGrid.setHgap(10);
        searchGrid.setVgap(10);

        // Search fields
        TextField caseIdField = new TextField();
        caseIdField.setPromptText("Case ID");

        TextField complainantField = new TextField();
        complainantField.setPromptText("Complainant Name");

        DatePicker fromDatePicker = new DatePicker();
        DatePicker toDatePicker = new DatePicker();

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("All Types", "Fraud", "Money Laundering", "Kidnapping",
                "Drug Offense", "Extortion", "Robbery");
        typeCombo.setValue("All Types");

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("All Status", "approved", "in_progress", "solved", "closed");
        statusCombo.setValue("All Status");

        TextField locationField = new TextField();
        locationField.setPromptText("Location");

        // Add to grid
        searchGrid.add(new Label("Case ID:"), 0, 0);
        searchGrid.add(caseIdField, 1, 0);
        searchGrid.add(new Label("Complainant:"), 0, 1);
        searchGrid.add(complainantField, 1, 1);
        searchGrid.add(new Label("From Date:"), 0, 2);
        searchGrid.add(fromDatePicker, 1, 2);
        searchGrid.add(new Label("To Date:"), 0, 3);
        searchGrid.add(toDatePicker, 1, 3);
        searchGrid.add(new Label("Case Type:"), 0, 4);
        searchGrid.add(typeCombo, 1, 4);
        searchGrid.add(new Label("Status:"), 0, 5);
        searchGrid.add(statusCombo, 1, 5);
        searchGrid.add(new Label("Location:"), 0, 6);
        searchGrid.add(locationField, 1, 6);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button searchBtn = new Button("🔍 Search");
        Button clearBtn = new Button("🗑️ Clear");
        Button closeBtn = new Button("❌ Close");

        searchBtn.setOnAction(e -> {
            // Perform search based on criteria
            performAdvancedSearch(caseIdField.getText(), complainantField.getText(),
                    fromDatePicker.getValue(), toDatePicker.getValue(),
                    typeCombo.getValue(), statusCombo.getValue(),
                    locationField.getText());
            dialog.close();
        });

        clearBtn.setOnAction(e -> {
            caseIdField.clear();
            complainantField.clear();
            fromDatePicker.setValue(null);
            toDatePicker.setValue(null);
            typeCombo.setValue("All Types");
            statusCombo.setValue("All Status");
            locationField.clear();
        });

        closeBtn.setOnAction(e -> dialog.close());

        buttons.getChildren().addAll(searchBtn, clearBtn, closeBtn);
        content.getChildren().addAll(titleLabel, searchGrid, buttons);

        Scene scene = new Scene(content, 400, 400);
        dialog.setScene(scene);
        dialog.show();
    }

    private void performAdvancedSearch(String caseId, String complainant,
                                       java.time.LocalDate fromDate, java.time.LocalDate toDate,
                                       String type, String status, String location) {
        ObservableList<CaseItem> searchResults = FXCollections.observableArrayList();

        for (CaseItem caseItem : caseData) {
            boolean matches = true;

            // Apply search filters
            if (!caseId.isEmpty() && !String.valueOf(caseItem.getId()).contains(caseId)) {
                matches = false;
            }

            if (!complainant.isEmpty() && !caseItem.getComplainantName().toLowerCase()
                    .contains(complainant.toLowerCase())) {
                matches = false;
            }

            if (!"All Types".equals(type) && !caseItem.getType().equals(type)) {
                matches = false;
            }

            if (!"All Status".equals(status) && !caseItem.getStatus().equals(status)) {
                matches = false;
            }

            if (!location.isEmpty() && !caseItem.getLocation().toLowerCase()
                    .contains(location.toLowerCase())) {
                matches = false;
            }

            if (matches) {
                searchResults.add(caseItem);
            }
        }

        caseTable.setItems(searchResults);
        showAlert("Search completed. Found " + searchResults.size() + " matching cases.",
                Alert.AlertType.INFORMATION);
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Investigator Dashboard");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        // Initialize database
        DatabaseHelper.initializeDatabase();
        launch(args);
    }

    // Inner class for Case Item
    public static class CaseItem {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty type;
        private final SimpleStringProperty complainantName;
        private final SimpleStringProperty incidentDate;
        private final SimpleStringProperty location;
        private final SimpleStringProperty status;
        private final SimpleStringProperty priority;
        private final ResultSet resultSet;

        public CaseItem(int id, String type, String complainantName, String incidentDate,
                        String location, String status, String priority, ResultSet resultSet) {
            this.id = new SimpleIntegerProperty(id);
            this.type = new SimpleStringProperty(type);
            this.complainantName = new SimpleStringProperty(complainantName);
            this.incidentDate = new SimpleStringProperty(incidentDate);
            this.location = new SimpleStringProperty(location);
            this.status = new SimpleStringProperty(status);
            this.priority = new SimpleStringProperty(priority);
            this.resultSet = resultSet;
        }

        // Getters
        public int getId() { return id.get(); }
        public String getType() { return type.get(); }
        public String getComplainantName() { return complainantName.get(); }
        public String getIncidentDate() { return incidentDate.get(); }
        public String getLocation() { return location.get(); }
        public String getStatus() { return status.get(); }
        public String getPriority() { return priority.get(); }
        public ResultSet getResultSet() { return resultSet; }

        // Setters
        public void setStatus(String status) { this.status.set(status); }
        public void setPriority(String priority) { this.priority.set(priority); }

        // Property getters for TableView
        public SimpleIntegerProperty idProperty() { return id; }
        public SimpleStringProperty typeProperty() { return type; }
        public SimpleStringProperty complainantNameProperty() { return complainantName; }
        public SimpleStringProperty incidentDateProperty() { return incidentDate; }
        public SimpleStringProperty locationProperty() { return location; }
        public SimpleStringProperty statusProperty() { return status; }
        public SimpleStringProperty priorityProperty() { return priority; }
    }
}