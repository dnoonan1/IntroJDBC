package booksample;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author dnoonan1
 */
public class UpdateTest {
    
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        
        MySqlDatabase db = new MySqlDatabase("jdbc:mysql://localhost:3306/book",
            "root", "admin");
        
        System.out.println("Running Update test (crUd) ...");
        
        db.openConnection();
        
        List<Map<String,Object>> records = db.getAllRecords("author");
        
        for (Map record : records) {
            System.out.println(record);
        }
        
        System.out.println("\nUpdating record...");
        
        List<String> columns = Arrays.asList("author_id", "author_name", "date_added");
        List values = Arrays.asList(3, "Nick Somebody", "2015-11-01");
        
        boolean success = db.updateRecord("author", columns, values, "author_id", 3);
        
        if (success) {
            System.out.println("Record updated.");
        } else {
            System.out.println("Record NOT updated.");
        }
        
        records = db.getAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }
        
        db.closeConnection();
    }
    
}
