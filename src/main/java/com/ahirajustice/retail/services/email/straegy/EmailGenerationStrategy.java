package com.ahirajustice.retail.services.email.straegy;

import com.ahirajustice.retail.services.models.AppEmail;

public interface EmailGenerationStrategy {

    boolean canApply(Object... values);

    AppEmail generateEmail(Object... values);

}
