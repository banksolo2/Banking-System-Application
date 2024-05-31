package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "genders",
        uniqueConstraints = @UniqueConstraint(
                name ="gender_name_unique",
                columnNames = "name"
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Gender {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long genderId;
    private String name;
}
