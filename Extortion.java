package org.example.java;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDateTime;

public class Extortion extends Crime {

    // Form fields
    private ComboBox<String> extortionTypeCombo;
    private ComboBox<String> demandTypeCombo;
    private TextField demandAmountField;
    private ComboBox<String> threatTypeCombo;
    private TextArea threatDetailsArea;
    private ComboBox<String> perpetratorRelationCombo;
    private TextField perpetratorCountField;
    private TextArea perpetratorDescriptionArea;
    private CheckBox weaponUsedCheckBox;
    private ComboBox<String> weaponTypeCombo;
    private CheckBox moneyPaidCheckBox;
    private TextField paidAmountField;
    private ComboBox<String> paymentMethodCombo;
    private CheckBox businessTargetCheckBox;
    private ComboBox<String> businessTypeCombo;
    private TextField businessNameField;
    private CheckBox ongoingExtortionCheckBox;
    private ComboBox<String> frequencyCombo;
    private TextField durationField;
    private CheckBox evidenceAvailableCheckBox;
    private ComboBox<String> evidenceTypeCombo;
    private TextArea evidenceDetailsArea;
    private CheckBox witnessAvailableCheckBox;
    private TextArea witnessDetailsArea;
    private CheckBox policeContactedCheckBox;
    private TextField previousCaseNumberField;
    private ComboBox<String> communicationMethodCombo;
    private TextField contactDetailsField;
    private CheckBox familyThreatenedCheckBox;
    private TextArea familyThreatDetailsArea;
    private TextArea incidentDescriptionArea;
    private ComboBox<String> victimOccupationCombo;
    private TextField otherOccupationField;

    public Extortion() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Report Extortion");
        dialog.setHeaderText("Please provide details about the extortion incident");

        // Build form grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        int row = 0;

        // Type of Extortion
        Label extortionTypeLabel = new Label("Type of Extortion:");
        extortionTypeCombo = new ComboBox<>();
        extortionTypeCombo.getItems().addAll(
                "Money Demand",
                "Property Demand",
                "Business Extortion",
                "Protection Money",
                "Political Extortion",
                "Academic/Educational Extortion",
                "Other"
        );
        extortionTypeCombo.setPromptText("Select extortion type");
        grid.add(extortionTypeLabel, 0, row);
        grid.add(extortionTypeCombo, 1, row++);

        // Demand Type
        Label demandTypeLabel = new Label("What was Demanded:");
        demandTypeCombo = new ComboBox<>();
        demandTypeCombo.getItems().addAll(
                "Cash Money",
                "Bank Transfer",
                "Mobile Banking",
                "Property/Land",
                "Business Share",
                "Services",
                "Favor/Influence",
                "Other"
        );
        demandTypeCombo.setPromptText("Select demand type");
        grid.add(demandTypeLabel, 0, row);
        grid.add(demandTypeCombo, 1, row++);

        // Demand Amount
        Label demandAmountLabel = new Label("Demanded Amount (BDT):");
        demandAmountField = new TextField();
        demandAmountField.setPromptText("e.g., 100000");
        grid.add(demandAmountLabel, 0, row);
        grid.add(demandAmountField, 1, row++);

        // Type of Threat
        Label threatTypeLabel = new Label("Type of Threat:");
        threatTypeCombo = new ComboBox<>();
        threatTypeCombo.getItems().addAll(
                "Physical Violence",
                "Death Threat",
                "Property Damage",
                "Family Threat",
                "Business Harm",
                "Reputation Damage",
                "Legal Trouble",
                "Social Boycott",
                "Other"
        );
        threatTypeCombo.setPromptText("Select threat type");
        grid.add(threatTypeLabel, 0, row);
        grid.add(threatTypeCombo, 1, row++);

        // Threat Details
        Label threatDetailsLabel = new Label("Threat Details:");
        threatDetailsArea = new TextArea();
        threatDetailsArea.setPromptText("Describe the specific threats made");
        threatDetailsArea.setPrefRowCount(3);
        grid.add(threatDetailsLabel, 0, row);
        grid.add(threatDetailsArea, 1, row++);

