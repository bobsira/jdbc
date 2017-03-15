/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bobsira
 */
//official connection to java database template
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirstExample2 {
    // JDBC DRIVER NAME AND DATABASE URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/EMP";
    
    //DATABSE CREDENTIALS
    static final String USER = "root";
    static final String PASS = "root";
    
    public static void main (String[] args ){
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            
            try{
                // STEP 2: REGISTER JDBC DRIVER
                Class.forName(JDBC_DRIVER);
                
            } catch (Exception e) {
                //Logger.getLogger(FirstExample1.class.getName()).log(Level.SEVERE, null, ex);
                e.printStackTrace();
            }
            
            
           // STEP 3: OPEN A CONNECTION 
           System.out.println("Connecting to database...");
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (SQLException ex) {
                //Logger.getLogger(FirstExample1.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            
            // STEP 4 : EXECUTE A QUERY
            System.out.println("Creating statement...");
            String sql = "update Employees set age = ? where id = ?";
            stmt = conn.prepareStatement(sql);
            
            //bind values into the parameters.
            stmt.setInt(1,35); // This would set age
            stmt.setInt(2,102);// This would set ID
            
            // Let us update age of the record with ID = 102;
            int rows = stmt.executeUpdate();
            System.out.println("Rows impacted : " + rows );
            
            // Let us select all the records and display them.
            sql = "SELECT id, first, last, age FROM Employees";
            ResultSet rs = stmt.executeQuery(sql);
            
            
            //STEP 5: Extract data from result set
            while(rs.next()){
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
            }
            
            //step 6 : clean-up environment
            rs.close();
            stmt.close();
            conn.close();
            
        } 
        catch (SQLException e){
            e.printStackTrace();
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
        
          System.out.println("Goodbye!");
    }
    
}
