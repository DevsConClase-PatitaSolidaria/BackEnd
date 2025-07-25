package pe.edu.upc.patitasolidaria.backend.pets.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.patitasolidaria.backend.pets.domain.model.aggregates.Pet;
import pe.edu.upc.patitasolidaria.backend.pets.domain.model.commands.CreatePetCommand;
import pe.edu.upc.patitasolidaria.backend.pets.domain.model.commands.UpdatePetCommand;
import pe.edu.upc.patitasolidaria.backend.pets.domain.model.commands.DeletePetCommand;
import pe.edu.upc.patitasolidaria.backend.pets.domain.services.PetCommandService;
import pe.edu.upc.patitasolidaria.backend.pets.infrastructure.persistence.jpa.repositories.PetRepository;
import pe.edu.upc.patitasolidaria.backend.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.aggregates.Profile;

import java.util.Optional;

@Service
public class PetCommandServiceImpl implements PetCommandService {

    private final PetRepository petRepository;
    private final ProfileRepository profileRepository;

    public PetCommandServiceImpl(PetRepository petRepository, ProfileRepository profileRepository) {
        this.petRepository = petRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public Long handle(CreatePetCommand command) {
        // 1. Obtener el profile
        Profile profile = profileRepository.findById(command.profileId())
                .orElseThrow(() -> new IllegalArgumentException("Profile with id " + command.profileId() + " not found"));

        // 2. Crear pet con el profile
        Pet pet = new Pet(command, profile);

        try {
            this.petRepository.save(pet);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving pet: " + e.getMessage());
        }

        return pet.getId();
    }

    @Override
    public Optional<Pet> handle(UpdatePetCommand command) {
        var petId = command.petId();

        if (!this.petRepository.existsById(petId)) {
            throw new IllegalArgumentException("Pet with id " + petId + " does not exist");
        }

        var petToUpdate = this.petRepository.findById(petId).get();
        petToUpdate.updateInformation(
                command.name(),
                command.age(),
                command.photo(),
                command.breed(),
                command.size(),
                command.status(),
                command.description(),
                command.healthStatus(),
                command.vaccinationStatus(),
                command.specialNeeds()
        );

        try {
            var updatedPet = this.petRepository.save(petToUpdate);
            return Optional.of(updatedPet);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating pet: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeletePetCommand command) {
        if (!this.petRepository.existsById(command.petId())) {
            throw new IllegalArgumentException("Pet with id " + command.petId() + " does not exist");
        }

        try {
            this.petRepository.deleteById(command.petId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting pet: " + e.getMessage());
        }
    }
}
