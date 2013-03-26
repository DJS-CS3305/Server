package web;

import java.net.URLEncoder;
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
    private static final String NAME_REPLACE = "#[[name]]#";
    private static final String LECTURER_REPLACE = "#[[lecturer]]#";
    private static final String LOCATION_REPLACE = "#[[location]]#";
    private static final String DATES_REPLACE = "#[[dates]]#";
    private static final String START_TIME_REPLACE = "#[[s_time]]#";
    private static final String CLASS_TIME_REPLACE = "#[[c_time]]#";
    private static final String FEE_REPLACE = "#[[fee]]#";
    private static final String DESCRIPTION_REPLACE = "#[[desc]]#";
    private static final String CAPACITY_REPLACE = "#[[cap]]#";
    private static final String LECTURER_LINK_REPLACE = "#[[lectLink]]#";
    
    private static final String TITLE_TEMPLATE = 
            "<title>" + CODE_REPLACE + " Course Details</title>";
    private static final String BODY_TEMPLATE = "<div id=\"coursePage\">" +
            "<h2>" + CODE_REPLACE + " - " + NAME_REPLACE + "</h2>" + 
            "<p>Runs from: " + DATES_REPLACE + "</p>" +
            "<p>Start time: " + START_TIME_REPLACE + "</p>" +
            "<p>Class duration: " + CLASS_TIME_REPLACE + "</p>" +
            "<p>Lecturer: <a href=\"lecturers.jsp?name=" + LECTURER_LINK_REPLACE + 
                "\">" + LECTURER_REPLACE + "</a></p>" +
            "<p>Location: " + LOCATION_REPLACE + "</p>" +
            "<p>Cost: €" + FEE_REPLACE + "</p>" + 
            "<p>Capactiy: " + CAPACITY_REPLACE + "</p>" +
            "<div id=\"description\"><p>Course Description: </p>" + DESCRIPTION_REPLACE + "</div>" + 
            "</div>";
    private static SimpleDateFormat DATE_FORMAT =  new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat TIME_FORMAT =  new SimpleDateFormat("HH:mm");
    
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
            
            String name = results.getString("name");
            startDate = results.getDate("startDate");
            Date endDate = new Date(startDate.getTime() + 
                    (1000 * 60 * 60 * 24 * results.getInt("courseDuration")));
            Time startTime = results.getTime("startTime");
            long duration = results.getInt("classDuration") * 60 * 1000;
            //need to offset by 1 hour to display correctly
            Date classDuration = new Date(duration - 3600000);
            String lecturer = results.getString("lecturer");
            String location = results.getString("location");
            int fee = results.getInt("fee");
            int maxCapacity = results.getInt("capacity");
            int curCapacity = curCapacityResults.getInt("curCapacity");
            String descPseudo = results.getString("description");
            
            title = TITLE_TEMPLATE.replace(CODE_REPLACE, code);
            body = processQuery(code, name, startDate, endDate, startTime, lecturer, 
                    location, fee, maxCapacity, curCapacity, descPseudo, classDuration);
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
            
            title = "<title>Database Error</title>";
            body = "<h2>Database Error</h2>";
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
    private static String processQuery(String code, String name, Date start, 
            Date end, Time startTime, String lecturer, String location, int fee, 
            int maxCapacity, int curCapacity, String descriptionPseudo,
            Date classDuration) {
        String output = BODY_TEMPLATE;
        
        //format for appearance
        String feeWithDecimal = String.valueOf(((double)fee) / 100.0);
        String capString = curCapacity + "/" + maxCapacity;
        String description = HTMLTransformer.toHTML(descriptionPseudo);
        String dates = DATE_FORMAT.format(start) + " to " + DATE_FORMAT.format(end);
        String formattedDuration = TIME_FORMAT.format(classDuration);
        String formattedStart = TIME_FORMAT.format(startTime);
        String lecturerLink = "error";
        
        try {
            lecturerLink = URLEncoder.encode(lecturer, "UTF-8");
        }
        catch(Exception e) {
            
        }
        
        //cuts off leading zero if one exists
        if(formattedDuration.substring(0, 1).equals("0")) {
            formattedDuration = formattedDuration.substring(1);
        }
        
        output = output.replace(CODE_REPLACE, code);
        output = output.replace(NAME_REPLACE, name);
        output = output.replace(LECTURER_REPLACE, lecturer);
        output = output.replace(LECTURER_LINK_REPLACE, lecturerLink);
        output = output.replace(LOCATION_REPLACE, location);
        output = output.replace(DATES_REPLACE, dates);
        output = output.replace(FEE_REPLACE, feeWithDecimal);
        output = output.replace(START_TIME_REPLACE, formattedStart);
        output = output.replace(CLASS_TIME_REPLACE, formattedDuration);
        output = output.replace(CAPACITY_REPLACE, capString);
        output = output.replace(DESCRIPTION_REPLACE, description);
        
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
