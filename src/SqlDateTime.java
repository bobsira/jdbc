import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
public class SqlDateTime {
    public static void main(String[] args ){
        //get standard date and time
        java.util.Date javaDate = new java.util.Date();
        long javaTime = javaDate.getDate();
        System.out.println("The Java Date is: " + 
                javaDate.toString());
        
        //get and display sql date
        java.sql.Date sqlDate = new java.sql.Date(javaTime);
        System.out.println("The SQL Date is: " +
                sqlDate.toString());
        
        //get and display SQL TIME
        java.sql.Time sqlTime = new java.sql.Time(javaTime);
        System.out.println("The SQL TIME is: "+
                sqlTime.toString());
        
        
        //get and display SQL TIMESTAMP
        java.sql.Timestamp sqlTimestamp = 
                new java.sql.Timestamp(javaTime);
        System.out.println("The SQL TIMESTAMP is: " + 
                sqlTimestamp.toString());
    }
    
}
