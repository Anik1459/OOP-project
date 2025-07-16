package org.example.java;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Kidnapping extends Crime{

    private ComboBox<String> victimRelationshipCombo;
    private TextField victimNameField;
    private TextField victimAgeField;
    private ComboBox<String> victimGenderCombo;
    private TextArea victimDescriptionArea;
    private TextField victimContactField;
    private TextField victimAddressField;

    private ComboBox<String> kidnappingTypeCombo;
    private ComboBox<String> ransomDemandCombo;
    private TextField ransomAmountField;
    private ComboBox<String> contactMethodCombo;
    private TextField phoneNumberField;
    private ComboBox<String> suspectKnownCombo;
    private TextField suspectNameField;
    private TextArea suspectDescriptionArea;
    private TextField suspectAddressField;

    private ComboBox<String> vehicleInvolvedCombo;
    private TextField vehicleTypeField;
    private TextField vehicleColorField;
    private TextField vehicleNumberField;
    private ComboBox<String> witnessAvailableCombo;
    private TextArea witnessDetailsArea;
    private TextField witnessContactField;

    private ComboBox<String> policeInformedCombo;
    private TextField policeStationField;
    private TextField previousCaseField;
    private ComboBox<String> mediaContactCombo;
    private TextArea additionalInfoArea;

    public Kidnapping() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Report Kidnapping - Bangladesh Police");
        dialog.setHeaderText("Please provide complete details about the kidnapping incident");

        // Build form grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        int row = 0;

        // === VICTIM INFORMATION ===
        Label victimSectionLabel = new Label("VICTIM INFORMATION");
        victimSectionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(victimSectionLabel, 0, row++, 2, 1);

        // Victim relationship to complainant
        Label victimRelationshipLabel = new Label("Relationship to Victim:*");
        victimRelationshipCombo = new ComboBox<>();
        victimRelationshipCombo.getItems().addAll(
                "Self", "Son/Daughter", "Spouse", "Parent", "Sibling",
                "Relative", "Friend", "Colleague", "Other"
        );
        victimRelationshipCombo.setPromptText("Select relationship");
        grid.add(victimRelationshipLabel, 0, row);
        grid.add(victimRelationshipCombo, 1, row++);

        // Victim name
        Label victimNameLabel = new Label("Victim's Full Name:*");
        victimNameField = new TextField();
        victimNameField.setPromptText("Enter victim's full name");
        grid.add(victimNameLabel, 0, row);
        grid.add(victimNameField, 1, row++);

        // Victim age
        Label victimAgeLabel = new Label("Victim's Age:*");
        victimAgeField = new TextField();
        victimAgeField.setPromptText("Enter age");
        grid.add(victimAgeLabel, 0, row);
        grid.add(victimAgeField, 1, row++);

        // Victim gender
        Label victimGenderLabel = new Label("Victim's Gender:*");
        victimGenderCombo = new ComboBox<>();
        victimGenderCombo.getItems().addAll("Male", "Female", "Other");
        victimGenderCombo.setPromptText("Select gender");
        grid.add(victimGenderLabel, 0, row);
        grid.add(victimGenderCombo, 1, row++);

        // Victim description
        Label victimDescriptionLabel = new Label("Victim's Description:*");
        victimDescriptionArea = new TextArea();
        victimDescriptionArea.setPromptText("Height, weight, clothing, physical features etc.");
        victimDescriptionArea.setPrefRowCount(3);
        grid.add(victimDescriptionLabel, 0, row);
        grid.add(victimDescriptionArea, 1, row++);

        // Victim contact
        Label victimContactLabel = new Label("Victim's Contact Number:");
        victimContactField = new TextField();
        victimContactField.setPromptText("Mobile number if available");
        grid.add(victimContactLabel, 0, row);
        grid.add(victimContactField, 1, row++);

        // Victim address
        Label victimAddressLabel = new Label("Victim's Address:*");
        victimAddressField = new TextField();
        victimAddressField.setPromptText("Complete address");
        grid.add(victimAddressLabel, 0, row);
        grid.add(victimAddressField, 1, row++);

        // === KIDNAPPING DETAILS ===
        Label kidnappingSection = new Label("KIDNAPPING DETAILS");
        kidnappingSection.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(kidnappingSection, 0, row++, 2, 1);

        // Type of kidnapping
        Label kidnappingTypeLabel = new Label("Type of Kidnapping:*");
        kidnappingTypeCombo = new ComboBox<>();
        kidnappingTypeCombo.getItems().addAll(
                "Ransom Demand", "Human Trafficking", "Forced Marriage",
                "Child Abduction", "Revenge/Personal Dispute", "Unknown Motive"
        );
        kidnappingTypeCombo.setPromptText("Select type");
        grid.add(kidnappingTypeLabel, 0, row);
        grid.add(kidnappingTypeCombo, 1, row++);

        // Ransom demand
        Label ransomDemandLabel = new Label("Ransom Demanded:*");
        ransomDemandCombo = new ComboBox<>();
        ransomDemandCombo.getItems().addAll("Yes", "No", "Unknown");
        ransomDemandCombo.setPromptText("Select option");
        grid.add(ransomDemandLabel, 0, row);
        grid.add(ransomDemandCombo, 1, row++);

        // Ransom amount (dependent on ransom demand)
        Label ransomAmountLabel = new Label("Ransom Amount (BDT):");
        ransomAmountField = new TextField();
        ransomAmountField.setPromptText("Amount in Taka");
        ransomAmountField.setDisable(true);
        grid.add(ransomAmountLabel, 0, row);
        grid.add(ransomAmountField, 1, row++);

        // Contact method for ransom
        Label contactMethodLabel = new Label("Contact Method Used:");
        contactMethodCombo = new ComboBox<>();
        contactMethodCombo.getItems().addAll(
                "Phone Call", "SMS", "WhatsApp", "Email", "Letter", "Other"
        );
        contactMethodCombo.setPromptText("How were you contacted?");
        contactMethodCombo.setDisable(true);
        grid.add(contactMethodLabel, 0, row);
        grid.add(contactMethodCombo, 1, row++);

        // Phone number used for contact
        Label phoneNumberLabel = new Label("Phone Number Used:");
        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Number used by kidnappers");
        phoneNumberField.setDisable(true);
        grid.add(phoneNumberLabel, 0, row);
        grid.add(phoneNumberField, 1, row++);

        // === SUSPECT INFORMATION ===
        Label suspectSection = new Label("SUSPECT INFORMATION");
        suspectSection.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(suspectSection, 0, row++, 2, 1);

        // Suspect known
        Label suspectKnownLabel = new Label("Suspect Known to Victim:*");
        suspectKnownCombo = new ComboBox<>();
        suspectKnownCombo.getItems().addAll("Yes", "No", "Partially");
        suspectKnownCombo.setPromptText("Select option");
        grid.add(suspectKnownLabel, 0, row);
        grid.add(suspectKnownCombo, 1, row++);

        // Suspect name (dependent on suspect known)
        Label suspectNameLabel = new Label("Suspect Name:");
        suspectNameField = new TextField();
        suspectNameField.setPromptText("Name if known");
        suspectNameField.setDisable(true);
        grid.add(suspectNameLabel, 0, row);
        grid.add(suspectNameField, 1, row++);

        // Suspect description
        Label suspectDescriptionLabel = new Label("Suspect Description:");
        suspectDescriptionArea = new TextArea();
        suspectDescriptionArea.setPromptText("Physical description, clothing, distinctive features");
        suspectDescriptionArea.setPrefRowCount(3);
        grid.add(suspectDescriptionLabel, 0, row);
        grid.add(suspectDescriptionArea, 1, row++);

        // Suspect address
        Label suspectAddressLabel = new Label("Suspect Address:");
        suspectAddressField = new TextField();
        suspectAddressField.setPromptText("If known");
        suspectAddressField.setDisable(true);
        grid.add(suspectAddressLabel, 0, row);
        grid.add(suspectAddressField, 1, row++);

        // === VEHICLE INFORMATION ===
        Label vehicleSection = new Label("VEHICLE INFORMATION");
        vehicleSection.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(vehicleSection, 0, row++, 2, 1);

        // Vehicle involved
        Label vehicleInvolvedLabel = new Label("Vehicle Involved:*");
        vehicleInvolvedCombo = new ComboBox<>();
        vehicleInvolvedCombo.getItems().addAll("Yes", "No", "Unknown");
        vehicleInvolvedCombo.setPromptText("Select option");
        grid.add(vehicleInvolvedLabel, 0, row);
        grid.add(vehicleInvolvedCombo, 1, row++);

        // Vehicle type (dependent on vehicle involved)
        Label vehicleTypeLabel = new Label("Vehicle Type:");
        vehicleTypeField = new TextField();
        vehicleTypeField.setPromptText("Car, Motorcycle, Van, etc.");
        vehicleTypeField.setDisable(true);
        grid.add(vehicleTypeLabel, 0, row);
        grid.add(vehicleTypeField, 1, row++);

        // Vehicle color
        Label vehicleColorLabel = new Label("Vehicle Color:");
        vehicleColorField = new TextField();
        vehicleColorField.setPromptText("Color of vehicle");
        vehicleColorField.setDisable(true);
        grid.add(vehicleColorLabel, 0, row);
        grid.add(vehicleColorField, 1, row++);

        // Vehicle number
        Label vehicleNumberLabel = new Label("Vehicle Number:");
        vehicleNumberField = new TextField();
        vehicleNumberField.setPromptText("License plate number");
        vehicleNumberField.setDisable(true);
        grid.add(vehicleNumberLabel, 0, row);
        grid.add(vehicleNumberField, 1, row++);

        // === WITNESS INFORMATION ===
        Label witnessSection = new Label("WITNESS INFORMATION");
        witnessSection.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(witnessSection, 0, row++, 2, 1);

        // Witness available
        Label witnessAvailableLabel = new Label("Witnesses Available:*");
        witnessAvailableCombo = new ComboBox<>();
        witnessAvailableCombo.getItems().addAll("Yes", "No", "Unknown");
        witnessAvailableCombo.setPromptText("Select option");
        grid.add(witnessAvailableLabel, 0, row);
        grid.add(witnessAvailableCombo, 1, row++);

        // Witness details (dependent on witness available)
        Label witnessDetailsLabel = new Label("Witness Details:");
        witnessDetailsArea = new TextArea();
        witnessDetailsArea.setPromptText("Names and what they saw");
        witnessDetailsArea.setPrefRowCount(3);
        witnessDetailsArea.setDisable(true);
        grid.add(witnessDetailsLabel, 0, row);
        grid.add(witnessDetailsArea, 1, row++);

        // Witness contact
        Label witnessContactLabel = new Label("Witness Contact:");
        witnessContactField = new TextField();
        witnessContactField.setPromptText("Contact numbers");
        witnessContactField.setDisable(true);
        grid.add(witnessContactLabel, 0, row);
        grid.add(witnessContactField, 1, row++);

        // === ADDITIONAL INFORMATION ===
        Label additionalSection = new Label("ADDITIONAL INFORMATION");
        additionalSection.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(additionalSection, 0, row++, 2, 1);

        // Police informed
        Label policeInformedLabel = new Label("Other Police Stations Informed:*");
        policeInformedCombo = new ComboBox<>();
        policeInformedCombo.getItems().addAll("Yes", "No");
        policeInformedCombo.setPromptText("Select option");
        grid.add(policeInformedLabel, 0, row);
        grid.add(policeInformedCombo, 1, row++);

        // Police station name
        Label policeStationLabel = new Label("Police Station Name:");
        policeStationField = new TextField();
        policeStationField.setPromptText("Name of other police station");
        policeStationField.setDisable(true);
        grid.add(policeStationLabel, 0, row);
        grid.add(policeStationField, 1, row++);

        // Previous case
        Label previousCaseLabel = new Label("Previous Case Reference:");
        previousCaseField = new TextField();
        previousCaseField.setPromptText("If any previous case exists");
        grid.add(previousCaseLabel, 0, row);
        grid.add(previousCaseField, 1, row++);

        // Media contact
        Label mediaContactLabel = new Label("Media Contacted:*");
        mediaContactCombo = new ComboBox<>();
        mediaContactCombo.getItems().addAll("Yes", "No", "Planning to");
        mediaContactCombo.setPromptText("Select option");
        grid.add(mediaContactLabel, 0, row);
        grid.add(mediaContactCombo, 1, row++);

        // Additional information
        Label additionalInfoLabel = new Label("Additional Information:");
        additionalInfoArea = new TextArea();
        additionalInfoArea.setPromptText("Any other relevant information");
        additionalInfoArea.setPrefRowCount(4);
        grid.add(additionalInfoLabel, 0, row);
        grid.add(additionalInfoArea, 1, row++);

        // Set up dependent field listeners
        setupDependentFields();

        // Wrap in ScrollPane
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setPrefSize(600, 500);

        dialog.getDialogPane().setContent(scroll);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Handle result
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (validate()) {
                    // Record the case
                    UserDashboard.CaseRecord newCase = new UserDashboard.CaseRecord(
                            "KD" + System.currentTimeMillis(),
                            "Kidnapping",
                            "Victim: " + victimNameField.getText() + " | Type: " + kidnappingTypeCombo.getValue(),
                            LocalDateTime.now(),
                            "Under Investigation"
                    );

                    // Confirmation
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Kidnapping Report Submitted");
                    info.setHeaderText("Your kidnapping report has been submitted successfully");
                    info.setContentText("Case ID: " + newCase.getCaseId() +
                            "\nPriority: HIGH\nImmediate action will be taken.");
                    info.showAndWait();
                }
            }
        });
    }

    private void setupDependentFields() {
        // Ransom demand dependent fields
        ransomDemandCombo.setOnAction(e -> {
            boolean isYes = "Yes".equals(ransomDemandCombo.getValue());
            ransomAmountField.setDisable(!isYes);
            contactMethodCombo.setDisable(!isYes);
            phoneNumberField.setDisable(!isYes);

            if (!isYes) {
                ransomAmountField.clear();
                contactMethodCombo.setValue(null);
                phoneNumberField.clear();
            }
        });

        // Suspect known dependent fields
        suspectKnownCombo.setOnAction(e -> {
            boolean isKnown = "Yes".equals(suspectKnownCombo.getValue()) ||
                    "Partially".equals(suspectKnownCombo.getValue());
            suspectNameField.setDisable(!isKnown);
            suspectAddressField.setDisable(!isKnown);

            if (!isKnown) {
                suspectNameField.clear();
                suspectAddressField.clear();
            }
        });

        // Vehicle involved dependent fields
        vehicleInvolvedCombo.setOnAction(e -> {
            boolean isYes = "Yes".equals(vehicleInvolvedCombo.getValue());
            vehicleTypeField.setDisable(!isYes);
            vehicleColorField.setDisable(!isYes);
            vehicleNumberField.setDisable(!isYes);

            if (!isYes) {
                vehicleTypeField.clear();
                vehicleColorField.clear();
                vehicleNumberField.clear();
            }
        });

        // Witness available dependent fields
        witnessAvailableCombo.setOnAction(e -> {
            boolean isYes = "Yes".equals(witnessAvailableCombo.getValue());
            witnessDetailsArea.setDisable(!isYes);
            witnessContactField.setDisable(!isYes);

            if (!isYes) {
                witnessDetailsArea.clear();
                witnessContactField.clear();
            }
        });

        // Police informed dependent fields
        policeInformedCombo.setOnAction(e -> {
            boolean isYes = "Yes".equals(policeInformedCombo.getValue());
            policeStationField.setDisable(!isYes);

            if (!isYes) {
                policeStationField.clear();
            }
        });
    }

    private boolean validate() {
        List<String> errors = new ArrayList<>();

        // Validate required fields
        if (victimRelationshipCombo.getValue() == null) {
            errors.add("Please select relationship to victim");
        }
        if (victimNameField.getText().trim().isEmpty()) {
            errors.add("Please enter victim's name");
        }
        if (victimAgeField.getText().trim().isEmpty()) {
            errors.add("Please enter victim's age");
        } else {
            try {
                int age = Integer.parseInt(victimAgeField.getText().trim());
                if (age < 0 || age > 150) {
                    errors.add("Please enter a valid age");
                }
            } catch (NumberFormatException e) {
                errors.add("Please enter a valid age (numbers only)");
            }
        }
        if (victimGenderCombo.getValue() == null) {
            errors.add("Please select victim's gender");
        }
        if (victimDescriptionArea.getText().trim().isEmpty()) {
            errors.add("Please provide victim's description");
        }
        if (victimAddressField.getText().trim().isEmpty()) {
            errors.add("Please enter victim's address");
        }
        if (kidnappingTypeCombo.getValue() == null) {
            errors.add("Please select type of kidnapping");
        }
        if (ransomDemandCombo.getValue() == null) {
            errors.add("Please specify if ransom was demanded");
        }
        if (suspectKnownCombo.getValue() == null) {
            errors.add("Please specify if suspect is known");
        }
        if (vehicleInvolvedCombo.getValue() == null) {
            errors.add("Please specify if vehicle was involved");
        }
        if (witnessAvailableCombo.getValue() == null) {
            errors.add("Please specify if witnesses are available");
        }
        if (policeInformedCombo.getValue() == null) {
            errors.add("Please specify if other police stations were informed");
        }
        if (mediaContactCombo.getValue() == null) {
            errors.add("Please specify if media was contacted");
        }

        // Validate dependent fields
        if ("Yes".equals(ransomDemandCombo.getValue())) {
            if (ransomAmountField.getText().trim().isEmpty()) {
                errors.add("Please enter ransom amount");
            }
            if (contactMethodCombo.getValue() == null) {
                errors.add("Please select contact method used");
            }
        }

        if ("Yes".equals(suspectKnownCombo.getValue()) && suspectNameField.getText().trim().isEmpty()) {
            errors.add("Please enter suspect name since suspect is known");
        }

        if ("Yes".equals(vehicleInvolvedCombo.getValue()) && vehicleTypeField.getText().trim().isEmpty()) {
            errors.add("Please enter vehicle type since vehicle was involved");
        }

        if ("Yes".equals(witnessAvailableCombo.getValue()) && witnessDetailsArea.getText().trim().isEmpty()) {
            errors.add("Please provide witness details since witnesses are available");
        }

        if ("Yes".equals(policeInformedCombo.getValue()) && policeStationField.getText().trim().isEmpty()) {
            errors.add("Please enter police station name");
        }

        // Show errors if any
        if (!errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please correct the following errors:");
            alert.setContentText(String.join("\n• ", errors));
            alert.showAndWait();
            return false;
        }

        return true;
    }
}