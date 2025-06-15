package pe.edu.upc.patitasolidaria.backend.profiles.domain.services;

import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands.UpdateProfileCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Long handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdateProfileCommand command);
    void handle(DeleteProfileCommand command);
}