package com.dagy.cafemania.user.service;

import com.dagy.cafemania.security.jwt.JwtService;
import com.dagy.cafemania.shared.exceptions.EntityAllReadyExistException;
import com.dagy.cafemania.shared.exceptions.IncorrectPasswordException;
import com.dagy.cafemania.shared.exceptions.InvalidCredentialException;
import com.dagy.cafemania.shared.exceptions.ResourceNotFoundException;
import com.dagy.cafemania.shared.validators.ObjectsValidator;
import com.dagy.cafemania.user.User;
import com.dagy.cafemania.user.payload.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final ObjectsValidator<SignUpRequest> signUpRequestValidator;
    private final ObjectsValidator<SignInRequest> authenticationValidator;

    private final static String USER_NOT_FOUND_MSG =
            "L'utilisateur avec l'email %s n'a pas été trouvé";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("try to loadUserByUsername {}", username);
        Optional<User> userDetails = userRepository.findByEmail(username);
        log.info("loadUserByUsername success {}", userDetails);
        return Optional.of(
                User.builder()
                        .email(userDetails.get().getEmail())
                        .password(userDetails.get().getPassword())
                        .role(userDetails.get().getRole())
                        .phone(userDetails.get().getPhone())
                        .build()
        ).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, username)));

    }

    @Override
    public List<UserResponse> getAllUser() {
        log.info("Récupération de tous les utilisateurs");
        return userRepository.findAll()
                .stream()
                .map(UserMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse update(Integer id, UserRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        User user = optionalUser.get();
        // Mettre à jour les champs nécessaires avec les valeurs de la requête
//        user.setFirstName(request.getFirstname());
//        user.setLastName(request.getLastname());
//        user.setPhone(request.getPhone());
//        user.setEmail(request.getEmail());

        if ( request.getFirstname()==null){
            user.setFirstName(user.getFirstName());
        }else{
            user.setFirstName(request.getFirstname());
        }

        if ( request.getLastname()==null){
            user.setLastName(user.getLastName());
        }else{
            user.setLastName(request.getLastname());
        }

        if (request.getPhone()==null ){
            user.setPhone(user.getPhone());
        }else{
            user.setPhone(request.getPhone());
        }

        if ( request.getEmail()==null ){
            user.setEmail(user.getEmail());
        }else{
            user.setEmail(request.getEmail());
        }
        // ...

        // Enregistrer les modifications dans la base de données
        User updatedUser = userRepository.save(user);

        // Construire la réponse avec les données mises à jour
        UserResponse response = UserResponse.builder()
                .id(updatedUser.getId())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .phoneNumber(updatedUser.getPhone())
                .email(updatedUser.getEmail())
                // ...
                .build();

        return response;
    }

    @Override
    public List<UserResponse> findWithSorting(String field) {
        log.info("Récupération de tous les utilisateurs par ordre decroissant");
        List<User> userList;
        if ("firstName".equalsIgnoreCase(field)) {
            userList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
        } else if ("email".equalsIgnoreCase(field)) {
            userList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "email"));
        } else {
            throw new IllegalArgumentException("Invalid field for sorting: " + field);
        }
        return userList
                .stream()
                .map(UserMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserResponse> findWithPagination(int size, int page) {
        log.info("Récupération de tous les utilisateurs par pagination");
       List<UserResponse> responses = userRepository.findAll(PageRequest.of(size, page))
                .stream()
                .map(UserMapper::fromEntity)
                .toList();

        return new PageImpl<>(responses);
    }

    @Override
    public Page<UserResponse> findWithPaginationAndSorting(int page, int size, String field) {
        log.info("Récupération de tous les utilisateurs par pagination et par ordre decroissant");
        Pageable pageable;
        if ("firstName".equalsIgnoreCase(field)) {
            pageable = PageRequest.of(page, size, Sort.by("firstName").ascending());
        } else if ("email".equalsIgnoreCase(field)) {
            pageable = PageRequest.of(page, size, Sort.by("email").ascending());
        } else {
            throw new IllegalArgumentException("Invalid field for sorting: " + field);
        }

        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> userResponses = userPage.getContent().stream()
                .map(UserMapper::fromEntity)
                .toList();

//        return new PageImpl<>(userResponses, pageable, userPage.getTotalElements());
        return new PageImpl<>(userResponses);
    }

    @Override
    public SignUpResponse signup(SignUpRequest signUpRequest) {

        log.info("Creation de compte de : " + signUpRequest);
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            signUpRequestValidator.validate(signUpRequest);
            throw new IncorrectPasswordException("Les mots de passes ne correspondent pas");
        }

        Optional<User> verifiedUser = userRepository.findByEmail(signUpRequest.getEmail());

        if (verifiedUser.isPresent()) {
            log.warn("The user with email {}  already exist in BD", signUpRequest.getEmail());
            throw new EntityAllReadyExistException(
                    "L'email "
                            + signUpRequest.getEmail() +
                            " existe dèjà.");
        }

        var user = User.builder()
                .firstName(signUpRequest.getFirstname())
                .lastName(signUpRequest.getLastname())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(signUpRequest.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        var savedUser = userRepository.save(user);
        log.info("Compte crée : " + signUpRequest);

        var jwtToken = jwtService.generateToken(user);

        return SignUpResponse.builder()
                .accessToken(jwtToken)
//                .refreshToken("refreshToken")
                .build();
    }

    @Override
    public SignInResponse signin(SignInRequest signInRequest) {

        log.info("Connexion de l'utilisateur");

        authenticationValidator.validate(signInRequest);

        var user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Email incorrect ou innexistant !"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )

        );

        if (!authentication.isAuthenticated())
            throw  new InvalidCredentialException("Verifiez vos informations");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("USER LOGIN : "+user);

        if (!user.isEnabled() && user.getStatus().name().equalsIgnoreCase("DESACTIVE"))
            throw  new InvalidCredentialException("Attendez l'approbation de l'administration");

        var jwtToken = jwtService.generateToken(user);

        return SignInResponse.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .accessToken(jwtToken)
                .build();
    }


}
