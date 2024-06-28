package db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RESTOperation {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DeleteMapping("/delete-after-top10")
    public String deleteAfterTop10() {
        String deleteQuery = """
            DELETE FROM CLASSIFICA
            WHERE ID NOT IN (
                SELECT ID
                FROM (
                    SELECT ID
                    FROM CLASSIFICA
                    ORDER BY TEMPO
                    LIMIT 10
                ) AS top10
            );
        """;

        try {
            int rowsAffected = jdbcTemplate.update(deleteQuery);
            return "Records deleted successfully: " + rowsAffected;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}