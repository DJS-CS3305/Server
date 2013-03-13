package payments;

import com.paypal.sdk.core.nvp.NVPAPICaller;
import com.paypal.sdk.exceptions.PayPalException;
import java.net.URLEncoder;
import java.net.URLDecoder;

/**
 * Class for giving refunds to customers.
 * 
 * @author Stephen Fahy
 */
public class Refunds {
    private static final NVPAPICaller CALLER = new NVPAPICaller();
    
    /**
     * Gives a full refund for the given transaction ID.
     * @param transactionID 
     */
    public static void refund(String transactionID) throws PayPalException {
        String cmd = "METHOD=RefundTransaction&TRANSACTIONID=" + transactionID;
        
        try {
            cmd = URLEncoder.encode(cmd, "UTF-8");
        }
        catch(Exception e) {
        }
        
        String reply = CALLER.call(cmd);
        
        System.out.println(reply);
    }
}
