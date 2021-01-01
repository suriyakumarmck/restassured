package utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;

public class EmailUtils {

    public static boolean isValidEmailAddress(ArrayList<String> emailIds) {
        boolean result = true;
        for(var emailId : emailIds)
        {
            try {
                InternetAddress emailAddr = new InternetAddress(emailId);
                emailAddr.validate();
            } catch (AddressException ex) {
                result = false;
                break;
            }
        }
        return result;
    }
}