        // Perpetrator Relation
        Label perpetratorRelationLabel = new Label("Perpetrator's Relation:");
        perpetratorRelationCombo = new ComboBox<>();
        perpetratorRelationCombo.getItems().addAll(
                "Complete Stranger",
                "Known Person",
                "Neighbor",
                "Business Associate",
                "Former Employee",
                "Political Rival",
                "Family Member",
                "Criminal Gang",
                "Other"
        );
        perpetratorRelationCombo.setPromptText("Select relation(Optional):");
        grid.add(perpetratorRelationLabel, 0, row);
        grid.add(perpetratorRelationCombo, 1, row++);

        // Number of Perpetrators
        Label perpetratorCountLabel = new Label("Number of Perpetrators(Optional):");
        perpetratorCountField = new TextField();
        perpetratorCountField.setPromptText("e.g., 3");
        grid.add(perpetratorCountLabel, 0, row);
        grid.add(perpetratorCountField, 1, row++);

        // Perpetrator Description
        Label perpetratorDescLabel = new Label("Perpetrator Description (Optional):");
        perpetratorDescriptionArea = new TextArea();
        perpetratorDescriptionArea.setPromptText("Physical description, names if known, etc.");
        perpetratorDescriptionArea.setPrefRowCount(3);
        grid.add(perpetratorDescLabel, 0, row);
        grid.add(perpetratorDescriptionArea, 1, row++);

        // Weapon Used
        weaponUsedCheckBox = new CheckBox("Weapon Used or Shown");
        grid.add(weaponUsedCheckBox, 0, row, 2, 1);
        row++;

        // Weapon Type (dependent on weapon used checkbox)
        Label weaponTypeLabel = new Label("Type of Weapon:");
        weaponTypeCombo = new ComboBox<>();
        weaponTypeCombo.getItems().addAll(
                "Knife/Sharp Object",
                "Gun/Firearm",
                "Stick/Rod",
                "Bomb/Explosive",
                "Chemical/Acid",
                "Other"
        );
        weaponTypeCombo.setPromptText("Select weapon type");
        weaponTypeCombo.setDisable(true);
        grid.add(weaponTypeLabel, 0, row);
        grid.add(weaponTypeCombo, 1, row++);

        // Money Paid
        moneyPaidCheckBox = new CheckBox("Money/Demand was Paid");
        grid.add(moneyPaidCheckBox, 0, row, 2, 1);
        row++;

        // Paid Amount (dependent on money paid checkbox)
        Label paidAmountLabel = new Label("Amount Paid (BDT):");
        paidAmountField = new TextField();
        paidAmountField.setPromptText("e.g., 50000");
        paidAmountField.setDisable(true);
        grid.add(paidAmountLabel, 0, row);
        grid.add(paidAmountField, 1, row++);

        // Payment Method (dependent on money paid checkbox)
        Label paymentMethodLabel = new Label("Payment Method:");
        paymentMethodCombo = new ComboBox<>();
        paymentMethodCombo.getItems().addAll(
                "Cash",
                "Bank Transfer",
                "Mobile Banking (bKash/Nagad)",
                "Check",
                "Property Transfer",
                "Other"
        );
        paymentMethodCombo.setPromptText("Select payment method");
        paymentMethodCombo.setDisable(true);
        grid.add(paymentMethodLabel, 0, row);
        grid.add(paymentMethodCombo, 1, row++);

        // Business Target
        businessTargetCheckBox = new CheckBox("Business was Targeted");
        grid.add(businessTargetCheckBox, 0, row, 2, 1);
        row++;

        // Business Type (dependent on business target checkbox)
        Label businessTypeLabel = new Label("Type of Business:");
        businessTypeCombo = new ComboBox<>();
        businessTypeCombo.getItems().addAll(
                "Retail Shop",
                "Restaurant",
                "Manufacturing",
                "Transport",
                "Construction",
                "Trading",
                "Service Business",
                "Other"
        );
        businessTypeCombo.setPromptText("Select business type");
        businessTypeCombo.setDisable(true);
        grid.add(businessTypeLabel, 0, row);
        grid.add(businessTypeCombo, 1, row++);

        // Business Name (dependent on business target checkbox)
        Label businessNameLabel = new Label("Business Name:");
        businessNameField = new TextField();
        businessNameField.setPromptText("Name of the business");
        businessNameField.setDisable(true);
        grid.add(businessNameLabel, 0, row);
        grid.add(businessNameField, 1, row++);

        // Ongoing Extortion
        ongoingExtortionCheckBox = new CheckBox("Ongoing/Repeated Extortion");
        grid.add(ongoingExtortionCheckBox, 0, row, 2, 1);
        row++;

