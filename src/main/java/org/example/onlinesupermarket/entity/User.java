package org.example.onlinesupermarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    private String phoneNumber;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String userImg;

    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    private LocalDateTime createdAt ;

    private boolean isLocked = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WishList> wishLists;
}
