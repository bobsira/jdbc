

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class FirstExample10 {
    // JDBC DRIVER NAME AND DATABASE URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/EMP";
    
    //DATABSE CREDENTIALS
    static final String USER = "root";
    static final String PASS = "root";
    
    public static void main (String[] args ){
        Connection conn = null;
        //Statement stmt = null;
        PreparedStatement stmt = null;
        ////CallableStatement stmt = null;
        
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
            String SQL = "insert into Employees(id,first,last,age) "+
                         "values(?, ?, ?, ?)";
            
            // Create preparedStatemen
            System.out.println("Creating statement...");
            stmt = conn.prepareStatement(SQL);
            
            
            // set auto-commit to false
            conn.setAutoCommit(false);
            
            //first, let us select all the records and display them.
            printRows(stmt);
            
            
            //set the variables 
            stmt.setInt(1, 400);
            stmt.setString(2, "Pappu");
            stmt.setString(3, "Singh");
            stmt.setInt(4, 33);
            // add it to the batch
            stmt.addBatch();
            
            
            //set the variables
            stmt.setInt(1, 401);
            stmt.setString(2, "Pawan");
            stmt.setString(3, "Singh");
            stmt.setInt(4, 31);
            // add it to the batch
            stmt.addBatch();
            
            
            //create an int[] to hold returned values
            int[] count = stmt.executeBatch();
            
            //explicitly commit statments to apply changes
            conn.commit();
            
            
            //again, let us select all the records and display them
            printRows(stmt);
            
            //clean-up environment
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
    
    public static void printRows(Statement stmt) throws SQLException{
        newLine("Displaying available rows...");
        
        //Let us select all the records and display them.
        String sql = "select id, first, last, age from Employees";
        ResultSet rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            //retrieve by column name
            int id = rs.getInt("id");
            int age = rs.getInt("age");
            String first = rs.getString("first");
            String last = rs.getString("last");
            
            //display values
           System.out.print("ID: " +id);
           //newLine("ID: ", id);
            System.out.print(", Age: " + age);
            System.out.print(", First: " + first);
            System.out.println(", last: " + last);
        }
        newLine();
        rs.close();
    }
    public static void newLine(String message){
        System.out.println(message);
    }
    public static void newLine(int value){
        System.out.println(value);
    }
    public static void newLine(){
        System.out.println();
    }
    public static void newLine(String message, int value){
        System.out.println(message + value);
    }
    
    public static void printRs(ResultSet rs ) throws SQLException{
        //ensure we start with first row
        rs.beforeFirst();
        while(rs.next()){
            //retrive by column name
            int id = rs.getInt("id");
            int age = rs.getInt("age");
            String first = rs.getString("first");
            String last = rs.getString("last");
            
            //display the values 
            System.out.print("ID: "+ id);
            System.out.print(", Age " + age);
            System.out.print(", first "+ last);
            System.out.println(", last " + last );
        }
        
        System.out.println();
    }
    
}
