package SMS_Shared.ServiceImpl;


import SMS_Shared.Repository.TagRepo;
import SMS_Shared.Service.EmailService;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;


@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    TagRepo tagRepo;


private final JavaMailSender javaMailSender;
public EmailServiceImp (JavaMailSender javaMailSender){
    this.javaMailSender=javaMailSender;
}

    @Override
    public void sendEmailWithAttachment(String subject, Exception exp) throws MessagingException, IOException {
        Map<String, Object> emailCredentials = tagRepo.getEmailCredentials();

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(new InternetAddress("App Rover <alert@m2d.com>"));
        message.setSubject(subject);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(String.valueOf(emailCredentials.get("email_to"))));
        message.addHeader("X-Priority", "1");


        String FileName = "Logs.txt";
        final FileWriter fr = new FileWriter(FileName, true);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < exp.getStackTrace().length; ++i) {
            str.append(exp.getStackTrace()[i]).append("\n");
        }
        fr.write(new Date() + "\r\n" + exp.getMessage() + "\r\n" + str);
        fr.flush();
        fr.close();


        FileSystemResource file = new FileSystemResource(new File(FileName));
        Multipart multipart = new MimeMultipart();
        BodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(FileName);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(file.getFilename());
        multipart.addBodyPart(attachmentBodyPart);
        message.setContent(multipart);

        javaMailSender.send(message);

        Files.deleteIfExists(Paths.get(FileName));
    }


//
//    @Override
//    public void sendEmailWithAttachment(Exception e) throws MessagingException, IOException {
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo("m.qasim@m2d.com");
//        helper.setSubject("Exception");
//
//
//        String FileName = "Logs.txt";
//        final FileWriter fr = new FileWriter(FileName, true);
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < e.getStackTrace().length; ++i) {
//            str.append(e.getStackTrace()[i]).append("\n");
//        }
//        fr.write(new Date() + "\r\n" + e.getMessage() + "\r\n" + str);
//        fr.flush();
//        fr.close();
//
//
//        FileSystemResource file = new FileSystemResource(new File(FileName));
//        Multipart multipart = new MimeMultipart();
//        BodyPart attachmentBodyPart = new MimeBodyPart();
//        DataSource source = new FileDataSource(FileName);
//        attachmentBodyPart.setDataHandler(new DataHandler(source));
//        attachmentBodyPart.setFileName(file.getFilename());
//        multipart.addBodyPart(attachmentBodyPart);
//        message.setContent(multipart);
//
//        javaMailSender.send(message);
//
//    }
}
