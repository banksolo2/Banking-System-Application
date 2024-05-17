package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "user_roles",
        uniqueConstraints = @UniqueConstraint(
                name = "user_role_name_unique",
                columnNames = "name"
        )
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long roleId;

    private String name;

    private String code;
}