        // Frequency (dependent on ongoing extortion checkbox)
        Label frequencyLabel = new Label("Frequency:");
        frequencyCombo = new ComboBox<>();
        frequencyCombo.getItems().addAll(
                "Daily",
                "Weekly",
                "Monthly",
                "Quarterly",
                "Yearly",
                "Irregular"
        );
        frequencyCombo.setPromptText("Select frequency");
        frequencyCombo.setDisable(true);
        grid.add(frequencyLabel, 0, row);
        grid.add(frequencyCombo, 1, row++);

        // Duration (dependent on ongoing extortion checkbox)
        Label durationLabel = new Label("Duration (months):");
        durationField = new TextField();
        durationField.setPromptText("e.g., 6");
        durationField.setDisable(true);
        grid.add(durationLabel, 0, row);
        grid.add(durationField, 1, row++);

        // Evidence Available
        evidenceAvailableCheckBox = new CheckBox("Evidence Available");
        grid.add(evidenceAvailableCheckBox, 0, row, 2, 1);
        row++;

        // Evidence Type (dependent on evidence available checkbox)
        Label evidenceTypeLabel = new Label("Type of Evidence:");
        evidenceTypeCombo = new ComboBox<>();
        evidenceTypeCombo.getItems().addAll(
                "Audio Recording",
                "Video Recording",
                "Text Messages",
                "Written Threats",
                "Email/Digital Evidence",
                "Photographs",
                "Bank Records",
                "Other"
        );
        evidenceTypeCombo.setPromptText("Select evidence type");
        evidenceTypeCombo.setDisable(true);
        grid.add(evidenceTypeLabel, 0, row);
        grid.add(evidenceTypeCombo, 1, row++);

        // Evidence Details (dependent on evidence available checkbox)
        Label evidenceDetailsLabel = new Label("Evidence Details:");
        evidenceDetailsArea = new TextArea();
        evidenceDetailsArea.setPromptText("Describe the evidence you have");
        evidenceDetailsArea.setPrefRowCount(2);
        evidenceDetailsArea.setDisable(true);
        grid.add(evidenceDetailsLabel, 0, row);
        grid.add(evidenceDetailsArea, 1, row++);

        // Witness Available
        witnessAvailableCheckBox = new CheckBox("Witnesses Available");
        grid.add(witnessAvailableCheckBox, 0, row, 2, 1);
        row++;

        // Witness Details (dependent on witness available checkbox)
        Label witnessDetailsLabel = new Label("Witness Details:");
        witnessDetailsArea = new TextArea();
        witnessDetailsArea.setPromptText("Names, contact numbers, and details of witnesses");
        witnessDetailsArea.setPrefRowCount(3);
        witnessDetailsArea.setDisable(true);
        grid.add(witnessDetailsLabel, 0, row);
        grid.add(witnessDetailsArea, 1, row++);

        // Police Contacted Previously
        policeContactedCheckBox = new CheckBox("Police Contacted Previously");
        grid.add(policeContactedCheckBox, 0, row, 2, 1);
        row++;

        // Previous Case Number (dependent on police contacted checkbox)
        Label previousCaseLabel = new Label("Previous Case Number:");
        previousCaseNumberField = new TextField();
        previousCaseNumberField.setPromptText("e.g., GD-123/2024");
        previousCaseNumberField.setDisable(true);
        grid.add(previousCaseLabel, 0, row);
        grid.add(previousCaseNumberField, 1, row++);

        // Communication Method
        Label communicationMethodLabel = new Label("How were you Contacted:");
        communicationMethodCombo = new ComboBox<>();
        communicationMethodCombo.getItems().addAll(
                "Phone Call",
                "Text Message",
                "In Person",
                "Email",
                "Social Media",
                "Through Others",
                "Written Note",
                "Other"
        );
        communicationMethodCombo.setPromptText("Select communication method ");
        grid.add(communicationMethodLabel, 0, row);
        grid.add(communicationMethodCombo, 1, row++);

        // Contact Details
        Label contactDetailsLabel = new Label("Perpetrator Contact Details:");
        contactDetailsField = new TextField();
        contactDetailsField.setPromptText("Phone number, email, or other contact info");
        grid.add(contactDetailsLabel, 0, row);
        grid.add(contactDetailsField, 1, row++);

