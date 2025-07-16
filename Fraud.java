package src.main;

import com.example.demo1.Crime;
import com.example.demo1.UserDashboard;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDateTime;

public class Fraud extends Crime {

    public Fraud() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Report Fraud");
        dialog.setHeaderText("Please provide details about the incident");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));
        int row = 0;

        // 1. Type of Fraud
        grid.add(new Label("Type of Fraud:"), 0, row);
        ComboBox<String> fraudTypeBox = new ComboBox<>();
        fraudTypeBox.getItems().addAll(
                "Financial Fraud",
                "Land/Property Fraud",
                "Job Scam",
                "Online Scam",
                "Bank/Cheque Fraud",
                "Identity Fraud",
                "Other"
        );
        fraudTypeBox.setPrefWidth(300);
        grid.add(fraudTypeBox, 1, row++);

        TextField fraudOtherField = new TextField();
        fraudOtherField.setPromptText("If other, specify...");
        fraudOtherField.setDisable(true);
        grid.add(fraudOtherField, 1, row++);

        fraudTypeBox.setOnAction(e -> {
            fraudOtherField.setDisable(!fraudTypeBox.getValue().contains("Other"));
        });

        // 2. Mode of Communication
        grid.add(new Label("Mode of Communication:"), 0, row);
        VBox commModes = new VBox(5);
        CheckBox phone = new CheckBox("Phone Call");
        CheckBox sms = new CheckBox("SMS/Message");
        CheckBox whatsapp = new CheckBox("WhatsApp/Facebook");
        CheckBox email = new CheckBox("Email");
        CheckBox inPerson = new CheckBox("In-person Meeting");
        CheckBox commOther = new CheckBox("Other");
        commModes.getChildren().addAll(phone, sms, whatsapp, email, inPerson, commOther);
        grid.add(commModes, 1, row++);

        // 3. Financial Transaction
        grid.add(new Label("Was there a financial transaction?"), 0, row);
        ToggleGroup transactionGroup = new ToggleGroup();
        RadioButton yesTrans = new RadioButton("Yes");
        RadioButton noTrans = new RadioButton("No");
        yesTrans.setToggleGroup(transactionGroup);
        noTrans.setToggleGroup(transactionGroup);
        HBox transactionBox = new HBox(15, yesTrans, noTrans);
        grid.add(transactionBox, 1, row++);

        TextField amountField = new TextField();
        amountField.setPromptText("Amount in BDT");
        TextField methodField = new TextField();
        methodField.setPromptText("Cash, Bkash, Bank Transfer, etc.");
        amountField.setDisable(true);
        methodField.setDisable(true);
        grid.add(new Label("Transaction Amount:"), 0, row);
        grid.add(amountField, 1, row++);
        grid.add(new Label("Transaction Method:"), 0, row);
        grid.add(methodField, 1, row++);

        yesTrans.setOnAction(e -> {
            amountField.setDisable(false);
            methodField.setDisable(false);
        });
        noTrans.setOnAction(e -> {
            amountField.clear();
            methodField.clear();
            amountField.setDisable(true);
            methodField.setDisable(true);
        });

        // 4. Supporting Documents
        grid.add(new Label("Supporting Documents:"), 0, row);
        VBox docsBox = new VBox(5);
        CheckBox contract = new CheckBox("Contract or Agreement");
        CheckBox receipt = new CheckBox("Receipt/Bill");
        CheckBox bank = new CheckBox("Bank Statement");
        CheckBox cheque = new CheckBox("Cheque/Promissory Note");
        CheckBox chats = new CheckBox("Chat Screenshots");
        CheckBox media = new CheckBox("Photo/Video Evidence");
        CheckBox witness = new CheckBox("Witness Statement");
        docsBox.getChildren().addAll(contract, receipt, bank, cheque, chats, media, witness);
        grid.add(docsBox, 1, row++);

        // 5. Did the accused promise to return?
        grid.add(new Label("Did the accused promise to return or resolve?"), 0, row);
        ToggleGroup promiseGroup = new ToggleGroup();
        RadioButton promisedYes = new RadioButton("Yes");
        RadioButton promisedNo = new RadioButton("No");
        promisedYes.setToggleGroup(promiseGroup);
        promisedNo.setToggleGroup(promiseGroup);
        HBox promiseBox = new HBox(15, promisedYes, promisedNo);
        grid.add(promiseBox, 1, row++);

        // 6. Reported elsewhere?
        grid.add(new Label("Have you reported this elsewhere?"), 0, row);
        ToggleGroup reportGroup = new ToggleGroup();
        RadioButton reportedYes = new RadioButton("Yes, reported before");
        RadioButton reportedNo = new RadioButton("No, first time");
        reportedYes.setToggleGroup(reportGroup);
        reportedNo.setToggleGroup(reportGroup);
        HBox reportBox = new HBox(15, reportedYes, reportedNo);
        grid.add(reportBox, 1, row++);

        // 7. What action do you want from police?
        grid.add(new Label("What action do you seek from police?"), 0, row);
        TextArea actionArea = new TextArea();
        actionArea.setPromptText("e.g., I want legal action against the accused.");
        actionArea.setPrefRowCount(3);
        grid.add(actionArea, 1, row++);

        // ScrollPane
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setPadding(new Insets(10));
        scroll.setPrefViewportHeight(700);
        scroll.setPrefViewportWidth(700);
        dialog.getDialogPane().setPrefSize(800, 750); // Optional: sets the dialog pane size

        dialog.getDialogPane().setContent(scroll);

        // Buttons
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                // Basic validation
                if (fraudTypeBox.getValue() == null ||
                        (fraudTypeBox.getValue().contains("Other") && fraudOtherField.getText().trim().isEmpty())) {
                    showError("Please select or enter the type of fraud.");
                    return;
                }

                if (transactionGroup.getSelectedToggle() == yesTrans &&
                        (amountField.getText().isEmpty() || methodField.getText().isEmpty())) {
                    showError("Please enter transaction amount and method.");
                    return;
                }

                if (actionArea.getText().trim().isEmpty()) {
                    showError("Please describe what action you seek from police.");
                    return;
                }

                // Save simple record
                UserDashboard.CaseRecord newCase = new UserDashboard.CaseRecord(
                        "CR" + System.currentTimeMillis(),
                        "Fraud",
                        fraudTypeBox.getValue(),
                        LocalDateTime.now(),
                        "Under Investigation"
                );

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Report Submitted");
                info.setHeaderText("Your report has been submitted successfully");
                info.setContentText("Case ID: " + newCase.getCaseId());
                info.showAndWait();
            }
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Form Validation Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
