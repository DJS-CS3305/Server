package test;

import java.sql.ResultSet;
import log.ErrorLogger;
import sql.Query;

/**
 * Test for queries.
 * 
 * @author Stephen Fahy
 */
public class QueryTest {
    public static void test() {
        String query = "SELECT * FROM Courses;";
        ResultSet rs = Query.query(query);
        
        try {
            while(rs.next()) {
                System.out.print(rs.getString(1));
                System.out.print(" :: ");
                System.out.print(rs.getString(2));
                System.out.print(" :: ");
                System.out.print(rs.getString(3));
                System.out.print(" :: ");
                System.out.print(rs.getString(4));
                System.out.print(" :: ");
                System.out.print(rs.getString(5));
                System.out.print(" :: ");
                System.out.print(rs.getString(6));
                System.out.print(" :: ");
                System.out.print(rs.getString(7));
                System.out.print(" :: ");
                System.out.println(rs.getString(8));
            }
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString() + " Printing failure.");
        }
    }
}
