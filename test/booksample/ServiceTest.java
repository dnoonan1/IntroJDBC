package booksample;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author Dan Noonan
 */
public class ServiceTest {
    
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        
        Database db = new MySqlDatabase("jdbc:mysql://localhost:3306/book",
            "root", "admin");
        DAO<Author> dao = new AuthorDAO(db);
        AuthorService as = new AuthorService(dao);
        List<Author> authors = as.getAllAuthors();
        printList(authors);
        
        // Add new author
        as.addAuthor(new Author("Bob", new Date()));
        authors = as.getAllAuthors();
        printList(authors);
        
        // Update an author
        Author author = as.getAuthorById(13);
        if (author != null) {
            author.setAuthorName("Rob");
            as.saveAuthor(author);
            authors = as.getAllAuthors();
            printList(authors);
            // Delete an author
            as.deleteAuthor(author);
            authors = as.getAllAuthors();
            printList(authors);
        }
        
    }
    
    private static void printList(List list) {
        for (Object item : list) {
            System.out.println(item);
        }
        System.out.println();
    }
    
}
