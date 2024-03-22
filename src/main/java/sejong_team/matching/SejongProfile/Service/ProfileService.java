package com.example.sejongproject.SejongProfile.Service;

import com.example.sejongproject.SejongProfile.Entity.*;
import com.example.sejongproject.SejongProfile.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Profile getProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Transactional
    public void upload(Profile profile, MultipartFile file) throws Exception{

        String imagePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\profileImage";

        UUID uuid = UUID.randomUUID();

        String imageName = uuid + "_" + file.getOriginalFilename();

        File saveImage = new File(imagePath, imageName);

        file.transferTo(saveImage);

        profile.setImageName(imageName);
        profile.setImagePath("/profileImage" + imagePath);

        profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public Resource loadProfileImage(Long profileId) throws FileNotFoundException {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new FileNotFoundException("Profile not found"));

        String imagePath = profile.getImagePath();
        String imageName = profile.getImageName();

        File imageFile = new File(imagePath, imageName);

        if (imageFile.exists()) {
            // 이미지 파일이 존재하면 Resource 객체로 반환
            return new FileSystemResource(imageFile);
        } else {
            // 이미지 파일이 존재하지 않으면 예외 처리 또는 기본 이미지 등을 반환
            throw new FileNotFoundException("Profile image not found");
        }
    }
    @Transactional
    public Profile addAchivementToProfile(Long profileId, Achievement achievement) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));

        achievement.setProfile(profile);
        profile.getAchievement().add(achievement);

        return profileRepository.save(profile);
    }

    @Transactional
    public Profile updateAchievement(Long profileId, Long achievementId, Achievement updatedAchievement) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        Achievement achievement = profile.getAchievement().stream()
                .filter(ach -> ach.getId().equals(achievementId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Achievement not found"));

        achievement.setAchivement(updatedAchievement.getAchivement());

        return profileRepository.save(profile);
    }

    @Transactional
    public Profile deleteAchievement(Long profileId, Long achievementId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        Achievement achievement = profile.getAchievement().stream()
                .filter(ach -> ach.getId().equals(achievementId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Achievement not found"));

        profile.getAchievement().remove(achievement);

        return profileRepository.save(profile);
    }
    @Transactional
    public Profile addInterestToProfile(Long profileId, Interest interest) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));

        interest.setProfile(profile);
        profile.getInterest().add(interest);

        return profileRepository.save(profile);
    }

    public Profile updateInterest(Long profileId, Long interestId, Interest updatedInterest) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        Interest interest = profile.getInterest().stream()
                .filter(inte -> inte.getId().equals(interestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Interest not found"));

        // updatedInterest의 필드를 사용하여 interest 업데이트
        interest.setInterest(updatedInterest.getInterest());

        return profileRepository.save(profile);
    }

    @Transactional
    public Profile deleteInterest(Long profileId, Long interestId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        Interest interest = profile.getInterest().stream()
                .filter(inte -> inte.getId().equals(interestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Interest not found"));

        profile.getInterest().remove(interest);

        return profileRepository.save(profile);
    }
    @Transactional
    public Profile addLanguageToProfile(Long profileId, Language language) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));

        language.setProfile(profile);
        profile.getLanguage().add(language);

        return profileRepository.save(profile);
    }

    @Transactional
    public Profile updateLanguage(Long profileId, Long languageId, Language updatedLanguage) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        Language language = profile.getLanguage().stream()
                .filter(lang -> lang.getId().equals(languageId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Language not found"));

        // updatedLanguage의 필드를 사용하여 language 업데이트
        language.setLanguage(updatedLanguage.getLanguage());

        return profileRepository.save(profile);
    }

    @Transactional
    public Profile deleteLanguage(Long profileId, Long languageId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        Language language = profile.getLanguage().stream()
                .filter(lang -> lang.getId().equals(languageId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Language not found"));

        profile.getLanguage().remove(language);

        return profileRepository.save(profile);
    }
    @Transactional
    public Profile addSavedPostToProfile(Long profileId, SavedPost savedPost) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));

        savedPost.setProfile(profile);
        profile.getSavedPost().add(savedPost);

        return profileRepository.save(profile);
    }
    @Transactional
    public Profile updateSavedPost(Long profileId, Long postId, SavedPost updatedSavedPost) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        SavedPost savedPost = profile.getSavedPost().stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("SavedPost not found"));

        // updatedSavedPost의 필드를 사용하여 savedPost 업데이트
        savedPost.setSavedPost(updatedSavedPost.getSavedPost());

        return profileRepository.save(profile);
    }

    @Transactional
    public Profile deleteSavedPost(Long profileId, Long postId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        SavedPost savedPost = profile.getSavedPost().stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("SavedPost not found"));

        profile.getSavedPost().remove(savedPost);

        return profileRepository.save(profile);
    }
    @Transactional(readOnly = true)
    public Profile getProfileWithSavedPosts(Long profileId) {
        return profileRepository.findProfileWithSavedPostsByUserId(profileId);
    }

    @Transactional(readOnly = true)
    public Profile getProfileWithLanguages(Long profileId) {
        return profileRepository.findProfileWithLanguagesByUserId(profileId);
    }

    @Transactional(readOnly = true)
    public Profile getProfileWithAchievements(Long profileId) {
        return profileRepository.findProfileWithAchievementsByUserId(profileId);
    }

    @Transactional(readOnly = true)
    public Profile getProfileWithInterests(Long profileId) {
        return profileRepository.findProfileWithInterestsByUserId(profileId);
    }


}
