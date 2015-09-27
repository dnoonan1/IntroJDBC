package booksample;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author dnoonan1
 */
public class CreateTest {
    
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        
        MySqlDatabase db = new MySqlDatabase("jdbc:mysql://localhost:3306/book",
            "root", "admin");
        
        System.out.println("Running Create test (Crud) ...");
        
        db.openConnection();
        
        List<Map<String,Object>> records = db.getAllRecords("author");
        
        for (Map record : records) {
            System.out.println(record);
        }
        
        System.out.println("\nCreating new record...");
        
        List<String> columns = Arrays.asList("author_name", "date_added");
        List values = Arrays.asList("Nick Nobody", "2015-01-03");
        
        boolean success = db.insertRecord("author", columns, values);
        
        if (success) {
            System.out.println("Record created.");
        } else {
            System.out.println("Record NOT created.");
        }
        
        records = db.getAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }
        
        db.closeConnection();
    }
    
}
