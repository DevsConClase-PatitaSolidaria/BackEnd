package pe.edu.upc.patitasolidaria.backend.iam.application.internal.commandservices;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import pe.edu.upc.patitasolidaria.backend.iam.application.internal.outboundservices.hashing.HashingService;
import pe.edu.upc.patitasolidaria.backend.iam.application.internal.outboundservices.tokens.TokenService;
import pe.edu.upc.patitasolidaria.backend.iam.domain.model.aggregates.JwtUserDetails;
import pe.edu.upc.patitasolidaria.backend.iam.domain.model.aggregates.User;
import pe.edu.upc.patitasolidaria.backend.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.patitasolidaria.backend.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.patitasolidaria.backend.iam.domain.services.UserCommandService;
import pe.edu.upc.patitasolidaria.backend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import pe.edu.upc.patitasolidaria.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import pe.edu.upc.patitasolidaria.backend.iam.infrastructure.tokens.jwt.BearerTokenService;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.patitasolidaria.backend.profiles.domain.model.valueobjects.ProfileType;
import pe.edu.upc.patitasolidaria.backend.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.Optional;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

  private final UserRepository userRepository;
  private final HashingService hashingService;
  private final BearerTokenService tokenService;
  private final RoleRepository roleRepository;
  private final ProfileRepository profileRepository;

  public UserCommandServiceImpl(UserRepository userRepository,
                                HashingService hashingService,
                                BearerTokenService tokenService,
                                RoleRepository roleRepository,
                                ProfileRepository profileRepository) {
    this.userRepository = userRepository;
    this.hashingService = hashingService;
    this.tokenService = tokenService;
    this.roleRepository = roleRepository;
    this.profileRepository = profileRepository;
  }

  /**
   * Handle the sign-in command
   * <p>
   *     This method handles the {@link SignInCommand} command and returns the user and the token.
   * </p>
   * @param command the sign-in command containing the username and password
   * @return and optional containing the user matching the username and the generated token
   * @throws RuntimeException if the user is not found or the password is invalid
   */
  @Override
  public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
    var user = userRepository.findByUsername(command.username());
    if (user.isEmpty())
      throw new RuntimeException("User not found");

    if (!hashingService.matches(command.password(), user.get().getPassword()))
      throw new RuntimeException("Invalid password");

    // Obtener el perfil relacionado al usuario
    var profile = user.get().getProfile();
    if (profile == null)
      throw new RuntimeException("User has no associated profile");

    var roleName = profile.getRole().name();

    var jwtUserDetails = new JwtUserDetails(
            user.get().getUsername(),
            user.get().getPassword(),
            profile.getId(),
            roleName
    );

    var token = tokenService.generateToken(jwtUserDetails);

    return Optional.of(ImmutablePair.of(user.get(), token));
  }


  /**
   * Handle the sign-up command
   * <p>
   *     This method handles the {@link SignUpCommand} command and returns the user.
   * </p>
   * @param command the sign-up command containing the username and password
   * @return the created user
   */
  @Override
  public Optional<User> handle(SignUpCommand command) {
    if (userRepository.existsByUsername(command.username()))
      throw new RuntimeException("Username already exists");

    var roles = command.roles().stream()
            .map(role ->
                    roleRepository.findByName(role.getName())
                            .orElseThrow(() -> new RuntimeException("Role name not found")))
            .toList();

    // ✅ Crear el usuario sin perfil todavía
    var user = new User(command.username(), hashingService.encode(command.password()), roles);
    userRepository.save(user); // persistimos para generar ID

    // ✅ Crear el perfil con toda la data
    var profile = new Profile(command.profileCommand());
    profile.setUser(user); // asociar el usuario
    profileRepository.save(profile); // guardar el perfil

    // ✅ Asociar el perfil al usuario y guardar nuevamente (opcional)
    user.setProfile(profile);
    userRepository.save(user); // opcional si quieres tener ambos lados sincronizados

    return Optional.of(user);
  }
}
