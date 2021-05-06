package smat.meal.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import smat.meal.entity.NotificationEmail;
import smat.meal.exception.SmatException;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    void sendMailSignUp(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("anhpdn1@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
        //    messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
            messageHelper.setText(notificationEmail.getBody());
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException exception) {
            throw new SmatException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), exception);
        }
    }

    public void sendMailForMeal(NotificationEmail notificationEmail) {
        MimeMessagePreparator message1Preparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("anhpdn1@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            //    messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
            messageHelper.setText(notificationEmail.getBody());
        };

        try {
            mailSender.send(message1Preparator);
            log.info("Sent!!");
        } catch (MailException exception) {
            throw new SmatException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), exception);
        }
    }
}
