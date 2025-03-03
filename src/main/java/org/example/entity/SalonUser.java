package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.UserRoles;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name  = "users")
public class SalonUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String phoneNumber;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @PrePersist
    public void setDefaultRole() {
        if (role == null) {
            role = UserRoles.USER;
        }
    }

}
