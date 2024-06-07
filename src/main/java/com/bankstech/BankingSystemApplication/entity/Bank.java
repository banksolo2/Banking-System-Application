package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "banks"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Bank {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long bankId;
    private String name;
    private String code;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn( name = "updated_by")
    private User updatedBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn( name = "deleted_by")
    private User deletedBy;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
}
