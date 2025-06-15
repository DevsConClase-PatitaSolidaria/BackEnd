package pe.edu.upc.patitasolidaria.backend.profiles.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.queries.GetAllProfilesQuery;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.queries.GetProfileByIdQuery;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.queries.GetProfilesByRoleQuery;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.services.ProfileQueryService;
import pe.edu.upc.patitasolidaria.backend.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }

    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.profileId());
    }

    @Override
    public List<Profile> handle(GetProfilesByRoleQuery query) {
        return profileRepository.findByRole(query.role());
    }
}