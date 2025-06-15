package pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands;

public record DeleteProfileCommand(Long profileId) {
    public DeleteProfileCommand {
        if (profileId == null) {
            throw new IllegalArgumentException("Profile ID is required");
        }
    }
}