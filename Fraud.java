package org.example.java;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
public class Fraud extends Crime {

    public Fraud() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Report Fraud");
        dialog.setHeaderText("Please provide details about the incident");

        // 1) Build your form grid first
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Fraud‑specific fields
        Label fraudLabel = new Label("Type of Fraud:");
        TextField fraudField = new TextField();
        fraudField.setPromptText("e.g. Online Scam");

        grid.add(fraudLabel, 0, 0);
        grid.add(fraudField, 1, 0);

        // 2) Wrap the grid in a ScrollPane
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // 3) Set the ScrollPane *once* as the dialog content
        dialog.getDialogPane().setContent(scroll);

        // 4) Add OK/Cancel buttons
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // 5) Handle result
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Validate input
                if (fraudField.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Please enter the type of fraud.").showAndWait();
                    return;
                }

                // Record the case
                UserDashboard.CaseRecord newCase = new UserDashboard.CaseRecord(
                        "CR" + System.currentTimeMillis(),
                        "Fraud",
                        fraudField.getText(),
                        LocalDateTime.now(),
                        "Under Investigation"
                );

                // Confirmation
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Report Submitted");
                info.setHeaderText("Your report has been submitted successfully");
                info.setContentText("Case ID: " + newCase.getCaseId());
                info.showAndWait();
            }
        });
    }
}

