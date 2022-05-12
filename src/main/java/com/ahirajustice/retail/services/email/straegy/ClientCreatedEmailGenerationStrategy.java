package com.ahirajustice.retail.services.email.straegy;

import com.ahirajustice.retail.entities.Client;
import com.ahirajustice.retail.enums.EmailType;
import com.ahirajustice.retail.services.models.AppEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientCreatedEmailGenerationStrategy implements EmailGenerationStrategy {

    @Value("${app.config.email-service.template-ids.client-created}")
    private String clientCreatedTemplateId;

    @Value("${app.config.email-service.subjects.client-created}")
    private String clientCreatedSubject;

    @Value("${app.config.email-service.from}")
    private String from;

    @Override
    public boolean canApply(EmailType emailType) {
        return EmailType.CLIENT_CREATED.equals(emailType);
    }

    @Override
    public AppEmail generateEmail(Object... values) {
        try {
            Client client = (Client) values[0];
            String secret = (String) values[1];

            AppEmail appEmail = new AppEmail();

            appEmail.setTemplateId(clientCreatedTemplateId);
            appEmail.setSubject(clientCreatedSubject);
            appEmail.addFrom(from);
            appEmail.addTo(client.getAdminEmail());
            appEmail.setContextVariable("name", client.getName());
            appEmail.setContextVariable("identifier", client.getIdentifier());
            appEmail.setContextVariable("secret", secret);

            return appEmail;
        }
        catch (ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }


    }

}
