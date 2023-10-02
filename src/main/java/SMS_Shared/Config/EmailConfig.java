package SMS_Shared.Config;

import SMS_Shared.Repository.TagRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;
import java.util.Properties;

@Configuration
public class EmailConfig {

    private final TagRepo tagRepo;

    public EmailConfig(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        Map<String, Object> emailCredentials = tagRepo.getEmailCredentials();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(String.valueOf(emailCredentials.get("hostname")));
        mailSender.setUsername(String.valueOf(emailCredentials.get("email_user_id")));
        mailSender.setPassword(String.valueOf(emailCredentials.get("email_password")));
        mailSender.setPort(Integer.parseInt(String.valueOf(emailCredentials.get("port"))));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", emailCredentials.get("authentication"));
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        return mailSender;
    }

}
