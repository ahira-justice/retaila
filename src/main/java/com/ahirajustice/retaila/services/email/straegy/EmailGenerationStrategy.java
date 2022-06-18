package com.ahirajustice.retaila.services.email.straegy;

import com.ahirajustice.retaila.models.AppEmail;

public interface EmailGenerationStrategy {

    boolean canApply(Object... values);

    AppEmail generateEmail(Object... values);

}
