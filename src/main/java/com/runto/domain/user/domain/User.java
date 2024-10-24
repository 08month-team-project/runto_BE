package com.runto.domain.user.domain;

import com.runto.domain.common.BaseTimeEntity;
import com.runto.domain.user.type.Gender;
import com.runto.domain.user.type.UserRole;
import com.runto.domain.user.type.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    private String name;

    @Column(nullable = false, length = 15)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(STRING)
    private Gender gender;

    @Column(nullable = false, name = "user_status")
    @Enumerated(STRING)
    private UserStatus status;

    @Column(nullable = false, name = "user_role")
    @Enumerated(STRING)
    private UserRole role;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LocalAccount localAccount;

    @PrePersist
    public void prePersist() {
        status = UserStatus.ACTIVE;
    }
}
