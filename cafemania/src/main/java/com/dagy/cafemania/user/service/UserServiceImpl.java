package com.dagy.cafemania.user.service;

import com.dagy.cafemania.shared.exceptions.EntityAllReadyExistException;
import com.dagy.cafemania.shared.exceptions.IncorrectPasswordException;
import com.dagy.cafemania.shared.validators.ObjectsValidator;
import com.dagy.cafemania.user.User;
import com.dagy.cafemania.user.payload.SignUpRequest;
import com.dagy.cafemania.user.payload.SignUpResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final ObjectsValidator<SignUpRequest> signUpRequestValidator;

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
                .name(signUpRequest.getLastname())
                .email(signUpRequest.getEmail())
//                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(signUpRequest.getRole())
                .createdAt(LocalDateTime.now())
                .build();


        var savedUser = userRepository.save(user);
        log.info("Compte crée : " + signUpRequest);

        return SignUpResponse.builder()
                .accessToken("jwtToken")
                .refreshToken("refreshToken")
                .build();
    }


}
