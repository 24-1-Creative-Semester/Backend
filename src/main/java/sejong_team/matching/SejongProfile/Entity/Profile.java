package com.example.sejongproject.SejongProfile.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", updatable = false)
    private Long userId;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "imagePath", nullable = false)
    private String imagePath;

    @Column(name = "imageName", nullable = false, unique = true)
    private String imageName;

//    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<MyPost> posts;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SavedPost> savedPost;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Language> language;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Achievement> achievement;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Interest> interest;




//    @OneToOne(mappedBy = "image")
//    @JoinColumn(name = "image_id", referencedColumnName = "imageId", foreignKey = @ForeignKey(name = "FKjvw51ll74pvplckkn054pakw9"))
//    private Profile profile;



}
