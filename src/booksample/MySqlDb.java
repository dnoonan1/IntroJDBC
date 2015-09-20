package booksample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dnoonan1
 */
public class MySqlDb {
    private static final String SELECT_ALL_FROM = "SELECT * FROM ";
    private static final String WHERE = " WHERE ";
    private static final String EQUALS = " = ";
    private static final String PARAMETER = "?";
    private static final int SUCCESS = 1;
    
    private Connection conn;
    
    public void openConnection(String driverClass, String url,
            String userName, String password) throws Exception {
        Class.forName (driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    public void closeConnection() throws SQLException {
        conn.close();
    }
    
    public List<Map<String,Object>> findAllRecords(String tableName) throws SQLException {
        List<Map<String,Object>> records = new ArrayList<>();
        
        String sql = SELECT_ALL_FROM + tableName;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String,Object> record = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
                records.add(record);
            }
        } catch (SQLException e) {
            throw e;
        }
        
        return records;
    }
    
    public boolean deleteRecordById(String tableName, String primaryKeyName,
            Object primaryKeyValue) throws SQLException {
        
        boolean recordDeleted;
        String sql = "DELETE FROM " + tableName
                + WHERE + primaryKeyName + EQUALS;
        
        if (primaryKeyValue instanceof String) {
            sql += "'" + primaryKeyValue + "'";
        } else {
            sql += primaryKeyValue;
        }
        
        try (Statement stmt = conn.createStatement()) {
            recordDeleted = stmt.executeUpdate(sql) == SUCCESS;
            return recordDeleted;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public boolean deleteRecordById2(String tableName, String primaryKeyName,
            Object primaryKeyValue) throws SQLException {
        
        boolean recordDeleted;
        String sql = "DELETE FROM " + tableName
                + WHERE + primaryKeyName + EQUALS + PARAMETER;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, primaryKeyValue);
            recordDeleted = pstmt.executeUpdate() == SUCCESS;
            return recordDeleted;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public static void main(String[] args) throws Exception {
        MySqlDb db = new MySqlDb();
        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root", "admin");
        
        List<Map<String,Object>> records = db.findAllRecords("book");
        
        for (Map record : records) {
            System.out.println(record);
        }
        
        System.out.println("Deleting record...");
        
        boolean success = db.deleteRecordById2("author", "author_id", "b101");
        
        if (success) {
            System.out.println("Record deleted.");
        } else {
            System.out.println("Record NOT deleted.");
        }
        
        records = db.findAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }
        
        db.closeConnection();
    }
    
}
