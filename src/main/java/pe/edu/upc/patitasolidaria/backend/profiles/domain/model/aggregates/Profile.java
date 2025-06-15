package pe.edu.upc.patitasolidaria.backend.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.valueobjects.*;

import pe.edu.upc.patitasolidaria.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.List;

@Entity
@Table(name = "profiles")
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @Getter
    @Embedded
    private Email email;

    @Getter
    @Embedded
    private Address address;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 15, nullable = false)
    private ProfileType role;

    @Getter
    @ElementCollection
    @CollectionTable(name = "profile_payment_methods", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "payment_method", length = 50)
    private List<String> paymentMethods;

    @Getter
    @ElementCollection
    @CollectionTable(name = "profile_preferences", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "preference", length = 100)
    private List<String> preferences;

    @Getter
    @Column(name = "profile_pic", length = 300)
    private String profilePic;

    @Getter
    @Column(name = "bio", length = 500)
    private String bio;

    @Getter
    @Column(name = "capacity")
    private Integer capacity;

    @Getter
    @Column(name = "animals_available")
    private Integer animalsAvailable;

    @Getter
    @Column(name = "home_type", length = 50)
    private String homeType;

    @Getter
    @Column(name = "previous_experience", length = 500)
    private String previousExperience;

    public Profile() {
    }

    public Profile(CreateProfileCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        this.name = command.name();
        this.email = new Email(command.email());
        this.address = new Address(command.address());
        this.role = command.role();
        this.paymentMethods = command.paymentMethods();
        this.preferences = command.preferences();
        this.profilePic = command.profilePic();
        this.bio = command.bio();
        this.capacity = command.capacity();
        this.animalsAvailable = command.animalsAvailable();
        this.homeType = command.homeType();
        this.previousExperience = command.previousExperience();
    }

    public Profile updateInformation(
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
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        if (email != null && !email.trim().isEmpty()) {
            this.email = new Email(email);
        }
        if (address != null && !address.trim().isEmpty()) {
            this.address = new Address(address);
        }
        if (role != null) {
            this.role = role;
        }
        if (paymentMethods != null) {
            this.paymentMethods = paymentMethods;
        }
        if (preferences != null) {
            this.preferences = preferences;
        }
        if (profilePic != null) {
            this.profilePic = profilePic;
        }
        if (bio != null) {
            this.bio = bio;
        }
        if (capacity != null) {
            this.capacity = capacity;
        }
        if (animalsAvailable != null) {
            this.animalsAvailable = animalsAvailable;
        }
        if (homeType != null) {
            this.homeType = homeType;
        }
        if (previousExperience != null) {
            this.previousExperience = previousExperience;
        }
        return this;
    }
}