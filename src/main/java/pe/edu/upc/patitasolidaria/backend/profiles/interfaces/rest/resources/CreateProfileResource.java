package pe.edu.upc.patitasolidaria.backend.profiles.interfaces.rest.resources;

import java.util.List;

public record CreateProfileResource(
        String name,
        String email,
        String address,
        String role,
        List<String> paymentMethods,
        List<String> preferences,
        String profilePic,
        String bio,
        int capacity,
        List<String> animalsAvailable,
        String homeType,
        String previousExperience
) {}