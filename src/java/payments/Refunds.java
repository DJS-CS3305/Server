package payments;

import com.paypal.sdk.core.nvp.NVPAPICaller;
import com.paypal.sdk.exceptions.PayPalException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import log.ErrorLogger;

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
            String reply = URLDecoder.decode(CALLER.call(cmd), "UTF-8");
            
            System.out.println(reply);
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
        }
    }
}
