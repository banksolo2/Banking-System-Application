package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table( name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Account {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String accountNumber;
    @ManyToOne
    @JoinColumn( name = "account_type_id")
    private AccountType accountType;
    @ManyToOne
    @JoinColumn( name = "account_status_id")
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    @ManyToOne
    @JoinColumn( name = "gender_id")
    private Gender gender;
    private String address;
    @ManyToOne
    @JoinColumn( name = "state_of_origin")
    private State stateOfOrigin;
    private String phoneNumber;
    private String alternativePhoneNumber;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn( name = "created_by")
    private User createdBy;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}