        // Family Threatened
        familyThreatenedCheckBox = new CheckBox("Family Members Threatened");
        grid.add(familyThreatenedCheckBox, 0, row, 2, 1);
        row++;

        // Family Threat Details (dependent on family threatened checkbox)
        Label familyThreatDetailsLabel = new Label("Family Threat Details:");
        familyThreatDetailsArea = new TextArea();
        familyThreatDetailsArea.setPromptText("Details of threats made against family");
        familyThreatDetailsArea.setPrefRowCount(2);
        familyThreatDetailsArea.setDisable(true);
        grid.add(familyThreatDetailsLabel, 0, row);
        grid.add(familyThreatDetailsArea, 1, row++);

        // Victim Occupation
        Label victimOccupationLabel = new Label("Victim's Occupation:");
        victimOccupationCombo = new ComboBox<>();
        victimOccupationCombo.getItems().addAll(
                "Business Owner",
                "Employee",
                "Government Officer",
                "Teacher",
                "Doctor",
                "Engineer",
                "Student",
                "Housewife",
                "Retired",
                "Other"
        );
        victimOccupationCombo.setPromptText("Select occupation");
        grid.add(victimOccupationLabel, 0, row);
        grid.add(victimOccupationCombo, 1, row++);

        // Other Occupation (dependent on "Other" selection)
        Label otherOccupationLabel = new Label("Other Occupation:");
        otherOccupationField = new TextField();
        otherOccupationField.setPromptText("Specify occupation");
        otherOccupationField.setDisable(true);
        grid.add(otherOccupationLabel, 0, row);
        grid.add(otherOccupationField, 1, row++);

        // Incident Description
        Label incidentDescLabel = new Label("Detailed Incident Description:");
        incidentDescriptionArea = new TextArea();
        incidentDescriptionArea.setPromptText("Provide a detailed description of the incident");
        incidentDescriptionArea.setPrefRowCount(4);
        grid.add(incidentDescLabel, 0, row);
        grid.add(incidentDescriptionArea, 1, row++);

        // Set up dependent field listeners
        setupDependentFields();

