package src.main;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;


public class DrugOffence extends Crime {

    @Override
    public void absMethod() {
        // --- Base / common form ---------------------------------------------------------
        GridPane formGrid = buildForm(); // from Crime (includes complainant, contact, etc.)
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(20));

        int row = formGrid.getRowCount(); // append after common fields

        // ---------------------------------------------------------------------------------
        // 1. Type of drug involved (ComboBox)
        // ---------------------------------------------------------------------------------
        Label drugTypeLabel = new Label("What type of drug is involved? *");
        ComboBox<String> drugTypeBox = new ComboBox<>();
        drugTypeBox.getItems().addAll(
                "Cannabis",
                "Heroin",
                "Yaba",
                "Cocaine",
                "Methamphetamine",
                "Phensedyl / Codeine syrup",
                "Opium",
                "Other"
        );
        drugTypeBox.setPromptText("Select drug type");

        TextField otherDrugField = new TextField();
        otherDrugField.setPromptText("If 'Other', specify");
        otherDrugField.setDisable(true);
        drugTypeBox.setOnAction(e -> {
            String sel = drugTypeBox.getValue();
            otherDrugField.setDisable(sel == null || !"Other".equals(sel));
        });

        formGrid.add(drugTypeLabel, 0, row);
        formGrid.add(drugTypeBox, 1, row++);
        formGrid.add(otherDrugField, 1, row++);

        // ---------------------------------------------------------------------------------
        // 2. Quantity of drug
        // ---------------------------------------------------------------------------------
        Label qtyLabel = new Label("Quantity involved (specify units) *");
        TextField qtyField = new TextField();
        qtyField.setPromptText("e.g., 500 gm, 20 tablets, 3 vials");
        formGrid.add(qtyLabel, 0, row);
        formGrid.add(qtyField, 1, row++);

        // ---------------------------------------------------------------------------------
        // 3. Incident location (ComboBox)
        // ---------------------------------------------------------------------------------
        Label whereLabel = new Label("Where did the incident take place? *");
        ComboBox<String> whereBox = new ComboBox<>();
        whereBox.getItems().addAll(
                "Street / Road",
                "House / Residence",
                "Vehicle",
                "Public Place / Park",
                "Educational Institution",
                "Workplace / Factory",
                "Other"
        );
        whereBox.setPromptText("Select location type");

        TextField whereDetailsField = new TextField();
        whereDetailsField.setPromptText("Exact address / description");
        whereDetailsField.setDisable(true);
        whereBox.setOnAction(e -> {
            String sel = whereBox.getValue();
            // Always allow address details when something selected; but required if Other.
            whereDetailsField.setDisable(sel == null);
        });

        formGrid.add(whereLabel, 0, row);
        formGrid.add(whereBox, 1, row++);
        formGrid.add(whereDetailsField, 1, row++);

        // ---------------------------------------------------------------------------------
        // 4. Date & approx time noticed
        // ---------------------------------------------------------------------------------
        Label whenLabel = new Label("When did you notice / encounter the drug offence? *");
        DatePicker datePicker = new DatePicker();
        TextField timeField = new TextField();
        timeField.setPromptText("Approx time (e.g., 10:30 PM)");
        HBox whenBox = new HBox(10, datePicker, timeField);
        formGrid.add(whenLabel, 0, row);
        formGrid.add(whenBox, 1, row++);
        Label discoverLabel = new Label("How was the drug discovered / detected? *");
        ComboBox<String> discoverBox = new ComboBox<>();
        discoverBox.getItems().addAll(
                "Random Check",
                "Tip-off / Informant",
                "Suspicious Behavior",
                "Public Complaint",
                "Routine Patrol",
                "Other"
        );
        discoverBox.setPromptText("Select method");

        TextField discoverOtherField = new TextField();
        discoverOtherField.setPromptText("If 'Other', specify");
        discoverOtherField.setDisable(true);
        discoverBox.setOnAction(e -> {
            String sel = discoverBox.getValue();
            discoverOtherField.setDisable(sel == null || !"Other".equals(sel));
        });

        formGrid.add(discoverLabel, 0, row);
        formGrid.add(discoverBox, 1, row++);
        formGrid.add(discoverOtherField, 1, row++);

