package src.main;

import java.sql.*;

public  class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:identifier.sqlite";


    public static void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "gender TEXT NOT NULL," +
                "division TEXT NOT NULL," +
                "district TEXT NOT NULL," +
                "thana TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "phone TEXT NOT NULL" +
                ")";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createFraudTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS fraud_reports (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "father_name TEXT NOT NULL," +
                    "mother_name TEXT NOT NULL," +
                    "complainant_phone TEXT NOT NULL," +
                    "location TEXT NOT NULL," +
                    "incident_date TEXT NOT NULL," +
                    "incident_time TEXT NOT NULL," +
                    "description TEXT NOT NULL," +
                    "nid_or_bc TEXT NOT NULL," +
                    "photo_path TEXT," +

                    // Accused info
                    "accused_name TEXT," +
                    "accused_phone TEXT," +
                    "accused_email TEXT," +
                    "accused_address TEXT," +

                // Fraud specific
                "fraud_type TEXT NOT NULL," +
                "mode_of_communication TEXT NOT NULL," +  // multiple modes stored as comma-separated string
                "transaction_involved TEXT," +  // "Yes" or "No"
                "transaction_amount TEXT," +  // stored as TEXT for flexibility, or could be REAL
                "transaction_method TEXT," +
                "supporting_documents TEXT," +  // comma-separated list
                "accused_promise TEXT," +  // "Yes" or "No"
                "previously_reported TEXT," +  // "Yes" or "No"
                "police_action TEXT" +  // detailed description
                ")";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Fraud report table created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createKidnappingTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS kidnapping_reports (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +

                // Basic info from your earlier example
                "name TEXT NOT NULL," +
                "father_name TEXT NOT NULL," +
                "mother_name TEXT NOT NULL," +
                "complainant_phone TEXT NOT NULL," +
                "location TEXT NOT NULL," +
                "incident_date TEXT NOT NULL," +
                "incident_time TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "nid_or_bc TEXT NOT NULL," +
                "photo_path TEXT," +

                // Accused info
                "accused_name TEXT," +
                "accused_phone TEXT," +
                "accused_email TEXT," +
                "accused_address TEXT," +

                // Victim information
                "victim_age INTEGER," +
                "victim_gender TEXT," +
                "victim_height TEXT," +
                "victim_clothing TEXT," +
                "victim_marks TEXT," +

                // Last known information
                "last_known_location TEXT," +
                "last_seen_time TEXT," +
                "last_known_activity TEXT," +

                // Suspected kidnapper info
                "kidnapper_known TEXT," +          // "Yes, known person", "No, stranger", "Unsure"
                "kidnapper_description TEXT," +
                "kidnapper_relationship TEXT," +  // relationship to victim

                // Witness info
                "witness_available TEXT," +        // "Yes", "No", "Not sure"
                "witness_details TEXT," +

                // Emergency alert
                "amber_alert TEXT," +              // "Yes, urgent alert needed", "No, not necessary", "Let police decide"
                "urgency_level TEXT," +

                // Ransom info
                "ransom_demand TEXT," +            // "Yes", "No"
                "ransom_communication TEXT," +
                "ransom_amount TEXT," +

                // Additional info
                "previous_report TEXT," +          // "Yes, reported earlier", "No, first report"
                "motive_financial INTEGER," +      // 0 or 1 for checkbox
                "motive_revenge INTEGER," +
                "motive_family INTEGER," +
                "motive_political INTEGER," +
                "motive_unknown INTEGER," +
                "motive_other INTEGER," +
                "action_requested TEXT" +

                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Kidnapping reports table created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertKidnappingReport(
            String name,
            String fatherName,
            String motherName,
            String complainantPhone,
            String location,
            String incidentDate,
            String incidentTime,
            String description,
            String nidOrBc,
            String photoPath,

            String accusedName,
            String accusedPhone,
            String accusedEmail,
            String accusedAddress,

            Integer victimAge,
            String victimGender,
            String victimHeight,
            String victimClothing,
            String victimMarks,

            String lastKnownLocation,
            String lastSeenTime,
            String lastKnownActivity,

            String kidnapperKnown,
            String kidnapperDescription,
            String kidnapperRelationship,

            String witnessAvailable,
            String witnessDetails,

            String amberAlert,
            String urgencyLevel,

            String ransomDemand,
            String ransomCommunication,
            String ransomAmount,

            String previousReport,
            int motiveFinancial,
            int motiveRevenge,
            int motiveFamily,
            int motivePolitical,
            int motiveUnknown,
            int motiveOther,
            String actionRequested
    ) {
        String sql = "INSERT INTO kidnapping_reports (" +
                "name, father_name, mother_name, complainant_phone, location, incident_date, incident_time, description, nid_or_bc, photo_path," +
                "accused_name, accused_phone, accused_email, accused_address," +
                "victim_age, victim_gender, victim_height, victim_clothing, victim_marks," +
                "last_known_location, last_seen_time, last_known_activity," +
                "kidnapper_known, kidnapper_description, kidnapper_relationship," +
                "witness_available, witness_details," +
                "amber_alert, urgency_level," +
                "ransom_demand, ransom_communication, ransom_amount," +
                "previous_report, motive_financial, motive_revenge, motive_family, motive_political, motive_unknown, motive_other," +
                "action_requested" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, fatherName);
            pstmt.setString(3, motherName);
            pstmt.setString(4, complainantPhone);
            pstmt.setString(5, location);
            pstmt.setString(6, incidentDate);
            pstmt.setString(7, incidentTime);
            pstmt.setString(8, description);
            pstmt.setString(9, nidOrBc);
            pstmt.setString(10, photoPath);

            pstmt.setString(11, accusedName);
            pstmt.setString(12, accusedPhone);
            pstmt.setString(13, accusedEmail);
            pstmt.setString(14, accusedAddress);

            if (victimAge != null) pstmt.setInt(15, victimAge); else pstmt.setNull(15, java.sql.Types.INTEGER);
            pstmt.setString(16, victimGender);
            pstmt.setString(17, victimHeight);
            pstmt.setString(18, victimClothing);
            pstmt.setString(19, victimMarks);

            pstmt.setString(20, lastKnownLocation);
            pstmt.setString(21, lastSeenTime);
            pstmt.setString(22, lastKnownActivity);

            pstmt.setString(23, kidnapperKnown);
            pstmt.setString(24, kidnapperDescription);
            pstmt.setString(25, kidnapperRelationship);

            pstmt.setString(26, witnessAvailable);
            pstmt.setString(27, witnessDetails);

            pstmt.setString(28, amberAlert);
            pstmt.setString(29, urgencyLevel);

            pstmt.setString(30, ransomDemand);
            pstmt.setString(31, ransomCommunication);
            pstmt.setString(32, ransomAmount);

            pstmt.setString(33, previousReport);
            pstmt.setInt(34, motiveFinancial);
            pstmt.setInt(35, motiveRevenge);
            pstmt.setInt(36, motiveFamily);
            pstmt.setInt(37, motivePolitical);
            pstmt.setInt(38, motiveUnknown);
            pstmt.setInt(39, motiveOther);

            pstmt.setString(40, actionRequested);

            pstmt.executeUpdate();
            System.out.println("Kidnapping report inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public static boolean updatePasswordByPhone(String phone, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE phone = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, phone);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean validateLogin(String phone, String password) {
        String sql = "SELECT * FROM users WHERE phone = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, phone);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // returns true if a matching row is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public static void insertUser(String firstName, String lastName, String gender,
                                  String division, String district, String thana,
                                  String password , String phone) {
        String sql = "INSERT INTO users(first_name, last_name, gender, division, district, thana, password , phone) VALUES(?, ?, ?, ?, ?, ?, ? , ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, gender);
            pstmt.setString(4, division);
            pstmt.setString(5, district);
            pstmt.setString(6, thana);
            pstmt.setString(7, password);
            pstmt.setString(8, phone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertFraudReport(
            String name,
            String fatherName,
            String motherName,
            String complainantPhone,
            String location,
            String incidentDate,
            String incidentTime,
            String description,
            String nidOrBc,
            String photoPath,
            String accusedName,
            String accusedPhone,
            String accusedEmail,
            String accusedAddress,
            String fraudType,
            String modeOfCommunication,     // comma-separated string of modes
            String transactionInvolved,     // "Yes" or "No"
            String transactionAmount,
            String transactionMethod,
            String supportingDocuments,     // comma-separated string of docs
            String accusedPromise,          // "Yes" or "No"
            String previouslyReported,      // "Yes" or "No"
            String policeAction
    ) {
        String sql = "INSERT INTO fraud_reports (" +
                "name, father_name, mother_name, complainant_phone, location, incident_date, incident_time, description, nid_or_bc, photo_path, " +
                "accused_name, accused_phone, accused_email, accused_address, fraud_type, mode_of_communication, transaction_involved, transaction_amount, " +
                "transaction_method, supporting_documents, accused_promise, previously_reported, police_action) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, fatherName);
            pstmt.setString(3, motherName);
            pstmt.setString(4, complainantPhone);
            pstmt.setString(5, location);
            pstmt.setString(6, incidentDate);
            pstmt.setString(7, incidentTime);
            pstmt.setString(8, description);
            pstmt.setString(9, nidOrBc);
            pstmt.setString(10, photoPath);

            pstmt.setString(11, accusedName);
            pstmt.setString(12, accusedPhone);
            pstmt.setString(13, accusedEmail);
            pstmt.setString(14, accusedAddress);

            pstmt.setString(15, fraudType);
            pstmt.setString(16, modeOfCommunication);
            pstmt.setString(17, transactionInvolved);
            pstmt.setString(18, transactionAmount);
            pstmt.setString(19, transactionMethod);
            pstmt.setString(20, supportingDocuments);
            pstmt.setString(21, accusedPromise);
            pstmt.setString(22, previouslyReported);
            pstmt.setString(23, policeAction);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Fraud report inserted successfully.");
            } else {
                System.out.println("Failed to insert fraud report.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
