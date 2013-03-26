
import java.sql.Connection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class loginCheck {
    
    String username;
    String password;
    
    public loginCheck(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public boolean checkDB() {
        return false;
        
    }
    
}
