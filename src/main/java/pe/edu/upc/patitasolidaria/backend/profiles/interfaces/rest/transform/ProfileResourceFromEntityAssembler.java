package pe.edu.upc.patitasolidaria.backend.profiles.interfaces.rest.transform;

import org.springframework.stereotype.Component;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.patitasolidaria.backend.profiles.interfaces.rest.resources.ProfileResource;

import java.util.Collections;
import java.util.List;

@Component
public class ProfileResourceFromEntityAssembler {

    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
                entity.getId(),
                entity.getName(),
                entity.getEmail().value(),
                entity.getAddress().value(),
                entity.getRole().name(),
                entity.getPaymentMethods(),
                entity.getPreferences(),
                entity.getProfilePic(),
                entity.getBio(),
                entity.getCapacity() != null ? entity.getCapacity() : 0,
                convertAnimalsAvailableToList(entity.getAnimalsAvailable()),
                entity.getHomeType(),
                entity.getPreviousExperience()
        );
    }

    private static List<String> convertAnimalsAvailableToList(Integer animalsAvailable) {
        if (animalsAvailable == null) return Collections.emptyList();
        return List.of(String.valueOf(animalsAvailable));
    }
}
