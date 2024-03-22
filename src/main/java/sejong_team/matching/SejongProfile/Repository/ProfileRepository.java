package com.example.sejongproject.SejongProfile.Repository;

import com.example.sejongproject.SejongProfile.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Retrieve a profile by ID with its savedPosts
    Profile findProfileWithSavedPostsByUserId(Long userId);

    // Retrieve a profile by ID with its languages
    Profile findProfileWithLanguagesByUserId(Long userId);

    // Retrieve a profile by ID with its achievements
    Profile findProfileWithAchievementsByUserId(Long userId);

    // Retrieve a profile by ID with its interests
    Profile findProfileWithInterestsByUserId(Long userId);

    List<Profile> findAll();
}
