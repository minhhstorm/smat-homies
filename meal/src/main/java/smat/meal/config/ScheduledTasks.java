package smat.meal.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import smat.meal.entity.NotificationEmail;
import smat.meal.entity.UserEntity;
import smat.meal.repository.UserRepository;
import smat.meal.service.MailContentBuilder;
import smat.meal.service.MailService;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private final UserRepository userRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Scheduled(cron = "0 49 16 * * ?")
    public void SendMailForMeal() {
//        List<UserEntity> userEntities = userRepository.findAll();
//        String message = mailContentBuilder.build("Hãy vào đăng kí cơm nào!!");
//        for (int i = 0; i < userEntities.size(); i++) {
//            System.out.println(userEntities.get(i).getEmail());
//            mailService.sendMailForMeal(new NotificationEmail("Hãy đăng kí cơm nào", userEntities.get(i).getEmail(), message));
//            logger.info("Success");
//        }
        Optional<UserEntity> userEntity = userRepository.findByUsername("minhnc");
        String message = mailContentBuilder.build("Hãy vào đăng kí cơm nào!!");
        mailService.sendMailForMeal(new NotificationEmail("Hãy đăng kí cơm nào", userEntity.get().getEmail(), message));
        logger.info("Success");
    }


}
