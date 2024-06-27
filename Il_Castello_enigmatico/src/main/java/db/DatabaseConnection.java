package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * La classe DatabaseConnection gestisce la connessione al database.
 */
public class DatabaseConnection {

    /**
     * Il driver JDBC.
     */
    static final String JDBC_DRIVER = "org.h2.Driver";
    /**
     * L'URL del database.
     */
    static final String DB_URL = "jdbc:h2:./src/main/resources/database/db_map";

    /**
     * L'utente del database.
     */
    static final String USER = "sa";
    /**
     * La password del database.
     */
    static final String PASS = "";

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
        String start = "RUNSCRIPT FROM 'Il_Castello_enigmatico/src/main/resources/database/db_start.sql'";
        String fill = "RUNSCRIPT FROM 'Il_Castello_enigmatico/src/main/resources/database/db_info.sql'";

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
     * Stampa la classifica dal database.
     */
    public static void printClassificaFromDB() {
        String sql_query = "SELECT * FROM CLASSIFICA ORDER BY TEMPO";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql_query);
             ResultSet rs = stmt.executeQuery()) {

            StringBuilder classifica = new StringBuilder();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String username = rs.getString("USERNAME");
                Time tempo = rs.getTime("TEMPO");
                classifica.append("ID: ").append(id).append(", Username: ").append(username).append(", Tempo: ").append(tempo).append("\n");
            }
            //printdb.displayText(classifica.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}