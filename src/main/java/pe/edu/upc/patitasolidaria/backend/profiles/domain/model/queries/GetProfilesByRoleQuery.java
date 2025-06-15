package pe.edu.upc.patitasolidaria.backend.profiles.domain.model.queries;

import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.valueobjects.ProfileType;

public record GetProfilesByRoleQuery(ProfileType role) {}
