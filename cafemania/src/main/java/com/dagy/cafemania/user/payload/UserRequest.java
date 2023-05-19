package com.dagy.cafemania.user.payload;

import com.dagy.cafemania.user.Role;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRequest {

    private Integer id;
    @NotNull(message = "Le nom est obligatoire")
    @NotEmpty(message = "Le nom est obligatoire")
    private String firstname;

    @NotNull(message = "Le prénom est obligatoire")
    @NotEmpty(message = "Le prénoms est obligatoire")
    private String lastname;

    @NotNull(message = "L'email est obligatoire")
    @NotEmpty(message = "L'email est obligatoire")
    @Email(message = "Email invalide", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    private String phone;

    /**
     * Min 1 uppercase letter.
     * Min 1 lowercase letter.
     * Min 1 special character.
     * Min 1 number.
     * Min 8 characters.
     */
    @NotNull(message = "Le password est obligatoire")
    @NotEmpty(message = "Le password est obligatoire")
    @Pattern(message = "Mot de passe invalide", regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;

    @NotBlank(message = "Confirmez votre password")
    private String confirmPassword;

    private Role role;
}
