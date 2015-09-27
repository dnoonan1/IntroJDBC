package booksample;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author Dan Noonan
 */
public class DAOTest {
   
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        
        Database db = new MySqlDatabase("jdbc:mysql://localhost:3306/book",
            "root", "admin");
        DAO<Author> dao = new AuthorDAO(db);
        
        // List all authors
        List<Author> authors = dao.getAll();
        printList(authors);
        
        // Add a new author
        System.out.println("Adding new author...");
        Author author = new Author("Jim Jones", new Date());
        dao.add(author);
        
        // List authors again
        authors = dao.getAll();
        printList(authors);
        
        // Update author
        System.out.println("Updating author...");
        author = dao.getById(4);
        author.setAuthorName("Jim von Jones");
        dao.save(author);
        
        authors = dao.getAll();
        printList(authors);
        
        // Delete author
        System.out.println("Deleting author(s)...");
        dao.deleteById(6);
        dao.deleteById(5);
        
        authors = dao.getAll();
        printList(authors);
        
    }
    
    private static void printList(List list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }
    
}
