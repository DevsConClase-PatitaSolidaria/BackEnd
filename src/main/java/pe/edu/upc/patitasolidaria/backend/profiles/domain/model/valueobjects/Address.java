package pe.edu.upc.patitasolidaria.backend.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public record Address(
        @NotBlank(message = "Address cannot be blank")
        @Size(max = 255, message = "Address must be less than 255 characters")
        String value
) implements Serializable {

    public Address {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}