package web;

import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import log.ErrorLogger;
import sql.Query;

/**
 * Class for representing a webpage for a course. The class only takes the
 * course code as input and uses the database to obtain all other information
 * for the course. It can then be output as HTML on the user's browser.
 * 
 * @author Stephen Fahy
 */
public class CoursePage {
    private static final String CODE_REPLACE = "#[[code]]#";
    private static final String LECTURER_REPLACE = "#[[lecturer]]#";
    private static final String LOCATION_REPLACE = "#[[location]]#";
    private static final String DATES_REPLACE = "#[[dates]]#";
    private static final String START_TIME_REPLACE = "#[[s_time]]#";
    private static final String CLASS_TIME_REPLACE = "#[[c_time]]#";
    private static final String FEE_REPLACE = "#[[fee]]#";
    private static final String DESCRIPTION_REPLACE = "#[[desc]]#";
    private static final String CAPACITY_REPLACE = "#[[cap]]#";
    private static final String TITLE_TEMPLATE = 
            "<title>" + CODE_REPLACE + " Course Details</title>";
    private static final String BODY_TEMPLATE = 
            "<h1>" + CODE_REPLACE + " Course Details</h1>" + 
            "<p>Runs from: " + DATES_REPLACE + "</p>" +
            "<p>Start time: " + START_TIME_REPLACE + "</p>" +
            "<p>Class duration: " + CLASS_TIME_REPLACE + "</p>" +
            "<p>Lecturer: " + LECTURER_REPLACE + "</p>" +
            "<p>Location: " + LOCATION_REPLACE + "</p>" +
            "<p>Cost: €" + FEE_REPLACE + "</p>" + 
            "<p>Capactiy: " + CAPACITY_REPLACE + "</p>" +
            "<div><p>Course Description: </p>" + DESCRIPTION_REPLACE + "</div>";
    private static SimpleDateFormat DATE_FORMAT = 
            new SimpleDateFormat("dd/MM/yyyy");
    
    private String code;
    private String title;
    private String body;
    private Date startDate;
    
    /**
     * Constructor.
     * @param code The course code of this webpage.
     */
    public CoursePage(String code) {
        ResultSet results = Query.query("SELECT * FROM Courses WHERE code = '" + 
                code + "';");
        ResultSet curCapacityResults = 
                Query.query("SELECT COUNT(*) AS curCapacity FROM " + 
                "Registrations WHERE courseCode = '" + code + "';");
        
        this.code = code;
        
        try {
            results.next();
            curCapacityResults.next();
            startDate = results.getDate("startDate");
            Date endDate = new Date(startDate.getTime() + 
                    (1000 * 60 * 60 * 24 * results.getInt("courseDuration")));
            Time startTime = results.getTime("startTime");
            Time classDuration = new Time(results.getInt("classDuration") * 60 * 1000);
            String lecturer = results.getString("lecturer");
            String location = results.getString("location");
            int fee = results.getInt("fee");
            int maxCapacity = results.getInt("capacity");
            int curCapacity = curCapacityResults.getInt("curCapacity");
            String desc = results.getString("description");
            
            System.out.println(title);
            System.out.println(body);
            System.out.println("###########################");
            title = TITLE_TEMPLATE.replaceAll(CODE_REPLACE, code);
            body = processQuery(code, startDate, endDate, startTime, lecturer, 
                    location, fee, maxCapacity, curCapacity, desc, classDuration);
            System.out.println(title);
            System.out.println(body);
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
            
            title = "<title>Database Error</title>";
            body = "<h1>Database Error</h1>";
            startDate = new Date(0);
        }
    }
    
    /**
     * Adds a hit to the counter on the database.
     */
    public void addHit() {
        Query.query("UPDATE Courses SET hits = hits + 1 WHERE code = '" +
                code + "';");
    }
    
    /**
     * Uses parameters from the database query to construct a body for the page.
     * 
     * @param code The course code.
     * @param start The start date of the course.
     * @param end The end date of the course.
     * @param time The daily time the course is held at.
     * @param lecturer The lecturer teaching the class.
     * @param location The location of the course classes.
     * @param fee The cost of the course in cents.
     * @param maxCapacity The maximum capacity of the course.
     * @param curCapacity The current capacity of the course.
     * @param descriptionPseudo Pseudo-HTML of the course description.
     * @return The body content of the page.
     */
    private static String processQuery(String code, Date start, Date end, 
            Time startTime, String lecturer, String location, int fee, 
            int maxCapacity, int curCapacity, String descriptionPseudo,
            Time classDuration) {
        String output = BODY_TEMPLATE;
        
        String feeWithDecimal = String.valueOf(((double)fee) / 100.0);
        String capString = curCapacity + "/" + maxCapacity;
        String description = HTMLTransformer.toHTML(descriptionPseudo);
        String dates = DATE_FORMAT.format(start) + " to " + DATE_FORMAT.format(end);
        
        output = output.replaceAll(CODE_REPLACE, code);
        output = output.replaceAll(LECTURER_REPLACE, lecturer);
        output = output.replaceAll(LOCATION_REPLACE, location);
        output = output.replaceAll(DATES_REPLACE, dates);
        output = output.replaceAll(FEE_REPLACE, feeWithDecimal);
        output = output.replaceAll(START_TIME_REPLACE, startTime.toString());
        output = output.replaceAll(CLASS_TIME_REPLACE, classDuration.toString());
        output = output.replaceAll(CAPACITY_REPLACE, capString);
        output = output.replaceAll(DESCRIPTION_REPLACE, description);
        
        return output;
    }
    
    //getters
    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }
    public Date getStartDate() {
        return startDate;
    }
}
