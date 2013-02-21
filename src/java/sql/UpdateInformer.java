package sql;

import java.sql.ResultSet;
import log.ErrorLogger;
import mail.Mailer;

/**
 * Informs users of updates to the courses they are part of.
 * 
 * @author Stephen Fahy
 */
public class UpdateInformer {
    private static final String BLANK_COURSE_CODE_ERROR = 
            "Blank course code found in sql.UpdateInformer.inform() with " +
            "query ";
    
    private static final String REPLACEMENT_COURSE_CODE = "@#~~~~#@";
    private static final String MESSAGE = 
            "The details of the course " + REPLACEMENT_COURSE_CODE + 
            " to which you are registered, have changed. Please " +
            "visit the website to view the changes.";
    private static final String SUBJECT = "Update to course " + 
            REPLACEMENT_COURSE_CODE;
    
    /**
     * Informs the users via email of updates to their courses.
     * 
     * @param query The update query that caused the update.
     */
    public static void inform(String query) {
        String courseCode = "";
        int index = 0;
        
        for(String word : query.split(" ")) {
            if(word.equals("code")) {
                // The code will be 2 words after; ie: "code = 'CS3305'"
                courseCode = query.split(" ")[index + 2];
                // This shaves off the punctuation; ie: 'CS3305', to CS3305
                // It also works if the code is the last part: 'CS3305'; to CS3305
                courseCode = courseCode.substring(1, 2);
                //shouldn't break to make sure the code received is the last code
                //ie: the one after the WHERE
            }
            index++;
        }
        
        if(!courseCode.equals("")) {
            String selectQuery = "SELECT email FROM FullReg, Users WHERE " + 
                    "FullReg.userId = Users.id AND " +
                    "FullReg.courseCode = " + courseCode + ";";
            ResultSet results = Query.query(selectQuery);
            
            try {
                while(results.next()) {
                    String email = results.getString(1);
                    Mailer.mail(email, 
                            SUBJECT.replaceAll(REPLACEMENT_COURSE_CODE, courseCode),
                            MESSAGE.replaceAll(REPLACEMENT_COURSE_CODE, courseCode));
                }
            }
            catch(Exception e) {
                ErrorLogger.get().log(e.toString());
                e.printStackTrace();
            }
        }
        else {
            ErrorLogger.get().log(BLANK_COURSE_CODE_ERROR + query);
        }
    }
}
