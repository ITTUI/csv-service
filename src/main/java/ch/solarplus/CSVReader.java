package ch.solarplus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CSVReader {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        // URL from the DB "grades"
        String jdbcURL = "jdbc:mysql://localhost:3306/grades";

        // change the username
        String username = "*****";

        // change the password
        String password = "";
 
        String csvFilePath = "C:\\Users\\ivan.tujkic\\Documents\\Summaries\\VC Code\\grades.csv";
 
        int batchSize = 20;
 
        Connection connection = null;
 
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
 
            // insert-query for the table
            String sql = 
                "INSERT INTO grades_student (lastname, firstname, test_1, test_2, test_3, test_4, final_grade, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
 
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;
 
            int count = 0;
 
            // skip header line
            lineReader.readLine(); 
 
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String lastname = data[0].replace("\"", "");
                String firstname = data[1].replace("\"", "");
                String test_1 = data[2];
                String test_2 = data[3];
                String test_3 = data[4];
                String test_4 = data[5];
                String final_grade = data[6];
                String grade = data[7].replace("\"", "");
 
                stmt.setString(1, lastname);
                stmt.setString(2, firstname);
                stmt.setString(3, test_1);
                stmt.setString(4, test_2);
                stmt.setString(5, test_3);
                stmt.setString(6, test_4);
                stmt.setString(7, final_grade);
                stmt.setString(8, grade);
 
                stmt.addBatch();
 
                if (count % batchSize == 0) {
                    stmt.executeBatch();
                }
            }
 
            lineReader.close();
 
            // execute the remaining queries
            stmt.executeBatch();
 
            connection.commit();
            connection.close();
 
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
 
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            
        }
 
    }

}
