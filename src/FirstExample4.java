
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bobsira
 */
public class FirstExample4 {
    // JDBC DRIVER NAME AND DATABASE URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/EMP";
    
    //DATABSE CREDENTIALS
    static final String USER = "root";
    static final String PASS = "root";
    
    public static void main (String[] args ){
        Connection conn = null;
        Statement stmt = null;
        //PreparedStatement stmt = null;
        //CallableStatement stmt = null;
        
        try {
            
            try{
                // STEP 2: REGISTER JDBC DRIVER
                Class.forName(JDBC_DRIVER);
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FirstExample1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             // STEP 3: OPEN A CONNECTION 
           System.out.println("Connecting to database...");
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (SQLException ex) {
                Logger.getLogger(FirstExample1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // STEP 4 : EXECUTE A QUERY
            System.out.println("Creating statement...");
            
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String sql;
            sql = "select id, first, last, age from Employees";
            ResultSet rs = stmt.executeQuery(sql);
            
            
            //Move cursor to the last row.
            System.out.println("Moving cursor to the last...");
            rs.last();
            
            //STEP 5 : Extract data from result set
            System.out.println("Displaying record...");
            //Retrieve by column name 
            int id = rs.getInt("id");
            int age = rs.getInt("age");
            String first = rs.getString("first");
            String last = rs.getString("last");
            
            //Display values 
            System.out.print("ID: " + id);
            System.out.print(", Age: " + age);
            System.out.print(", First: " + first);
            System.out.println(", Last: " + last);
            
            //Move cursor to the first row
            System.out.println("Moving cursor to the first row..");
            rs.first();
            
            // STEP 6: Extract data from the resultset
            System.out.println("Displaying records");
            //Retrieve by column name
            id = rs.getInt("id");
            age = rs.getInt("age");
            first = rs.getString("first");
            last = rs.getString("last");
            
            //display values 
            System.out.print("ID: " + id);
            System.out.print(", Age" + age );
            System.out.print(", First" + first);
            System.out.println(", Last" + last );
            //move cursor to the first row.
            
            System.out.println("Moving cursor to the next row...");
            rs.next();
            
            
            //Step 7: Extract data from result set
            System.out.println("Displaying records");
            //Retrieve by column name
            id = rs.getInt("id");
            age = rs.getInt("age");
            first = rs.getString("first");
            last = rs.getString("last");
            
            //display values 
            System.out.print("ID: " + id);
            System.out.print(", Age" + age );
            System.out.print(", First" + first);
            System.out.println(", Last" + last );
            
            //STEP 8: CLEAN-UP ENVIRONMENT
            rs.close();
            stmt.close();
            conn.close();
            
        } 
        catch (SQLException exc){
            exc.printStackTrace();
        }
        
        finally
           {
      //finally block used to close resources
            
           try{
                if(stmt!=null)
                    stmt.close();
             }
           catch (SQLException se2)
              {
                // nothing we can do  
               }
           try{
                if(conn!=null)
                  conn.close();
              }
           catch (SQLException se)
           {
              se.printStackTrace();
            }
           
          }
    }
    
}
