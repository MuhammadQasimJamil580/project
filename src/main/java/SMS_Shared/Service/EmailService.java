package SMS_Shared.Service;

import jakarta.mail.MessagingException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface EmailService {

//    public String emailWithAttachment(Exception e) throws MessagingException, IOException;

    void sendEmailWithAttachment(String subject,Exception exp) throws MessagingException, IOException;


}
