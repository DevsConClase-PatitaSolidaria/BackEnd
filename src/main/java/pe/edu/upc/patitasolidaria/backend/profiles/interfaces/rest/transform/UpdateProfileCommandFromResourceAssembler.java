package pe.edu.upc.patitasolidaria.backend.profiles.interfaces.rest.transform;

import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.valueobjects.ProfileType;
import pe.edu.upc.patitasolidaria.backend.profiles.interfaces.rest.resources.ProfileResource;

import java.util.List;

public class UpdateProfileCommandFromResourceAssembler {

    public static UpdateProfileCommand toCommandFromResource(Long profileId, ProfileResource resource) {
        return new UpdateProfileCommand(
                profileId,
                resource.name(),
                resource.email(),
                resource.address(),
                ProfileType.valueOf(resource.role()),
                resource.paymentMethods(),
                resource.preferences(),
                resource.profilePic(),
                resource.bio(),
                resource.capacity(),
                convertAnimalsAvailable(resource.animalsAvailable()),
                resource.homeType(),
                resource.previousExperience()
        );
    }

    private static Integer convertAnimalsAvailable(List<String> animalsAvailable) {
        if (animalsAvailable == null || animalsAvailable.isEmpty()) return 0;
        try {
            return Integer.parseInt(animalsAvailable.get(0));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}