package booksample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author dnoonan1
 */
public class RetrieveTest {
    
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        
        MySqlDatabase db = new MySqlDatabase("jdbc:mysql://localhost:3306/book",
            "root", "admin");
        
        System.out.println("Running Retrieve test (cRud) ...");
        
        db.openConnection();
        
        List<Map<String,Object>> records = db.getAllRecords("author");
        
        for (Map record : records) {
            System.out.println(record);
        }
        
        System.out.println("Finding author by ID...");
        
        Map<String, Object> record = db.getByPrimaryKey("author", "author_id", 1);
        if (record == null) {
            System.out.println("Author not found.");
        } else {
            System.out.println(record);
        }
        
        db.closeConnection();
    }
    
}
