package com.kaya.yatang.db.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fridge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 🔹 User(소유자) 관계 (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
