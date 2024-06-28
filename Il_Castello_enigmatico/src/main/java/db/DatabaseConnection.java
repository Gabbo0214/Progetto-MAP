package db;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * La classe DatabaseConnection gestisce la connessione al database.
 */
@SpringBootApplication
public class DatabaseConnection {
    /**
     * Il driver JDBC.
     */
    static final String JDBC_DRIVER = "org.h2.Driver";

    /**
     * L'URL del database.
     */
    static final String DB_URL;

    /**
     * L'utente del database.
     */
    static final String USER = "sa";

    /**
     * La password del database.
     */
    static final String PASS = "";

    static {
        // Costruisci il percorso relativo per il database
        String projectDir = System.getProperty("user.dir");
        String relativeDbPath = Paths.get(projectDir, "Il_Castello_enigmatico", "src", "main", "java", "db", "db_map")
                .toString();
        DB_URL = "jdbc:h2:" + relativeDbPath;
    }

    /**
     * Connette al database.
     *
     * @return la connessione al database
     */
    public static Connection connect() {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            initializeDatabase(conn);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inizializza il database eseguendo script SQL.
     *
     * @param conn la connessione al database
     */
    private static void initializeDatabase(Connection conn) {
        // Costruisci i percorsi relativi per gli script SQL
        String projectDir = System.getProperty("user.dir");
        String startScriptPath = Paths
                .get(projectDir, "Il_Castello_enigmatico", "src", "main", "resources", "database", "db_start.sql")
                .toString();
        String fillScriptPath = Paths
                .get(projectDir, "Il_Castello_enigmatico", "src", "main", "resources", "database", "db_info.sql")
                .toString();

        String start = "RUNSCRIPT FROM '" + startScriptPath + "'";
        String fill = "RUNSCRIPT FROM '" + fillScriptPath + "'";

        try {
            try (PreparedStatement stmt = conn.prepareStatement(start)) {
                stmt.execute();
            }

            String sql = "SELECT COUNT(*) AS count FROM CLASSIFICA";
            boolean isClassificaEmpty = true;

            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    isClassificaEmpty = rs.getInt("count") == 0;
                }
            }

            if (isClassificaEmpty) {
                try (PreparedStatement stmt = conn.prepareStatement(fill)) {
                    stmt.execute();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Chiude la connessione al database.
     *
     * @param conn la connessione al database
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Ottiene la classifica dal database.
     */
    public static List<String> printClassificaFromDB() {
        String sql_query = "SELECT USERNAME, TEMPO FROM CLASSIFICA ORDER BY TEMPO";
        List<String> classifica = new ArrayList<>();
        try (Connection conn = DatabaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql_query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("USERNAME");
                Time tempo = rs.getTime("TEMPO");
                classifica.add(username + " - " + tempo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la lettura della classifica dal database", e);
        }
        return classifica;
    }

    public static void main(String[] args) {

        // Connetti al database e inizializza
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn != null) {
                RESTOperation restOperation = new RESTOperation();
                restOperation.deleteAfterTop10();
                // Database inizializzato con successo
                System.out.println("Database inizializzato con successo.");

                // Ottieni la classifica dal database
                List<String> classifica = DatabaseConnection.printClassificaFromDB();

                // Stampa la classifica
                System.out.println("Classifica:");
                for (String record : classifica) {
                    System.out.println(record);
                }
            } else {
                System.err.println("Connessione al database non riuscita.");
            }
        } catch (Exception e) {
            // Stampa l'intero stack trace dell'eccezione per debugging
            e.printStackTrace();
            System.err.println("Errore durante l'inizializzazione del database: " + e.getMessage());
        }
    }
}