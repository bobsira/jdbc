
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
public class FirstExample7 {
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
            
            //STEP 4: set auto commit as false
            conn.setAutoCommit(false);
            
            
            // STEP 5 : Execute a query to create statement with
            // required arguments for RS example
            System.out.println("Creating statement...");
            
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            
            //STEP 6: insert a row into Employees table
            System.out.println("Inserting one row...");
            String SQL = "insert into Employees " + 
                    "values (106, 20, 'Rita', 'Tez')";
            
            stmt.executeUpdate(SQL);
            
            
            //STEP 7: insert one more row into Employees table
            SQL = "insert into Employees " + 
                    "values (107, 22, 'Sita', 'Singh')";
            
            stmt.executeUpdate(SQL);
            
            //STEP 8: commit data here.
            System.out.println("Commiting data here...");
            conn.commit();
            
            //STEP 9: now list all the available records.
            String sql = "select id, first, last, age from Employees";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("List result set for reference...");
            printRs(rs);
            
            //STEP 10: clean-up environment
            rs.close();
            stmt.close();
            conn.close();
            
        } 
        catch (SQLException exc){
            // handles errors for jdbc
            exc.printStackTrace();
            
            //if there is an error then rollback the changes.
            System.out.println("Rolling back data here...");
            
            try{
                if(conn!=null)
                    conn.rollback();
            } catch (SQLException exc2){
                exc2.printStackTrace();
            }
            
            
            
            
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
