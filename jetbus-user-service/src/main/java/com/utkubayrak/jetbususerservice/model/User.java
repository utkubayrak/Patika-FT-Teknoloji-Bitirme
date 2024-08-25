package com.utkubayrak.jetbususerservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utkubayrak.jetbususerservice.model.enums.GenderEnum;
import com.utkubayrak.jetbususerservice.model.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)

    private String fullName;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    private boolean isCorporate;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private boolean enabled;

    private String resetCode;

    private LocalDateTime resetCodeExpiresAt;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;



}
