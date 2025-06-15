package pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands;

import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.valueobjects.ProfileType;

import java.util.List;

public record CreateProfileCommand(
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
    public CreateProfileCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be null or blank");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role is required");
        }
    }
}