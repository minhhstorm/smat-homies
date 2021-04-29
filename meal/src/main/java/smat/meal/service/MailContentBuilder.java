package smat.meal.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import smat.meal.common.Constant;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;
    public String build(String message) {
        Context context = new Context();
        context.setVariable(Constant.MESSAGE, message);
        return templateEngine.process(Constant.MAIL_TEMPLATE, context);
    }
}
