package pe.edu.upc.patitasolidaria.backend.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Embeddable
public record Email(
        @NotBlank(message = "Email cannot be blank")
        @jakarta.validation.constraints.Email(message = "Email should be valid")
        String value
) implements Serializable {

    public Email {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}