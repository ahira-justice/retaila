package com.ahirajustice.retail.services.email.impl;

import com.ahirajustice.retail.entities.Client;
import com.ahirajustice.retail.enums.EmailType;
import com.ahirajustice.retail.services.email.EmailGenerationStrategy;
import com.ahirajustice.retail.services.models.AppEmail;
import org.springframework.stereotype.Component;

@Component
public class ClientCreatedEmailGenerationStrategy implements EmailGenerationStrategy {

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

            appEmail.addTo(client.getAdminEmail());
            appEmail.setContextVariable("identifier", client.getIdentifier());
            appEmail.setContextVariable("secret", secret);

            return appEmail;
        }
        catch (ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }


    }

}
