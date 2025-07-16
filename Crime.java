package org.example.java;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.example.java.UserDashboard;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public abstract class Crime extends UserDashboard {
    private static final Map<String, String[]> divisionToDistricts = new HashMap<>();
    private static final Map<String, String[]> districtToThanas   = new HashMap<>();

static {
    divisionToDistricts.put("Dhaka", new String[]{"Dhaka", "Faridpur", "Gazipur", "Gopalganj", "Kishoreganj", "Madaripur", "Manikganj", "Munshiganj", "Narayanganj", "Narsingdi", "Rajbari", "Shariatpur", "Tangail"});
    divisionToDistricts.put("Chittagong", new String[]{"Bandarban", "Brahmanbaria", "Chandpur", "Chittagong", "Comilla", "Cox's Bazar", "Feni", "Khagrachhari", "Lakshmipur", "Noakhali", "Rangamati"});
    divisionToDistricts.put("Rajshahi", new String[]{"Bogra", "Joypurhat", "Naogaon", "Natore", "Chapainawabganj", "Pabna", "Rajshahi", "Sirajganj"});
    divisionToDistricts.put("Khulna", new String[]{"Bagerhat", "Chuadanga", "Jessore", "Jhenaidah", "Khulna", "Kushtia", "Magura", "Meherpur", "Narail", "Satkhira"});
    divisionToDistricts.put("Sylhet", new String[]{"Habiganj", "Moulvibazar", "Sunamganj", "Sylhet"});
    divisionToDistricts.put("Barisal", new String[]{"Barguna", "Barisal", "Bhola", "Jhalokati", "Patuakhali", "Pirojpur"});
    divisionToDistricts.put("Rangpur", new String[]{"Dinajpur", "Gaibandha", "Kurigram", "Lalmonirhat", "Nilphamari", "Panchagarh", "Rangpur", "Thakurgaon"});
    divisionToDistricts.put("Mymensingh", new String[]{"Jamalpur", "Mymensingh", "Netrakona", "Sherpur"});

    // Initialize district to thanas mapping (sample data - you can expand this)
    districtToThanas.put("Dhaka", new String[]{"Dhanmondi", "Gulshan", "Ramna", "Tejgaon", "Pallabi", "Shah Ali", "Turag", "Dakshinkhan", "Uttara", "Savar", "Keraniganj", "Dohar", "Nawabganj", "Dhamrai"});
    districtToThanas.put("Chittagong", new String[]{"Kotwali", "Panchlaish", "Double Mooring", "Pahartali", "Bayezid", "Chandgaon", "Karnaphuli", "Banshkhali", "Boalkhali", "Anwara", "Chandanaish", "Satkania", "Lohagara", "Hathazari", "Raozan", "Rangunia", "Sandwip", "Sitakunda", "Mirsharai", "Fatikchhari", "Patiya"});
    districtToThanas.put("Sylhet", new String[]{"Sylhet Sadar", "South Surma", "Balaganj", "Beanibazar", "Bishwanath", "Companiganj", "Dakshin Surma", "Fenchuganj", "Golapganj", "Gowainghat", "Jaintiapur", "Kanaighat", "Osmani Nagar", "Zakiganj"});
    districtToThanas.put("Rajshahi", new String[]{"Boalia", "Matihar", "Rajpara", "Shah Makhdum", "Bagha", "Bagmara", "Charghat", "Durgapur", "Godagari", "Mohanpur", "Paba", "Puthia", "Tanore"});
    districtToThanas.put("Khulna", new String[]{"Khulna Sadar", "Sonadanga", "Khalishpur", "Doulatpur", "Kotwali", "Aranghata", "Batiaghata", "Dacope", "Dumuria", "Dighalia", "Koyra", "Paikgachha", "Phultala", "Rupsa", "Terokhada"});
    districtToThanas.put("Barisal", new String[]{"Barisal Sadar", "Kotwali", "Bakerganj", "Babuganj", "Wazirpur", "Banaripara", "Gournadi", "Agailjhara", "Mehendiganj", "Muladi", "Hizla"});
    districtToThanas.put("Rangpur", new String[]{"Rangpur Sadar", "Kotwali", "Badarganj", "Gangachara", "Kaunia", "Mithapukur", "Pirgachha", "Pirganj", "Taraganj"});
    districtToThanas.put("Mymensingh", new String[]{"Mymensingh Sadar", "Kotwali", "Bhaluka", "Dhobaura", "Fulbaria", "Gaffargaon", "Gouripur", "Haluaghat", "Ishwarganj", "Muktagachha", "Nandail", "Phulpur", "Trishal"});
    districtToThanas.put("Jamalpur",new String[]{"Bokshiganj Thana", " Dewanganj Thana", "Islampur Thana", "Jamalpur Sadar Thana","Madarganj Thana","Melandaha Thana","Sarishabari Thana"});
    districtToThanas.put("Sherpur", new String[]{"Sherpur Sadar Thana","Nalitabari Thana","Sreebardi Thana","Jhenaigati Thana","Nakla Thana"});
    districtToThanas.put("Netrokona", new String[]{"Atpara Thana","Barhatta Thana","Durgapur Thana","Khaliajuri Thana","Kalmakanda Thana","Kendua Thana","Madan Thana","Mohanganj Thana","Netrokona Sadar Thana","Purbadhala Thana"});

    // Add more districts and thanas as needed
    districtToThanas.put("Gazipur", new String[]{"Gazipur Sadar", "Kaliakair", "Kapasia", "Sreepur", "Kaliganj", "Tongi"});
    districtToThanas.put("Narayanganj", new String[]{"Narayanganj Sadar", "Araihazar", "Bandar", "Rupganj", "Sonargaon"});
    districtToThanas.put("Tangail", new String[]{"Tangail Sadar", "Sakhipur", "Basail", "Madhupur", "Ghatail", "Kalihati", "Nagarpur", "Mirzapur", "Gopalpur", "Delduar", "Bhuapur", "Dhanbari"});
    districtToThanas.put("Kishoreganj", new String[]{"Kuliarchar Thana","Hossainpur Thana","Pakundia Thana","Kishoreganj Sadar Thana","Bajitpur Thana","Austagram Thana","Karimganj Thana","Katiadi Thana","Tarail Thana","Itna Thana","Nikli Thana","Mithamain Thana","Bhairab Thana"});
    districtToThanas.put("Faridpur", new String[]{"Alfadanga  ","Bhanga  ","Boalmari  ","Charbhadrasan  ","Faridpur Sadar  ","Madhukhali  ","Nagarkanda  ","Sadarpur  ","Saltha  "});
    districtToThanas.put("Madaripur", new String[]{"Madaripur Sadar  ","Kalkini  ","Rajoir  ","Shibchar  "});
    districtToThanas.put("Manikganj", new String[]{"Daulatpur  ","Ghior  ","Harirampur  ","Manikganj Sadar  ","Saturia  ","Shivalaya  ","Singair  "});
    districtToThanas.put("Munshiganj", new String[]{"Munshiganj Sadar  ","Sreenagar  ","Sirajdikhan  ","Lohajang  ","Gazaria  ","Tongibari  "});
    districtToThanas.put("Rajbari", new String[]{"Baliakandi  ","Goalanda  ","Kalukhali  ","Pangsha  ","Rajbari Sadar  "});
    districtToThanas.put("Shariatpur", new String[]{"Shariatpur Sadar  ","Bhedarganj  ","Damudya  ","Gosairhat  ","Naria  ","Zanjira  "});
    districtToThanas.put("Gopalganj",   new String[]{"Gopalganj Sadar   ","Kashiani   ","Kotalipara   ","Muksudpur   ","Tungipara   "});
    districtToThanas.put("Narsingdi",   new String[]{"Narsingdi Sadar   ","Belabo   ","Monohardi   ","Palash   ","Raipura   ","Shibpur   "});

    districtToThanas.put("Bandarban",    new String[]{"Ali Kadam   ","Bandarban Sadar   ","Lama   ","Naikhongchhari   ","Rowangchhari   ","Ruma   ","Thanchi   "});  // 7   s :contentReference[oaicite:0]{index=0}
    districtToThanas.put("Brahmanbaria", new String[]{"Akhaura   ","Bancharampur   ","Brahmanbaria Sadar   ","Kasba   ","Nabinagar   ","Nasirnagar   ","Sarail   ","Ashuganj   "});  // 8   s :contentReference[oaicite:1]{index=1}
    districtToThanas.put("Chandpur",     new String[]{"Chandpur Sadar   ","Faridganj   ","Haimchar   ","Haziganj   ","Kachua   ","Matlab    (North)","Matlab    (South)","Shahrasti   "});  // 8   s :contentReference[oaicite:2]{index=2}
    districtToThanas.put("Comilla",      new String[]{"Barura   ","Brahmanpara   ","Burichong   ","Chandina   ","Chauddagram   ","Daudkandi   ","Debidwar   ","Homna   ","Comilla Sadar Adarsha   ","Comilla Sadar South   ","Laksam   ","Muradnagar   ","Nangalkot   ","Titas   ","Meghna   ","Monoharganj   "});
    districtToThanas.put("Cox's Bazar",  new String[]{"Chakaria   ","Cox’s Bazar Sadar   ","Kutubdia   ","Maheshkhali   ","Ramu   ","Teknaf   ","Ukhia   ","Pekua   "});  // 8   s :contentReference[oaicite:5]{index=5}
    districtToThanas.put("Feni",         new String[]{"Chhagalnaiya   ","Daganbhuiyan   ","Feni Sadar   ","Parshuram   ","Sonagazi   ","Fulgazi   "});  // 6   s :contentReference[oaicite:6]{index=6}
    districtToThanas.put("Khagrachhari", new String[]{"Dighinala   ","Khagrachhari   ","Lakshmichhari   ","Mahalchhari   ","Manikchhari   ","Matiranga   ","Panchhari   ","Ramgarh   "});  // 8   s :contentReference[oaicite:7]{index=7}
    districtToThanas.put("Lakshmipur",   new String[]{"Lakshmipur Sadar   ","Raipur   ","Ramganj   ","Ramgati   "});  // 4   s :contentReference[oaicite:8]{index=8}
    districtToThanas.put("Noakhali",     new String[]{"Begumganj   ","Chatkhil   ","Companiganj   ","Hatiya   ","Senbagh   ","Noakhali Sadar   ","Subarnachar   "});  // 7   s :contentReference[oaicite:9]{index=9}
    districtToThanas.put("Rangamati",    new String[]{"Bagaichhari   ","Barkal   ","Kawkhali (Betbunia)   ","Belaichhari   ","Kaptai   ","Juraichhari   ","Langadu   ","Mannerchar   ","Rajasthali   ","Rangamati Sadar   "});  // 10   s :contentReference[oaicite:10]{index=10}


    districtToThanas.put("Bogra",           new String[]{"Bogra Sadar   ","Adamdighi   ","Dhunat   ","Dhupchanchia   ","Gabtali   ","Kahaloo   ","Nandigram   ","Sariakandi   ","Shajahanpur   ","Sherpur   ","Shibganj   ","Sonatala   "});
    districtToThanas.put("Joypurhat",       new String[]{"Joypurhat Sadar   ","Akkelpur   ","Kalai   ","Khetlal   ","Panchbibi   "});
    districtToThanas.put("Naogaon",         new String[]{"Naogaon Sadar   ","Atrai   ","Badalgachhi   ","Dhamoirhat   ","Manda   ","Mohadevpur   ","Niamatpur   ","Patnitala   ","Porsha   ","Raninagar   ","Sapahar   "});
    districtToThanas.put("Natore",          new String[]{"Natore Sadar   ","Bagatipara   ","Baraigram   ","Gurudaspur   ","Lalpur   ","Naldanga   ","Singra   "});
    districtToThanas.put("Chapai Nawabganj",new String[]{"Chapainawabganj Sadar   ","Gomastapur   ","Nachole   ","Bholahat   ","Shibganj   "});
    districtToThanas.put("Pabna",           new String[]{"Pabna Sadar   ","Atgharia   ","Bera   ","Bhangura   ","Chatmohar   ","Faridpur   ","Ishwardi   ","Santhia   ","Sujanagar   "});
    districtToThanas.put("Sirajganj",        new String[]{"Sirajganj Sadar   ","Kazipur   ","Ullahpara   ","Shahjadpur   ","Raiganj   ","Kamarkhanda   ","Tarash   ","Belkuchi   ","Chauhali   "});


    districtToThanas.put("Bagerhat",   new String[]{"Bagerhat Sadar   ","Chitalmari   ","Fakirhat   ","Kachua   ","Mollahat   ","Mongla   ","Morrelganj   ","Rampal   ","Sarankhola   "});  // :contentReference[oaicite:0]{index=0}
    districtToThanas.put("Chuadanga", new String[]{"Chuadanga Sadar   ","Alamdanga   ","Jibannagar   ","Damurhuda   "});  // :contentReference[oaicite:1]{index=1}
    districtToThanas.put("Jessore",    new String[]{"Abhaynagar   ","Bagherpara   ","Chaugachha   ","Jessore Sadar   ","Jhikargachha   ","Keshabpur   ","Manirampur   ","Sharsha   "});  // :contentReference[oaicite:2]{index=2}
    districtToThanas.put("Jhenaidah",  new String[]{"Jhenaidah Sadar   ","Maheshpur   ","Kaliganj   ","Kotchandpur   ","Shailkupa   ","Harinakunda   "});  // :contentReference[oaicite:3]{index=3}
    districtToThanas.put("Kushtia",    new String[]{"Kushtia Sadar   ","Kumarkhali   ","Khoksa   ","Mirpur   ","Bheramara   ","Daulatpur   "});  // :contentReference[oaicite:4]{index=4}
    districtToThanas.put("Magura",     new String[]{"Magura Sadar   ","Mohammadpur   ","Shalikha   ","Sreepur   "});  // :contentReference[oaicite:5]{index=5}
    districtToThanas.put("Meherpur",   new String[]{"Meherpur Sadar   ","Gangni   ","Mujibnagar   "});  // :contentReference[oaicite:6]{index=6}
    districtToThanas.put("Narail",     new String[]{"Narail Sadar   ","Kalia   ","Lohagara   "});  // :contentReference[oaicite:7]{index=7}
    districtToThanas.put("Satkhira",   new String[]{"Satkhira Sadar   ","Assasuni   ","Debhata   ","Tala   ","Kalaroa   ","Kaliganj   ","Shyamnagar   "});  // :contentReference[oaicite:8]{index=8}


    districtToThanas.put("Habiganj",    new String[]{"Habiganj Sadar   ","Lakhai   ","Madhabpur   ","Nabiganj   ","Chunarughat   ","Baniachang   ","Bahubal   ","Ajmiriganj   "});  // 8   s
    districtToThanas.put("Moulvibazar", new String[]{"Moulvibazar Sadar   ","Sreemangal   ","Kulaura   ","Kamalganj   ","Juri   ","Barlekha   ","Rajnagar   "});         // 7   s
    districtToThanas.put("Sunamganj",   new String[]{"Sunamganj Sadar   ","Dakshin Sunamganj   ","Chhatak   ","Jagannathpur   ","Bishwamvarpur   ","Tahirpur   ","Derai   ","Dharampasha   ","Dowarabazar   ","Sulla   ","Jamalganj   "});  // 11   s :contentReference[oaicite:2]{index=2}


    districtToThanas.put("Barguna",    new String[]{"Barguna Sadar Upazila","Amtali Upazila","Bamna Upazila","Betagi Upazila","Patharghata Upazila","Taltali Upazila"});        // 6 upazilas :contentReference[oaicite:0]{index=0}
    districtToThanas.put("Bhola",      new String[]{"Bhola Sadar Upazila","Daulatkhan Upazila","Borhanuddin Upazila","Lalmohan Upazila","Tazumuddin Upazila","Manpura Upazila","Charfashion Upazila"}); // 7 upazilas :contentReference[oaicite:1]{index=1}
    districtToThanas.put("Jhalokati",  new String[]{"Jhalokati Sadar Upazila","Kathalia Upazila","Nalchity Upazila","Rajapur Upazila"});                                        // 4 upazilas :contentReference[oaicite:2]{index=2}
    districtToThanas.put("Patuakhali", new String[]{"Patuakhali Sadar Upazila","Dumki Upazila","Dashmina Upazila","Bauphal Upazila","Mirzaganj Upazila","Galachipa Upazila","Kalapara Upazila","Rangabali Upazila"}); // 8 upazilas :contentReference[oaicite:3]{index=3}
    districtToThanas.put("Pirojpur",   new String[]{"Pirojpur Sadar Upazila","Bhandaria Upazila","Mathbaria Upazila","Indurkani Upazila","Nazirpur Upazila","Nesarabad Upazila","Kawkhali Upazila"});                // 7 upazilas :contentReference[oaicite:4]{index=4}

    districtToThanas.put("Dinajpur",   new String[]{"Dinajpur Sadar Upazila","Birampur Upazila","Bochaganj Upazila","Birol Upazila","Chirirbandar Upazila","Ghoraghat Upazila","Hakimpur Upazila","Kaharole Upazila","Khansama Upazila","Parbatipur Upazila"});
    districtToThanas.put("Gaibandha",  new String[]{"Gaibandha Sadar Upazila","Palashbari Upazila","Gobindaganj Upazila","Saghata Upazila","Sadullapur Upazila","Fulchhari Upazila","Sundarganj Upazila"});
    districtToThanas.put("Kurigram",   new String[]{"Kurigram Sadar Upazila","Nageshwari Upazila","Phulbari Upazila","Ulipur Upazila","Chilmari Upazila","Rowmari Upazila","Rajarhat Upazila","Bhurungamari Upazila","Char Rajibpur Upazila"});
    districtToThanas.put("Lalmonirhat",new String[]{"Lalmonirhat Sadar Upazila","Aditmari Upazila","Hatibandha Upazila","Patgram Upazila","Kaliganj Upazila"});
    districtToThanas.put("Nilphamari", new String[]{"Nilphamari Sadar Upazila","Domar Upazila","Jaldhaka Upazila","Dimla Upazila","Saidpur Upazila","Kishoreganj Upazila"});
    districtToThanas.put("Panchagarh", new String[]{"Panchagarh Sadar Upazila","Atwari Upazila","Boda Upazila","Debiganj Upazila","Tetulia Upazila"});
    districtToThanas.put("Thakurgaon", new String[]{"Thakurgaon Sadar Upazila","Baliadangi Upazila","Haripur Upazila","Pirganj Upazila","Ranisankail Upazila"});

}

static TextField descriptionField;
static DatePicker datePicker ;
static TextField timeField;
static TextField nameField;
static TextField NIDField;
static  TextArea descriptionArea;
   static ComboBox<String> thanaCombo = new ComboBox<>();



    private static boolean validateForm() {
        StringBuilder errors = new StringBuilder();

        if (thanaCombo.getValue() == null) {
            errors.append("• Thana is required\n");
        }
        if (nameField.getText().trim().isEmpty()) {
            errors.append("• Name is required\n");
        }


        // Validate address
        if (datePicker.getValue() == null) {
            errors.append("•  Appropriate Date is required\n");
        }

        // Validate password
        if (NIDField.getText().isEmpty()) {
            errors.append("• NID is required\n");
        } else if (timeField.getText().isEmpty() ) {
            errors.append("• Enter Appropriate Time\n");
        }

        if (errors.length() > 0) {
            showErrorMessage(errors.toString());
            return false;
        }


        return true;
    }
    private     static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("❌ Validation Error");
        alert.setHeaderText("Please fix the following errors:");
        alert.setContentText(message);

        // Style the alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff6b6b, #ee5a52);" +
                        "-fx-text-fill: white;"
        );

        alert.showAndWait();
    }
    public  static void reportCrime(String crimeType) {


        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Report " + crimeType);
        dialog.setHeaderText("Please provide details about the incident");



        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

// **Force column widths to ensure labels are visible**
        ColumnConstraints labelCol = new ColumnConstraints();
        labelCol.setPrefWidth(150); // Wider column for labels
        ColumnConstraints fieldCol = new ColumnConstraints();
        fieldCol.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(labelCol, fieldCol);

// **Create labels with explicit styling (RED color for testing)**
        Label division = new Label("Division ");
        division.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        Label district = new Label("District ");
        district.setStyle("-fx-text-fill: red;  -fx-font-weight: bold;");
        Label thanas = new Label("Thanas ");
        thanas.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        Label dateLabel = new Label("Date:");
        dateLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        Label timeLabel = new Label("Time:");
        timeLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        Label descriptionLabel = new Label("Description:");
        descriptionLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        Label nidLabel = new Label("NID:");
        nidLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

// **Text Fields (unchanged)**
        ComboBox<String> divisionCombo = new ComboBox<>();
        divisionCombo.setPromptText("Division");
        divisionCombo.getItems().addAll(divisionToDistricts.keySet());

        ComboBox<String> districtCombo = new ComboBox<>();
        districtCombo.setPromptText("District");

        thanaCombo.setPromptText("Thana");
        datePicker = new DatePicker();
        timeField = new TextField();
        timeField.setPromptText("Time (e.g., 14:30)");
        descriptionArea = new TextArea();
        descriptionArea.setPromptText("Detailed description of the incident");
        descriptionArea.setPrefRowCount(4);
        nameField = new TextField();
        nameField.setPromptText("Your Name (Complainant)");
        NIDField = new TextField();
        NIDField.setPromptText("NID");

// (You’ll want to populate divisionCombo.getItems() with your divisions,
// then on divisionCombo.setOnAction(...) fill districtCombo based on the selected division,
// and similarly for thanaCombo.)

// 2) Add them to your grid in place of the old location row,
//    and bump all the others down by 2 rows:
        divisionCombo.setOnAction(e -> {
            districtCombo.getItems().clear();
            thanaCombo.getItems().clear();
            String div = divisionCombo.getValue();
            if (div != null) {
                districtCombo.getItems().addAll(divisionToDistricts.get(div));
            }
        });
        districtCombo.setOnAction(e -> {
            thanaCombo.getItems().clear();
            String dist = districtCombo.getValue();
            if (dist != null && districtToThanas.containsKey(dist)) {
                thanaCombo.getItems().addAll(districtToThanas.get(dist));
            }
        });
        grid.add(division, 0, 0);
        grid.add(divisionCombo,           1, 0);
        grid.add(district,  0, 1);
        grid.add(districtCombo,           1, 1);

        grid.add(thanas,     0, 2);
        grid.add(thanaCombo,              1, 2);

// **Add to GridPane (with row/column indexes)**

        grid.add(dateLabel, 0, 3);
        grid.add(datePicker, 1, 3);
        grid.add(timeLabel, 0, 4);
        grid.add(timeField, 1, 4);
        grid.add(descriptionLabel, 0, 5);
        grid.add(descriptionArea, 1, 5);
        grid.add(nameLabel, 0, 6);
        grid.add(nameField, 1, 6);
        grid.add(nidLabel, 0, 7);
        grid.add(NIDField, 1, 7);

// **Debug: Add a visible border to GridPane**
        grid.setStyle("-fx-border-color: blue; -fx-border-width: 2px;");

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Horizontal scroll if needed
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background-color: white;"); // Ensure contrast

        dialog.getDialogPane().setContent(scrollPane);

        if(Objects.equals(crimeType, "Robbery"))
        {

        }
        else if(Objects.equals(crimeType, "Fraud"))
        {

            grid.add(new Label("Description:"), 0, 4);

        }
        else if(Objects.equals(crimeType, "Theft/Burglary"))
        {

        }
        else if(Objects.equals(crimeType, "Assault"))
        {

        }
        else if(Objects.equals(crimeType, "Vandalism"))
        {

        }
        else if(Objects.equals(crimeType , "Drug Offence"))
        {

        }
        else if(Objects.equals(crimeType, "Domestic Violence"))
        {

        }
        else if(Objects.equals(crimeType, "Cybercrime"))
        {

        }
        else if(Objects.equals(crimeType , "Traffic Violation"))
        {

        }
        else if(Objects.equals(crimeType, "Harassment"))
        {

        }
        else if(Objects.equals(crimeType, "Identity Theft"))
        {

        }
        else if(Objects.equals(crimeType, "Shoplifting"))
        {

        }
        else if(Objects.equals(crimeType, "Kidnapping"))
        {

        }
        else if(Objects.equals(crimeType, "Arson"))
        {

        }
        else if(Objects.equals(crimeType, "Stalking"))
        {

        }
        else if(Objects.equals(crimeType, "Embezzlement"))
        {

        }
        else if(Objects.equals(crimeType, "Sexual Assault"))
        {

        }
        else if(Objects.equals(crimeType, "Money Laundering"))
        {

        }
        else if(Objects.equals(crimeType, "Extortion"))
        {

        }
        else if(Objects.equals(crimeType, "Public Disorder"))
        {

        }

        ButtonType submitType = new ButtonType("Submit Report", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitType, ButtonType.CANCEL);

// grab the actual Button Node:
        Button submitButton = (Button) dialog.getDialogPane().lookupButton(submitType);
        submitButton.addEventFilter(
                ActionEvent.ACTION,
                evt -> {
                    if (!validateForm()) {
                        evt.consume();  // stops the dialog from closing
                    }

                }
        );
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitType && validateForm()) {
                return "submitted";
            }
            return null;
        });


        dialog.showAndWait().ifPresent(result -> {
            if ("submitted".equals(result)) {
                // Add to case history
                UserDashboard.CaseRecord newCase = new UserDashboard.CaseRecord(
                        "CR" + System.currentTimeMillis(),
                        crimeType,
                        division.getText(),
                        LocalDateTime.now(),
                        "Under Investigation"
                );


                // Show confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Report Submitted");
                alert.setHeaderText("Your report has been submitted successfully");
                alert.setContentText("Case ID: " + newCase.getCaseId() + "\nYou can track the progress in Case History.");
                alert.showAndWait();
            }
        });
    }

}