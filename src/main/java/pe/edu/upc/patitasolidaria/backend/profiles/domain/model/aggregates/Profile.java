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

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", length = 100, nullable = false))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "address", length = 255, nullable = false))
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
        // Default constructor for JPA
    }

    public Profile(CreateProfileCommand command) {
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
        this.name = name;
        this.email = new Email(email);
        this.address = new Address(address);
        this.role = role;
        this.paymentMethods = paymentMethods;
        this.preferences = preferences;
        this.profilePic = profilePic;
        this.bio = bio;
        this.capacity = capacity;
        this.animalsAvailable = animalsAvailable;
        this.homeType = homeType;
        this.previousExperience = previousExperience;
        return this;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
}
