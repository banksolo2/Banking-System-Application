package com.bankstech.BankingSystemApplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "user_email_unique",
                columnNames = "email"
        )
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private Gender gender;
    private Boolean isActive;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
    @OneToOne
    private User deletedBy;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToOne
    private User createdBy;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OneToOne
    private User updatedBy;

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn( name = "user_id"),
            inverseJoinColumns = @JoinColumn( name = "role_id")
    )
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        for(Role r : this.roles){
            roles.add(r.getName());
        }

        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email ;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
