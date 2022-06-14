package com.ahirajustice.retaila.services.email.straegy;

import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.enums.EmailType;
import com.ahirajustice.retaila.enums.UserTokenType;
import com.ahirajustice.retaila.services.models.AppEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordEmailGenerationStrategy implements EmailGenerationStrategy {

    @Value("${app.config.email-service.template-ids.reset-password}")
    private String resetPasswordTemplateId;

    @Value("${app.config.email-service.subjects.reset-password}")
    private String resetPasswordSubject;

    @Value("${app.config.email-service.from}")
    private String from;

    @Override
    public boolean canApply(Object... values) {
        try {
            EmailType emailType = (EmailType) values[0];
            UserTokenType userTokenType = (UserTokenType) values[1];

            return EmailType.USER_TOKEN.equals(emailType) && UserTokenType.RESET_PASSWORD.equals(userTokenType);
        }
        catch (ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public AppEmail generateEmail(Object... values) {
        try {
            User user = (User) values[0];
            String token = (String) values[1];

            AppEmail appEmail = new AppEmail();

            appEmail.setTemplateId(resetPasswordTemplateId);
            appEmail.setSubject(resetPasswordSubject);
            appEmail.addFrom(from);
            appEmail.addTo(user.getEmail());
            appEmail.setContextVariable("first_name", user.getFirstName());
            appEmail.setContextVariable("last_name", user.getLastName());
            appEmail.setContextVariable("token", token);

            return appEmail;
        }
        catch (ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }


    }

}
