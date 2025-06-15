package pe.edu.upc.patitasolidaria.backend.profiles.interfaces.rest.transform;

import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.valueobjects.ProfileType;
import pe.edu.upc.patitasolidaria.backend.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
        return new CreateProfileCommand(
                resource.name(),
                resource.email(),
                resource.address(),
                ProfileType.valueOf(resource.role().toUpperCase()), // Convierte el String a Enum ProfileType
                resource.paymentMethods(),
                resource.preferences(),
                resource.profilePic(),
                resource.bio(),
                resource.capacity(),
                resource.animalsAvailable() != null ? resource.animalsAvailable().size() : 0, // Se interpreta como cantidad
                resource.homeType(),
                resource.previousExperience()
        );
    }
}