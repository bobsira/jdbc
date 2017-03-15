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

public class FirstExample1 {
    
    // JDBC DRIVER NAME AND DATABASE URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/EMP";
    
    //DATABSE CREDENTIALS
    static final String USER = "root";
    static final String PASS = "root";
    
    public static void main (String[] args ){
        Connection conn = null;
        Statement stmt = null;
        
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
            stmt = conn.createStatement();
            String sql = "update Employees set age = 30 where id = 103";
            
            // let us check if it returns a true result set or not.
            Boolean ret = stmt.execute(sql);
            System.out.println("Return value is : " + ret.toString());
            
            //let us update age of the record with ID = 103
            int rows = stmt.executeUpdate(sql);
            System.out.println("Rows impacted : " + rows);
            
            //let us select all the records and display them
            sql = "select id, first, last, age from Employees";
            ResultSet rs = stmt.executeQuery(sql);
            
            // step 5 : extract data from the result set 
            while(rs.next()){
            //retrieve by column name
            int id = rs.getInt("id");
            int age = rs.getInt("age");
            String first = rs.getString("first");
            String last = rs.getString("last");
            
            //display values 
            System.out.print("ID: " + id);
            System.out.print(", Age: " + age);
            System.out.print(", First: " + first);
            System.out.println(", Last: " + last);
        }
            // step 6 : clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }
        
        catch (SQLException se){
            //handles errors for jdbc
            se.printStackTrace();
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
    
}}
