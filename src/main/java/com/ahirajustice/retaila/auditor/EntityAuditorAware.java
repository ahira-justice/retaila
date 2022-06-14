package com.ahirajustice.retaila.auditor;

import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.services.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EntityAuditorAware implements AuditorAware<String> {

    private final CurrentUserService currentUserService;


    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            User currentUser = currentUserService.getCurrentUser();
            return Optional.of(currentUser.getUsername());
        }
        catch (Exception ex) {
            return Optional.of("SYSTEM");
        }

    }

}
