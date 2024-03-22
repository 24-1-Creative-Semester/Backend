package com.example.sejongproject.SejongProfile.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Interest")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interest", nullable = false)
    private String interest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Profile profile;

}
