package pe.edu.upc.patitasolidaria.backend.profiles.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.patitasolidaria.backend.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Long handle(CreateProfileCommand command) {
        var profile = new Profile(command);
        try {
            this.profileRepository.save(profile);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving profile: " + e.getMessage());
        }
        return profile.getId();
    }

    @Override
    public Optional<Profile> handle(UpdateProfileCommand command) {
        var profileId = command.profileId();

        if (!this.profileRepository.existsById(profileId)) {
            throw new IllegalArgumentException("Profile with id " + profileId + " does not exist");
        }

        var profileToUpdate = this.profileRepository.findById(profileId).get();
        profileToUpdate.updateInformation(
                command.name(),
                command.email(),
                command.address(),
                command.role(),
                command.paymentMethods(),
                command.preferences(),
                command.profilePic(),
                command.bio(),
                command.capacity(),
                command.animalsAvailable(),
                command.homeType(),
                command.previousExperience()
        );

        try {
            var updatedProfile = this.profileRepository.save(profileToUpdate);
            return Optional.of(updatedProfile);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating profile: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteProfileCommand command) {
        var profileId = command.profileId();

        if (!this.profileRepository.existsById(profileId)) {
            throw new IllegalArgumentException("Profile with id " + profileId + " does not exist");
        }

        try {
            this.profileRepository.deleteById(profileId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting profile: " + e.getMessage());
        }
    }
}
