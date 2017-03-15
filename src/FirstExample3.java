/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bobsira
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class FirstExample3 {
    // JDBC DRIVER NAME AND DATABASE URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/EMP";
    
    //DATABSE CREDENTIALS
    static final String USER = "root";
    static final String PASS = "root";
    
    public static void main (String[] args ) {
        Connection conn = null;
        CallableStatement stmt = null;
        
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
            String sql = "{call getEmpName (?, ?)}";
            stmt = conn.prepareCall(sql);
            
            //Bind IN parameter first, then bind OUT parameter
            int empID = 102;
            stmt.setInt(1,empID); //This would set ID as 102
            //Because second parameter is OUT so register it
            stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
            
            //use execute method to run stored procedure.
            System.out.println("Executing stored procedure...");
            stmt.execute();
            
            //retrieve employee name with getXXX method
            String empName = stmt.getString(2);
            System.out.println("Emp Name with ID:" + empID + " is " + empName);
            stmt.close();
            conn.close();
            
        } 
        
        catch(SQLException e ){
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
