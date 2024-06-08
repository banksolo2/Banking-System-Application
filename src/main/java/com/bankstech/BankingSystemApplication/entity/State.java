package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "states",
        uniqueConstraints = @UniqueConstraint(
                name = "state_name_unique",
                columnNames = "name"
        )
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class State {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long stateId;
    private String name;
    private String code;
}