        // ---------------------------------------------------------------------------------
        // 6. Drug trafficking / selling observed? (Yes/No dropdown)
        // ---------------------------------------------------------------------------------
        Label traffickLabel = new Label("Drug trafficking / selling observed? *");
        ComboBox<String> traffickBox = new ComboBox<>();
        traffickBox.getItems().addAll("Yes", "No", "Not Sure");
        traffickBox.setPromptText("Select");
        formGrid.add(traffickLabel, 0, row);
        formGrid.add(traffickBox, 1, row++);

        // ---------------------------------------------------------------------------------
        // 7. Exchange of money/items related to drug trade? (Yes/No dropdown)
        // ---------------------------------------------------------------------------------
        Label exchangeLabel = new Label("Exchange of money / items seen? *");
        ComboBox<String> exchangeBox = new ComboBox<>();
        exchangeBox.getItems().addAll("Yes", "No", "Not Sure");
        exchangeBox.setPromptText("Select");
        formGrid.add(exchangeLabel, 0, row);
        formGrid.add(exchangeBox, 1, row++);

        // ---------------------------------------------------------------------------------
        // 8. Suspicious vehicles or persons
        // ---------------------------------------------------------------------------------
        Label vehicleLabel = new Label("Suspicious vehicles / persons at scene?");
        TextArea vehicleArea = new TextArea();
        vehicleArea.setPromptText("Vehicle description, registration, persons seen, etc.");
        vehicleArea.setPrefRowCount(3);
        formGrid.add(vehicleLabel, 0, row);
        formGrid.add(vehicleArea, 1, row++);

        // ---------------------------------------------------------------------------------
        // 9. Witnesses? (Yes/No dropdown -> if Yes show fields)
        // ---------------------------------------------------------------------------------
        Label witnessLabel = new Label("Any witnesses to the incident? *");
        ComboBox<String> witnessBox = new ComboBox<>();
        witnessBox.getItems().addAll("Yes", "No");
        witnessBox.setPromptText("Select");
        TextField witnessNameField = new TextField();
        witnessNameField.setPromptText("Witness name");
        TextField witnessPhoneField = new TextField();
        witnessPhoneField.setPromptText("Witness phone");
        witnessNameField.setDisable(true);
        witnessPhoneField.setDisable(true);
        witnessBox.setOnAction(e -> {
            String sel = witnessBox.getValue();
            boolean enable = "Yes".equals(sel);
            witnessNameField.setDisable(!enable);
            witnessPhoneField.setDisable(!enable);
            if (!enable) {
                witnessNameField.clear();
                witnessPhoneField.clear();
            }
        });
        formGrid.add(witnessLabel, 0, row);
        formGrid.add(witnessBox, 1, row++);
        formGrid.add(witnessNameField, 1, row++);
        formGrid.add(witnessPhoneField, 1, row++);

        // ---------------------------------------------------------------------------------
        // 10. Anyone caught consuming? (text)
        // ---------------------------------------------------------------------------------
        Label consumeLabel = new Label("Anyone caught consuming at the spot?");
        TextArea consumeArea = new TextArea();
        consumeArea.setPromptText("Signs of intoxication, drug use");
        consumeArea.setPrefRowCount(3);
        formGrid.add(consumeLabel, 0, row);
        formGrid.add(consumeArea, 1, row++);

        // ---------------------------------------------------------------------------------
        // 11. Drug-related items recovered? (ComboBox + details)
        // ---------------------------------------------------------------------------------
        Label itemsLabel = new Label("Drug-related items recovered?");
        ComboBox<String> itemsBox = new ComboBox<>();
        itemsBox.getItems().addAll(
                "Packets",
                "Syringes",
                "Pipes",
                "Weighing Machine / Scale",
                "Foil / Wraps",
                "Cash bundles",
                "Other",
                "None"
        );
        itemsBox.setPromptText("Select item");
        TextField itemsOtherField = new TextField();
        itemsOtherField.setPromptText("If 'Other', specify");
        itemsOtherField.setDisable(true);
        itemsBox.setOnAction(e -> {
            String sel = itemsBox.getValue();
            itemsOtherField.setDisable(sel == null || !"Other".equals(sel));
        });
        formGrid.add(itemsLabel, 0, row);
        formGrid.add(itemsBox, 1, row++);
        formGrid.add(itemsOtherField, 1, row++);

