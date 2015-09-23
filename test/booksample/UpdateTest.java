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
        
        List<Map<String,Object>> records = db.getAllRecords("book");
        
        for (Map record : records) {
            System.out.println(record);
        }
        
        System.out.println("\nUpdating record...");
        
        List<String> columns = Arrays.asList("book_id", "title", "pub_date");
        List values = Arrays.asList(3, "I Now Have a Title!", "2015-01-01");
        
        boolean success = db.updateRecord("book", columns, values, "book_id", 5);
        
        if (success) {
            System.out.println("Record deleted.");
        } else {
            System.out.println("Record NOT deleted.");
        }
        
        records = db.getAllRecords("book");
        for (Map record : records) {
            System.out.println(record);
        }
        
        db.closeConnection();
    }
    
}
