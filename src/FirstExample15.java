
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FirstExample15 {
    // JDBC DRIVER NAME AND DATABASE URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/students";
    
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
           System.out.println("Connecting to a selected database...");
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Connected database successfully...");
            } catch (SQLException ex) {
                Logger.getLogger(FirstExample1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // STEP 4 : EXECUTE A QUERY
            System.out.println("Creating statement...");
            
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            
            String sql = "create table registration " + 
                         "(id integer not null auto_increment, " +
                         "first varchar(255), " +
                         "last varchar(255), " +
                         "age integer, " +
                         "primary key(id))";
            
            stmt.executeUpdate(sql);
            System.out.println("created table in the database successfully...");
            
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
