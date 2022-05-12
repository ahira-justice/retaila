package com.ahirajustice.retail.services.email.straegy;

import com.ahirajustice.retail.enums.EmailType;
import com.ahirajustice.retail.services.models.AppEmail;

public interface EmailGenerationStrategy {

    boolean canApply(EmailType emailType);

    AppEmail generateEmail(Object... values);

}
