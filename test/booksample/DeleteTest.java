package booksample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author dnoonan1
 */
public class DeleteTest {
    
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        
        MySqlDatabase db = new MySqlDatabase("jdbc:mysql://localhost:3306/book",
            "root", "admin");
        
        System.out.println("Running Delete test (cruD) ...");
        
        db.openConnection();
        
        List<Map<String,Object>> records = db.getAllRecords("book");
        
        for (Map record : records) {
            System.out.println(record);
        }
        
        System.out.println("\nDeleting record...");
        
        boolean success = db.psDeleteByPrimaryKey("book", "book_id", 3);
        
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
