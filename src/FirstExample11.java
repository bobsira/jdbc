
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.util.*;
import java.sql.*;

public class FirstExample11 {
    // JDBC DRIVER NAME AND DATABASE URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/EMP";
    
    //DATABSE CREDENTIALS
    static final String USER = "root";
    static final String PASS = "root";
    
    public static void main (String[] args ) throws IOException{
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        
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
            createXMLTable(stmt);
            
            
            //open a FileInputStream
            File f = new File("XML_DATA.xml");
            long fileLength = f.length();
            FileInputStream fis = new FileInputStream(f);
            
            //create PreparedStatement and stream data
            String SQL = "insert into XML_DATA values (?,?)";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, 100);
            pstmt.setAsciiStream(2, fis, (int)fileLength);
            pstmt.execute();
            
            //close input stream
            fis.close();
            
            // do a query to get the row
            SQL = "select Data from XML_DATA where id=100 ";
            rs = stmt.executeQuery(SQL);
            
            //get the first row
            if(rs.next()){
                //retrieve data from input stream
                InputStream xmlInputStream = rs.getAsciiStream(1);
                int c;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ( (c = xmlInputStream.read()) != -1 )
                    bos.write(c);
                
                //print results
                System.out.println(bos.toString());
            }
            //clean-up environment
            rs.close();
            stmt.close();
            pstmt.close();
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
    
    public static void createXMLTable(Statement stmt) throws SQLException{
        System.out.println("Creating XML_DATA table...");
        //create SQL Statement
        String streamingDataSql = "CREATE TABLE XML_DATA (id INTEGER, Data long) ";
        
        //drop table first if it exists
        try{
            stmt.executeUpdate("DROP TABLE XML_DATA");
        } catch (SQLException se ){
            //do nothing
        }
        
        //build table 
        stmt.executeUpdate(streamingDataSql);
    } 
    
}
