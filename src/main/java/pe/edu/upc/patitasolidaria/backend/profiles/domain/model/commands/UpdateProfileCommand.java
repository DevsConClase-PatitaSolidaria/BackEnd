package pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands;

import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.valueobjects.ProfileType;

import java.util.List;

public record UpdateProfileCommand(
        Long profileId,
        String name,
        String email,
        String address,
        ProfileType role,
        List<String> paymentMethods,
        List<String> preferences,
        String profilePic,
        String bio,
        Integer capacity,
        Integer animalsAvailable,
        String homeType,
        String previousExperience
) {
    public UpdateProfileCommand {
        if (profileId == null) {
            throw new IllegalArgumentException("Profile ID is required");
        }
    }
}