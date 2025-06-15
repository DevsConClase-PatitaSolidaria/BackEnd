package pe.edu.upc.patitasolidaria.backend.profiles.domain.services;

import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.queries.GetAllProfilesQuery;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.queries.GetProfileByIdQuery;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.queries.GetProfilesByRoleQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    List<Profile> handle(GetAllProfilesQuery query);
    Optional<Profile> handle(GetProfileByIdQuery query);
    List<Profile> handle(GetProfilesByRoleQuery query);
}