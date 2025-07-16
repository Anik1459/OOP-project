package org.example.java;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Robbery extends Crime {

    private ComboBox<String> robberyTypeCombo;
    private ComboBox<String> weaponUsedCombo;
    private TextField numberOfRobbersField;
    private TextArea robbersDescriptionArea;
    private TextArea itemsStolenArea;
    private TextField totalValueField;
    private ComboBox<String> vehicleUsedCombo;
    private TextField vehicleDetailsField;
    private ComboBox<String> injuryStatusCombo;
    private TextArea injuryDetailsArea;
    private ComboBox<String> witnessAvailableCombo;
    private TextArea witnessDetailsArea;
    private ComboBox<String> securityCameraCombo;
    private TextArea incidentDescriptionArea;
    private TextField suspectKnownField;
    private ComboBox<String> policeNotifiedCombo;
    private ComboBox<String> timeOfDayCombo;
    private ComboBox<String> lightingConditionCombo;
    private TextField crowdLevelField;
    private ComboBox<String> helpRequestedCombo;
    private TextArea additionalInfoArea;

    public Robbery() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Robbery Report - Bangladesh Police");
        dialog.setHeaderText("দস্যুতার ঘটনার বিবরণ দিন / Please provide details about the robbery incident");

        // Build form grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        int row = 0;

        // Incident Details
        Label incidentSectionLabel = new Label("Incident Details");
        incidentSectionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(incidentSectionLabel, 0, row++, 2, 1);

        Label robberyTypeLabel = new Label("Type of Robbery/দস্যুতার ধরন:*");
        robberyTypeCombo = new ComboBox<>();
        robberyTypeCombo.getItems().addAll(
                "Street Robbery / রাস্তার দস্যুতা",
                "Home Invasion / বাড়িতে দস্যুতা",
                "Shop/Business Robbery / দোকান/ব্যবসায়িক দস্যুতা",
                "Bank Robbery / ব্যাংক ডাকাতি",
                "Vehicle Hijacking / যানবাহন ছিনতাই",
                "Mobile/Chain Snatching / মোবাইল/চেইন ছিনতাই",
                "ATM Robbery / এটিএম ডাকাতি",
                "Other / অন্যান্য"
        );
        robberyTypeCombo.setPromptText("Select type of robbery");
        grid.add(robberyTypeLabel, 0, row);
        grid.add(robberyTypeCombo, 1, row++);

        Label timeOfDayLabel = new Label("Time of Day / দিনের সময়:*");
        timeOfDayCombo = new ComboBox<>();
        timeOfDayCombo.getItems().addAll(
                "Early Morning (5AM-8AM) / ভোর (৫-৮টা)",
                "Morning (8AM-12PM) / সকাল (৮-১২টা)",
                "Afternoon (12PM-4PM) / দুপুর (১২-৪টা)",
                "Evening (4PM-8PM) / সন্ধ্যা (৪-৮টা)",
                "Night (8PM-12AM) / রাত (৮-১২টা)",
                "Late Night (12AM-5AM) / গভীর রাত (১২-৫টা)"
        );
        timeOfDayCombo.setPromptText("Select time period");
        grid.add(timeOfDayLabel, 0, row);
        grid.add(timeOfDayCombo, 1, row++);

        Label lightingLabel = new Label("Lighting Condition / আলোর অবস্থা:*");
        lightingConditionCombo = new ComboBox<>();
        lightingConditionCombo.getItems().addAll(
                "Bright Daylight / উজ্জ্বল দিন",
                "Street Light / রাস্তার বাতি",
                "Dim Light / অস্পষ্ট আলো",
                "Dark / অন্ধকার"
        );
        lightingConditionCombo.setPromptText("Select lighting condition");
        grid.add(lightingLabel, 0, row);
        grid.add(lightingConditionCombo, 1, row++);

        Label crowdLevelLabel = new Label("Crowd Level / জনসমাগম:");
        crowdLevelField = new TextField();
        crowdLevelField.setPromptText("e.g. Crowded / জনাকীর্ণ, Empty / জনশূন্য");
        grid.add(crowdLevelLabel, 0, row);
        grid.add(crowdLevelField, 1, row++);

        Label incidentDescriptionLabel = new Label("Detailed Description / বিস্তারিত বিবরণ:*");
        incidentDescriptionArea = new TextArea();
        incidentDescriptionArea.setPromptText("Describe what happened step by step / ঘটনার ধাপে ধাপে বিবরণ দিন...");
        incidentDescriptionArea.setPrefRowCount(4);
        grid.add(incidentDescriptionLabel, 0, row);
        grid.add(incidentDescriptionArea, 1, row++);

        Label robberSectionLabel = new Label("Robber Information ");
        robberSectionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(robberSectionLabel, 0, row++, 2, 1);

        Label numberOfRobbersLabel = new Label("Number of Robbers / দস্যুর সংখ্যা:*");
        numberOfRobbersField = new TextField();
        numberOfRobbersField.setPromptText("e.g. 2, 3, Unknown");
        grid.add(numberOfRobbersLabel, 0, row);
        grid.add(numberOfRobbersField, 1, row++);

        Label robbersDescriptionLabel = new Label("Robbers Description / দস্যুদের বর্ণনা:*");
        robbersDescriptionArea = new TextArea();
        robbersDescriptionArea.setPromptText("Height, build, clothing, age, distinctive features / উচ্চতা, গড়ন, পোশাক, বয়স, বিশেষ চিহ্ন...");
        robbersDescriptionArea.setPrefRowCount(3);
        grid.add(robbersDescriptionLabel, 0, row);
        grid.add(robbersDescriptionArea, 1, row++);

        Label weaponUsedLabel = new Label("Weapon Used / ব্যবহৃত অস্ত্র:*");
        weaponUsedCombo = new ComboBox<>();
        weaponUsedCombo.getItems().addAll(
                "No Weapon / কোন অস্ত্র নেই",
                "Knife / ছুরি",
                "Gun / বন্দুক",
                "Stick/Rod / লাঠি/রড",
                "Threat Only / শুধু হুমকি",
                "Other Sharp Object / অন্যান্য ধারালো বস্তু",
                "Unknown / অজানা"
        );
        weaponUsedCombo.setPromptText("Select weapon type");
        grid.add(weaponUsedLabel, 0, row);
        grid.add(weaponUsedCombo, 1, row++);

        Label suspectKnownLabel = new Label("Suspect Known / সন্দেহভাজন পরিচিত:");
        suspectKnownField = new TextField();
        suspectKnownField.setPromptText("Name if known / নাম জানা থাকলে");
        grid.add(suspectKnownLabel, 0, row);
        grid.add(suspectKnownField, 1, row++);

        Label vehicleUsedLabel = new Label("Vehicle Used / ব্যবহৃত যানবাহন:");
        vehicleUsedCombo = new ComboBox<>();
        vehicleUsedCombo.getItems().addAll(
                "No Vehicle / কোন যানবাহন নেই",
                "Motorcycle / মোটরসাইকেল",
                "Car / গাড়ি",
                "Rickshaw / রিকশা",
                "CNG / সিএনজি",
                "Bus / বাস",
                "Bicycle / সাইকেল",
                "On Foot / পায়ে হেঁটে",
                "Other / অন্যান্য"
        );
        vehicleUsedCombo.setPromptText("Select vehicle type");
        grid.add(vehicleUsedLabel, 0, row);
        grid.add(vehicleUsedCombo, 1, row++);

        Label vehicleDetailsLabel = new Label("Vehicle Details / যানবাহনের বিবরণ:");
        vehicleDetailsField = new TextField();
        vehicleDetailsField.setPromptText("Number plate, color, model / নম্বর প্লেট, রং, মডেল");
        grid.add(vehicleDetailsLabel, 0, row);
        grid.add(vehicleDetailsField, 1, row++);

        // Stolen Items
        Label stolenSectionLabel = new Label("Stolen Items");
        stolenSectionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(stolenSectionLabel, 0, row++, 2, 1);

        Label itemsStolenLabel = new Label("Items Stolen / চুরি হওয়া জিনিস:*");
        itemsStolenArea = new TextArea();
        itemsStolenArea.setPromptText("List all stolen items / সব চুরি হওয়া জিনিসের তালিকা...");
        itemsStolenArea.setPrefRowCount(3);
        grid.add(itemsStolenLabel, 0, row);
        grid.add(itemsStolenArea, 1, row++);

        Label totalValueLabel = new Label("Total Value / মোট মূল্য:*");
        totalValueField = new TextField();
        totalValueField.setPromptText("Total value in BDT / মোট মূল্য টাকায়");
        grid.add(totalValueLabel, 0, row);
        grid.add(totalValueField, 1, row++);

        // Injury and Medical
        Label injurySectionLabel = new Label("Injury and Medical / আঘাত ও চিকিৎসা:");
        injurySectionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(injurySectionLabel, 0, row++, 2, 1);

        Label injuryStatusLabel = new Label("Injury Status / আঘাতের অবস্থা:*");
        injuryStatusCombo = new ComboBox<>();
        injuryStatusCombo.getItems().addAll(
                "No Injury / কোন আঘাত নেই",
                "Minor Injury / সামান্য আঘাত",
                "Serious Injury / গুরুতর আঘাত",
                "Critical Injury / সংকটজনক আঘাত"
        );
        injuryStatusCombo.setPromptText("Select injury status");
        grid.add(injuryStatusLabel, 0, row);
        grid.add(injuryStatusCombo, 1, row++);

        Label injuryDetailsLabel = new Label("Injury Details / আঘাতের বিবরণ:");
        injuryDetailsArea = new TextArea();
        injuryDetailsArea.setPromptText("Describe injuries if any / আঘাত থাকলে বিবরণ দিন...");
        injuryDetailsArea.setPrefRowCount(2);
        grid.add(injuryDetailsLabel, 0, row);
        grid.add(injuryDetailsArea, 1, row++);

        Label witnessSectionLabel = new Label("Witness Information ");
        witnessSectionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(witnessSectionLabel, 0, row++, 2, 1);

        Label witnessAvailableLabel = new Label("Witness Available / সাক্ষী আছে:*");
        witnessAvailableCombo = new ComboBox<>();
        witnessAvailableCombo.getItems().addAll("Yes / হ্যাঁ", "No / না", "Unknown / অজানা");
        witnessAvailableCombo.setPromptText("Are there any witnesses?");
        grid.add(witnessAvailableLabel, 0, row);
        grid.add(witnessAvailableCombo, 1, row++);

        Label witnessDetailsLabel = new Label("Witness Details / সাক্ষীর বিবরণ:");
        witnessDetailsArea = new TextArea();
        witnessDetailsArea.setPromptText("Names, contact numbers of witnesses / সাক্ষীদের নাম, যোগাযোগ নম্বর...");
        witnessDetailsArea.setPrefRowCount(3);
        witnessDetailsArea.setDisable(true); // Initially disabled
        grid.add(witnessDetailsLabel, 0, row);
        grid.add(witnessDetailsArea, 1, row++);

        // Add listener to enable/disable witness details based on selection
        witnessAvailableCombo.setOnAction(e -> {
            if (witnessAvailableCombo.getValue() != null &&
                    witnessAvailableCombo.getValue().startsWith("Yes")) {
                witnessDetailsArea.setDisable(false);
            } else {
                witnessDetailsArea.setDisable(true);
                witnessDetailsArea.clear();
            }
        });


        Label securitySectionLabel = new Label("Security and Evidence");
        securitySectionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(securitySectionLabel, 0, row++, 2, 1);

        Label securityCameraLabel = new Label("Security Camera / নিরাপত্তা ক্যামেরা:*");
        securityCameraCombo = new ComboBox<>();
        securityCameraCombo.getItems().addAll(
                "Yes, Available / হ্যাঁ, আছে",
                "No / না",
                "Unknown / অজানা"
        );
        securityCameraCombo.setPromptText("CCTV footage available?");
        grid.add(securityCameraLabel, 0, row);
        grid.add(securityCameraCombo, 1, row++);

        Label helpRequestedLabel = new Label("Help Requested / সাহায্য চাওয়া:*");
        helpRequestedCombo = new ComboBox<>();
        helpRequestedCombo.getItems().addAll(
                "Yes, People Helped / হ্যাঁ, লোকজন সাহায্য করেছে",
                "Yes, But No Help / হ্যাঁ, কিন্তু সাহায্য পাইনি",
                "No Help Requested / সাহায্য চাইনি",
                "No One Around / কেউ আশেপাশে ছিল না"
        );
        helpRequestedCombo.setPromptText("Did you request help?");
        grid.add(helpRequestedLabel, 0, row);
        grid.add(helpRequestedCombo, 1, row++);

        // Additional Information
        Label additionalSectionLabel = new Label("Additional Information / অতিরিক্ত তথ্য:");
        additionalSectionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(additionalSectionLabel, 0, row++, 2, 1);

        Label additionalInfoLabel = new Label("Additional Information / অতিরিক্ত তথ্য:");
        additionalInfoArea = new TextArea();
        additionalInfoArea.setPromptText("Any other relevant information / অন্য কোন প্রাসঙ্গিক তথ্য...");
        additionalInfoArea.setPrefRowCount(3);
        grid.add(additionalInfoLabel, 0, row);
        grid.add(additionalInfoArea, 1, row++);

        // Wrap in ScrollPane
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Set preferred size for dialog
        scroll.setPrefSize(750,600);

        dialog.getDialogPane().setContent(scroll);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Handle result
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (validate()) {
                    // Record the case
                    UserDashboard.CaseRecord newCase = new UserDashboard.CaseRecord(
                            "CR" + System.currentTimeMillis(),
                            "Robbery",
                            buildCaseDescription(),
                            LocalDateTime.now(),
                            "Under Investigation"
                    );

                    // Confirmation
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Report Submitted / রিপোর্ট জমা দেওয়া হয়েছে");
                    info.setHeaderText("Your robbery report has been submitted successfully");
                    info.setContentText("Case ID: " + newCase.getCaseId() +
                            "\nআপনার দস্যুতার রিপোর্ট সফলভাবে জমা দেওয়া হয়েছে।" +
                            "\nঅনুসন্ধানী টিম শীঘ্রই আপনার সাথে যোগাযোগ করবে।");
                    info.showAndWait();
                }
            }
        });
    }

    private boolean validate() {
        List<String> errors = new ArrayList<>();

        // Required fields validation
        if (robberyTypeCombo.getValue() == null) {
            errors.add("Type of robbery is required / দস্যুতার ধরন আবশ্যক");
        }

        if (timeOfDayCombo.getValue() == null) {
            errors.add("Time of day is required / দিনের সময় আবশ্যক");
        }

        if (lightingConditionCombo.getValue() == null) {
            errors.add("Lighting condition is required / আলোর অবস্থা আবশ্যক");
        }

        if (incidentDescriptionArea.getText().trim().isEmpty()) {
            errors.add("Detailed description is required / বিস্তারিত বিবরণ আবশ্যক");
        }

        if (numberOfRobbersField.getText().trim().isEmpty()) {
            errors.add("Number of robbers is required / দস্যুর সংখ্যা আবশ্যক");
        }

        if (robbersDescriptionArea.getText().trim().isEmpty()) {
            errors.add("Robbers description is required / দস্যুদের বর্ণনা আবশ্যক");
        }

        if (weaponUsedCombo.getValue() == null) {
            errors.add("Weapon used information is required / ব্যবহৃত অস্ত্রের তথ্য আবশ্যক");
        }

        if (itemsStolenArea.getText().trim().isEmpty()) {
            errors.add("Items stolen information is required / চুরি হওয়া জিনিসের তথ্য আবশ্যক");
        }

        if (totalValueField.getText().trim().isEmpty()) {
            errors.add("Total value is required / মোট মূল্য আবশ্যক");
        }

        if (injuryStatusCombo.getValue() == null) {
            errors.add("Injury status is required / আঘাতের অবস্থা আবশ্যক");
        }

        if (witnessAvailableCombo.getValue() == null) {
            errors.add("Witness availability information is required / সাক্ষীর তথ্য আবশ্যক");
        }

        // Validate witness details if witness is available
        if (witnessAvailableCombo.getValue() != null &&
                witnessAvailableCombo.getValue().startsWith("Yes") &&
                witnessDetailsArea.getText().trim().isEmpty()) {
            errors.add("Witness details are required when witnesses are available / সাক্ষী থাকলে সাক্ষীর বিবরণ আবশ্যক");
        }

        if (securityCameraCombo.getValue() == null) {
            errors.add("Security camera information is required / নিরাপত্তা ক্যামেরার তথ্য আবশ্যক");
        }

        if (helpRequestedCombo.getValue() == null) {
            errors.add("Help requested information is required / সাহায্য চাওয়ার তথ্য আবশ্যক");
        }

        if (policeNotifiedCombo.getValue() == null) {
            errors.add("Police notification information is required / পুলিশকে জানানোর তথ্য আবশ্যক");
        }

        // Validate total value format
        if (!totalValueField.getText().trim().isEmpty()) {
            try {
                String value = totalValueField.getText().trim().replaceAll("[^\\d.]", "");
                if (value.isEmpty()) {
                    errors.add("Please enter a valid amount / সঠিক পরিমাণ লিখুন");
                } else {
                    double amount = Double.parseDouble(value);
                    if (amount < 0) {
                        errors.add("Amount cannot be negative / পরিমাণ নেগেটিভ হতে পারে না");
                    }
                }
            } catch (NumberFormatException e) {
                errors.add("Please enter a valid amount / সঠিক পরিমাণ লিখুন");
            }
        }

        // Validate number of robbers
        if (!numberOfRobbersField.getText().trim().isEmpty() &&
                !numberOfRobbersField.getText().trim().equalsIgnoreCase("unknown")) {
            try {
                int count = Integer.parseInt(numberOfRobbersField.getText().trim());
                if (count <= 0) {
                    errors.add("Number of robbers must be positive / দস্যুর সংখ্যা ধনাত্মক হতে হবে");
                }
            } catch (NumberFormatException e) {
                // Allow "unknown" or text descriptions
            }
        }

        // Show errors if any
        if (!errors.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Please fix the following errors:\n" +
                    "নিম্নলিখিত ত্রুটিগুলি ঠিক করুন:\n\n");
            for (String error : errors) {
                errorMessage.append("• ").append(error).append("\n");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error / যাচাইকরণ ত্রুটি");
            alert.setHeaderText("Please complete all required fields / সব আবশ্যক ক্ষেত্র পূরণ করুন");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }

    private String buildCaseDescription() {
        StringBuilder description = new StringBuilder();
        description.append("Robbery Type: ").append(robberyTypeCombo.getValue()).append("\n");
        description.append("Time of Day: ").append(timeOfDayCombo.getValue()).append("\n");
        description.append("Lighting: ").append(lightingConditionCombo.getValue()).append("\n");
        description.append("Number of Robbers: ").append(numberOfRobbersField.getText()).append("\n");
        description.append("Weapon Used: ").append(weaponUsedCombo.getValue()).append("\n");
        description.append("Total Value Lost: ").append(totalValueField.getText()).append(" BDT\n");
        description.append("Injury Status: ").append(injuryStatusCombo.getValue()).append("\n");
        description.append("Witness Available: ").append(witnessAvailableCombo.getValue()).append("\n");
        description.append("Security Camera: ").append(securityCameraCombo.getValue()).append("\n");
        description.append("Police Notified: ").append(policeNotifiedCombo.getValue()).append("\n");

        description.append("\nDetailed Description:\n").append(incidentDescriptionArea.getText()).append("\n");
        description.append("\nRobbers Description:\n").append(robbersDescriptionArea.getText()).append("\n");
        description.append("\nItems Stolen:\n").append(itemsStolenArea.getText()).append("\n");

        if (!crowdLevelField.getText().trim().isEmpty()) {
            description.append("\nCrowd Level: ").append(crowdLevelField.getText()).append("\n");
        }

        if (!suspectKnownField.getText().trim().isEmpty()) {
            description.append("\nSuspect Known: ").append(suspectKnownField.getText()).append("\n");
        }

        if (vehicleUsedCombo.getValue() != null && !vehicleUsedCombo.getValue().contains("No Vehicle")) {
            description.append("\nVehicle Used: ").append(vehicleUsedCombo.getValue()).append("\n");
            if (!vehicleDetailsField.getText().trim().isEmpty()) {
                description.append("Vehicle Details: ").append(vehicleDetailsField.getText()).append("\n");
            }
        }

        if (!injuryDetailsArea.getText().trim().isEmpty()) {
            description.append("\nInjury Details: ").append(injuryDetailsArea.getText()).append("\n");
        }



        if (!witnessDetailsArea.getText().trim().isEmpty()) {
            description.append("\nWitness Details: ").append(witnessDetailsArea.getText()).append("\n");
        }



        if (!additionalInfoArea.getText().trim().isEmpty()) {
            description.append("\nAdditional Information: ").append(additionalInfoArea.getText()).append("\n");
        }

        return description.toString();
    }
}