        // ---------------------------------------------------------------------------------
        // 12. Carrying weapons / other illegal items? (ComboBox + details)
        // ---------------------------------------------------------------------------------
        Label weaponsLabel = new Label("Carrying weapons or other illegal items?");
        ComboBox<String> weaponsBox = new ComboBox<>();
        weaponsBox.getItems().addAll(
                "None",
                "Knife",
                "Gun",
                "Homemade Weapon",
                "Explosives",
                "Other"
        );
        weaponsBox.setPromptText("Select");
        TextField weaponsOtherField = new TextField();
        weaponsOtherField.setPromptText("If 'Other', specify");
        weaponsOtherField.setDisable(true);
        weaponsBox.setOnAction(e -> {
            String sel = weaponsBox.getValue();
            weaponsOtherField.setDisable(sel == null || !"Other".equals(sel));
        });
        formGrid.add(weaponsLabel, 0, row);
        formGrid.add(weaponsBox, 1, row++);
        formGrid.add(weaponsOtherField, 1, row++);

        // ---------------------------------------------------------------------------------
        // 13. Seen involved in drugs before? (Yes/No)
        // ---------------------------------------------------------------------------------
        Label priorLabel = new Label("Seen this person/group involved in drugs before? *");
        ComboBox<String> priorBox = new ComboBox<>();
        priorBox.getItems().addAll("Yes", "No", "Not Sure");
        priorBox.setPromptText("Select");
        formGrid.add(priorLabel, 0, row);
        formGrid.add(priorBox, 1, row++);

        // ---------------------------------------------------------------------------------
        // 14. Threats / attempts to destroy evidence? (ComboBox)
        // ---------------------------------------------------------------------------------
        Label threatLabel = new Label("Threats or attempts to destroy evidence?");
        ComboBox<String> threatBox = new ComboBox<>();
        threatBox.getItems().addAll(
                "None",
                "Threats to destroy",
                "Attempt to flush drugs",
                "Attempt to burn/dispose",
                "Evidence already destroyed",
                "Other"
        );
        threatBox.setPromptText("Select");
        TextField threatOtherField = new TextField();
        threatOtherField.setPromptText("If 'Other', specify");
        threatOtherField.setDisable(true);
        threatBox.setOnAction(e -> {
            String sel = threatBox.getValue();
            threatOtherField.setDisable(sel == null || !"Other".equals(sel));
        });
        formGrid.add(threatLabel, 0, row);
        formGrid.add(threatBox, 1, row++);
        formGrid.add(threatOtherField, 1, row++);

        // ---------------------------------------------------------------------------------
        // 15. CCTV / photo / video evidence?  (Yes/No -> enable upload buttons)
        // ---------------------------------------------------------------------------------
        Label evidenceLabel = new Label("CCTV / photo / video evidence available? *");
        ComboBox<String> evidenceBox = new ComboBox<>();
        evidenceBox.getItems().addAll("Yes", "No");
        evidenceBox.setPromptText("Select");

        Button uploadVideoBtn = new Button("Upload Video");
        Button uploadPhotoBtn = new Button("Upload Photo");
        uploadVideoBtn.setDisable(true);
        uploadPhotoBtn.setDisable(true);

        final FileChooser videoChooser = new FileChooser();
        videoChooser.setTitle("Select Video Evidence");
        videoChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mov", "*.avi", "*.mkv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        final FileChooser photoChooser = new FileChooser();
        photoChooser.setTitle("Select Photo Evidence");
        photoChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        final File[] videoFileHolder = new File[1];
        final File[] photoFileHolder = new File[1];

        evidenceBox.setOnAction(e -> {
            boolean enable = "Yes".equals(evidenceBox.getValue());
            uploadVideoBtn.setDisable(!enable);
            uploadPhotoBtn.setDisable(!enable);
            if (!enable) {
                uploadVideoBtn.setText("Upload Video");
                uploadPhotoBtn.setText("Upload Photo");
                videoFileHolder[0] = null;
                photoFileHolder[0] = null;
            }
        });

        uploadVideoBtn.setOnAction(e -> {
            Window w = formGrid.getScene().getWindow();
            File f = videoChooser.showOpenDialog(w);
            if (f != null) {
                videoFileHolder[0] = f;
                uploadVideoBtn.setText("Video: " + f.getName());
            }
        });
        uploadPhotoBtn.setOnAction(e -> {
            Window w = formGrid.getScene().getWindow();
            File f = photoChooser.showOpenDialog(w);
            if (f != null) {
                photoFileHolder[0] = f;
                uploadPhotoBtn.setText("Photo: " + f.getName());
            }
        });

