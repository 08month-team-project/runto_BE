package com.runto.domain.gathering.domain;

import com.runto.domain.common.BaseTimeEntity;
import com.runto.domain.gathering.type.GatheringStatus;
import com.runto.domain.gathering.type.GoalDistance;
import com.runto.domain.gathering.type.RunningConcept;
import com.runto.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.runto.domain.gathering.type.GatheringStatus.NORMAL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "gathering_type")
@Entity
public class Gathering extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gathering_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User host;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false, name = "appointed_at")
    private LocalDateTime appointedAt;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RunningConcept concept;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_distance")
    private GoalDistance goalDistance;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(nullable = false)
    private Long hits;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private GatheringStatus status;

    @Column(name = "max_number", nullable = false)
    private Integer maxNumber;

    @Column(name = "current_number", nullable = false)
    private Integer currentNumber;


    @PrePersist
    public void prePersist() {
        hits = 0L;
        status = NORMAL;
        currentNumber = 1; // 주최자에 대한 수
    }


}

