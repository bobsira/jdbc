
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
public class FirstExample6 {
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
                    ResultSet.CONCUR_UPDATABLE);
            
            //step 5 : execute a query
            String sql = "select id, first, last, age from Employees";
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.println("List result set for reference...");
            printRs(rs);
            
            //step 6 : loop through result set and add 5 in age
            //move to BFR position so while-loop works properly
            rs.beforeFirst();
            
            //step 7: extract data from result set
            while(rs.next()){
                //retrieve by colunm name
                int newAge = rs.getInt("age") + 5;
                rs.updateDouble("age", newAge);
                rs.updateRow();
            }
            
            System.out.println("List result set showing new ages...");
            printRs(rs);
            
            //insert a record into the table.
            //move to insert row and add column data with updateXXX()
            System.out.println("Inserting a new record...");
            rs.moveToInsertRow();
            rs.updateInt("id", 104);
            rs.updateString("first", "John");
            rs.updateString("last", "Paul");
            rs.updateInt("age", 40);
            //commit row
            rs.insertRow();
            System.out.println("List result set showing new set...");
            printRs(rs);
            
            //delete second record from the table.
            //set position to second record first
            rs.absolute(2);
            System.out.println("List the record before deleting...");
            //retrieve by column name
            
            int id = rs.getInt("id");
            int age = rs.getInt("age");
            String first = rs.getString("last");
            String last = rs.getString("last");
            
            //display values 
            System.out.print("ID: " + id);
            System.out.print(", Age" + age);
            System.out.print(", first" + first);
            System.out.println(", last" + last);
            
            //delete row
            rs.deleteRow();
            System.out.println("List result set after "
                    + "deleting one record...");
            
            printRs(rs);
            
            //step 8 : clean-up environment
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
            System.out.print(", Age" + age);
            System.out.print(", first"+ last);
            System.out.println(", last" + last );
        }
        
        System.out.println();
    }
    
}