        formGrid.add(evidenceLabel, 0, row);
        formGrid.add(evidenceBox, 1, row++);
        HBox evidenceBtnBox = new HBox(10, uploadVideoBtn, uploadPhotoBtn);
        formGrid.add(evidenceBtnBox, 1, row++);

        // ---------------------------------------------------------------------------------
        // ACTION BUTTONS ------------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        Button submitBtn = new Button("Submit");
        Button clearBtn = new Button("Clear");
        HBox btnBox = new HBox(10, submitBtn, clearBtn);
        formGrid.add(btnBox, 1, row++);

        // ---------------------------------------------------------------------------------
        // Validation & Submit handler
        // ---------------------------------------------------------------------------------
        submitBtn.setOnAction(e -> {
            // 1) Validate common form (except Accused info) - requires method in Crime.
            // TODO: Implement validateCommonFieldsExceptAccused() in Crime and uncomment.
            // if (!validateCommonFieldsExceptAccused()) {
            //     showAlert(Alert.AlertType.ERROR, "Validation Error", "Please complete required information in the common section.");
            //     return;
            // }

            // 2) Drug type
            if (drugTypeBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Select the drug type.");
                return;
            }
            if ("Other".equals(drugTypeBox.getValue()) && otherDrugField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Specify the drug type.");
                return;
            }

            if (whereBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Select where the incident took place.");
                return;
            }
            if (whereDetailsField.isDisabled() == false && whereDetailsField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Enter exact location details.");
                return;
            }

            // 5) Date
            if (datePicker.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Select the date of the incident.");
                return;
            }
            if (discoverBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Select how the drug was discovered.");
                return;
            }
            if ("Other".equals(discoverBox.getValue()) && discoverOtherField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Specify how it was discovered.");
                return;
            }

            if (traffickBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Indicate if trafficking/selling was observed.");
                return;
            }

            if (exchangeBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Indicate if money/items exchange was seen.");
                return;
            }

            if (witnessBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Indicate if there were witnesses.");
                return;
            }
            if ("Yes".equals(witnessBox.getValue())) {
                if (witnessNameField.getText().trim().isEmpty() || witnessPhoneField.getText().trim().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Enter witness name and phone.");
                    return;
                }
            }

            if (priorBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Indicate prior involvement.");
                return;
            }

            // 11) Evidence yes/no + file(s) if yes
            if (evidenceBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Indicate if you have evidence.");
                return;
            }
            if ("Yes".equals(evidenceBox.getValue()) && videoFileHolder[0] == null && photoFileHolder[0] == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Upload at least one evidence file (photo or video).");
                return;
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "Drug offence report submitted successfully!");
            // TODO: collect data into model + persist / send to backend.
        });

        clearBtn.setOnAction(e -> {
            drugTypeBox.setValue(null);
            otherDrugField.clear();
            otherDrugField.setDisable(true);

            qtyField.clear();

            whereBox.setValue(null);
            whereDetailsField.clear();
            whereDetailsField.setDisable(true);

            datePicker.setValue(null);
            timeField.clear();

            discoverBox.setValue(null);
            discoverOtherField.clear();
            discoverOtherField.setDisable(true);

            traffickBox.setValue(null);
            exchangeBox.setValue(null);
            vehicleArea.clear();

            witnessBox.setValue(null);
            witnessNameField.clear();
            witnessPhoneField.clear();
            witnessNameField.setDisable(true);
            witnessPhoneField.setDisable(true);

            consumeArea.clear();

            itemsBox.setValue(null);
            itemsOtherField.clear();
            itemsOtherField.setDisable(true);

            weaponsBox.setValue(null);
            weaponsOtherField.clear();
            weaponsOtherField.setDisable(true);

            priorBox.setValue(null);

            threatBox.setValue(null);
            threatOtherField.clear();
            threatOtherField.setDisable(true);

            evidenceBox.setValue(null);
            uploadVideoBtn.setDisable(true);
            uploadPhotoBtn.setDisable(true);
            uploadVideoBtn.setText("Upload Video");
            uploadPhotoBtn.setText("Upload Photo");
            videoFileHolder[0] = null;
            photoFileHolder[0] = null;
        });

        ScrollPane scrollPane = new ScrollPane(formGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        Scene scene = new Scene(scrollPane, 750, 700);
        Stage stage = new Stage();
        stage.setTitle("Report Drug Offence");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}