        // Wrap in ScrollPane
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setPrefSize(850,600);
        dialog.getDialogPane().setContent(scroll);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Handle result with continuous validation
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (!validate()) {
                event.consume(); // Prevent dialog from closing
            }
        });

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // This will only execute if validation passes
                // Record the case
                UserDashboard.CaseRecord newCase = new UserDashboard.CaseRecord(
                        "CR" + System.currentTimeMillis(),
                        "Extortion",
                        extortionTypeCombo.getValue() + " - " + demandTypeCombo.getValue(),
                        LocalDateTime.now(),
                        "Under Investigation"
                );

                // Confirmation
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Report Submitted");
                info.setHeaderText("Your extortion report has been submitted successfully");
                info.setContentText("Case ID: " + newCase.getCaseId());
                info.showAndWait();
            }
        });
    }

    private void setupDependentFields() {
        // Weapon dependent fields
        weaponUsedCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            weaponTypeCombo.setDisable(!newVal);
            if (!newVal) {
                weaponTypeCombo.setValue(null);
            }
        });

        // Money paid dependent fields
        moneyPaidCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            paidAmountField.setDisable(!newVal);
            paymentMethodCombo.setDisable(!newVal);
            if (!newVal) {
                paidAmountField.clear();
                paymentMethodCombo.setValue(null);
            }
        });

        // Business target dependent fields
        businessTargetCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            businessTypeCombo.setDisable(!newVal);
            businessNameField.setDisable(!newVal);
            if (!newVal) {
                businessTypeCombo.setValue(null);
                businessNameField.clear();
            }
        });

        // Ongoing extortion dependent fields
        ongoingExtortionCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            frequencyCombo.setDisable(!newVal);
            durationField.setDisable(!newVal);
            if (!newVal) {
                frequencyCombo.setValue(null);
                durationField.clear();
            }
        });

        // Evidence dependent fields
        evidenceAvailableCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            evidenceTypeCombo.setDisable(!newVal);
            evidenceDetailsArea.setDisable(!newVal);
            if (!newVal) {
                evidenceTypeCombo.setValue(null);
                evidenceDetailsArea.clear();
            }
        });

        // Witness dependent fields
        witnessAvailableCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            witnessDetailsArea.setDisable(!newVal);
            if (!newVal) {
                witnessDetailsArea.clear();
            }
        });

        // Police contacted dependent fields
        policeContactedCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            previousCaseNumberField.setDisable(!newVal);
            if (!newVal) {
                previousCaseNumberField.clear();
            }
        });

        // Family threatened dependent fields
        familyThreatenedCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            familyThreatDetailsArea.setDisable(!newVal);
            if (!newVal) {
                familyThreatDetailsArea.clear();
            }
        });

        // Other occupation dependent field
        victimOccupationCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            otherOccupationField.setDisable(!"Other".equals(newVal));
            if (!"Other".equals(newVal)) {
                otherOccupationField.clear();
            }
        });
    }

    private boolean validate() {
        StringBuilder errors = new StringBuilder();

        // Required fields validation
        if (extortionTypeCombo.getValue() == null) {
            errors.append("• Type of Extortion is required\n");
        }

        if (demandTypeCombo.getValue() == null) {
            errors.append("• What was Demanded is required\n");
        }

        if (demandAmountField.getText().trim().isEmpty()) {
            errors.append("• Demanded Amount is required\n");
        } else {
            try {
                Double.parseDouble(demandAmountField.getText().trim());
            } catch (NumberFormatException e) {
                errors.append("• Demanded Amount must be a valid number\n");
            }
        }

        if (threatTypeCombo.getValue() == null) {
            errors.append("• Type of Threat is required\n");
        }

        if (communicationMethodCombo.getValue() == null) {
            errors.append("• Communication Method is required\n");
        }

        if (victimOccupationCombo.getValue() == null) {
            errors.append("• Victim's Occupation is required\n");
        }


        // Dependent field validation
        if (weaponUsedCheckBox.isSelected() && weaponTypeCombo.getValue() == null) {
            errors.append("• Type of Weapon is required when weapon was used\n");
        }

        if (moneyPaidCheckBox.isSelected()) {
            if (paidAmountField.getText().trim().isEmpty()) {
                errors.append("• Amount Paid is required when money was paid\n");
            } else {
                try {
                    Double.parseDouble(paidAmountField.getText().trim());
                } catch (NumberFormatException e) {
                    errors.append("• Amount Paid must be a valid number\n");
                }
            }
            if (paymentMethodCombo.getValue() == null) {
                errors.append("• Payment Method is required when money was paid\n");
            }
        }

        if (businessTargetCheckBox.isSelected()) {
            if (businessTypeCombo.getValue() == null) {
                errors.append("• Type of Business is required when business was targeted\n");
            }
            if (businessNameField.getText().trim().isEmpty()) {
                errors.append("• Business Name is required when business was targeted\n");
            }
        }

        if (ongoingExtortionCheckBox.isSelected()) {
            if (frequencyCombo.getValue() == null) {
                errors.append("• Frequency is required for ongoing extortion\n");
            }
            if (durationField.getText().trim().isEmpty()) {
                errors.append("• Duration is required for ongoing extortion\n");
            } else {
                try {
                    Integer.parseInt(durationField.getText().trim());
                } catch (NumberFormatException e) {
                    errors.append("• Duration must be a valid number\n");
                }
            }
        }

        if (evidenceAvailableCheckBox.isSelected()) {
            if (evidenceTypeCombo.getValue() == null) {
                errors.append("• Type of Evidence is required when evidence is available\n");
            }
            if (evidenceDetailsArea.getText().trim().isEmpty()) {
                errors.append("• Evidence Details is required when evidence is available\n");
            }
        }

        if (witnessAvailableCheckBox.isSelected() && witnessDetailsArea.getText().trim().isEmpty()) {
            errors.append("• Witness Details is required when witnesses are available\n");
        }

        if (policeContactedCheckBox.isSelected() && previousCaseNumberField.getText().trim().isEmpty()) {
            errors.append("• Previous Case Number is required when police was contacted before\n");
        }

        if (familyThreatenedCheckBox.isSelected() && familyThreatDetailsArea.getText().trim().isEmpty()) {
            errors.append("• Family Threat Details is required when family was threatened\n");
        }

        if ("Other".equals(victimOccupationCombo.getValue()) && otherOccupationField.getText().trim().isEmpty()) {
            errors.append("• Other Occupation is required when 'Other' is selected\n");
        }

        // Show validation errors if any
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please correct the following errors:");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }
}