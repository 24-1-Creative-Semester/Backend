package com.example.sejongproject.SejongProfile.Controller;

import com.example.sejongproject.SejongProfile.Entity.*;
import com.example.sejongproject.SejongProfile.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile/test")
    public String profiletestForm(){
        return "image";
    }

    @GetMapping("/all")
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }


    @PostMapping("/profile/imageUpload")
    public boolean profileImageUpload(Profile profile, MultipartFile file) {
        try {
            profileService.upload(profile, file);
            return true; // 성공적으로 업로드되면 true 반환
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로깅
            return false; // 업로드 중 에러가 발생하면 false 반환
        }
    }

    @PostMapping("/{profileId}/achivements")
    public Profile addAchivementToProfile(@PathVariable Long profileId, @RequestBody Achievement achievement) {
        return profileService.addAchivementToProfile(profileId, achievement);
    }

    @PutMapping("/{profileId}/achievements/{achievementId}")
    public Profile updateAchievement(@PathVariable Long profileId, @PathVariable Long achievementId, @RequestBody Achievement updatedAchievement) {
        return profileService.updateAchievement(profileId, achievementId, updatedAchievement);
    }

    @DeleteMapping("/{profileId}/achievements/{achievementId}")
    public Profile deleteAchievement(@PathVariable Long profileId, @PathVariable Long achievementId) {
        return profileService.deleteAchievement(profileId, achievementId);
    }

    @PostMapping("/{profileId}/interests")
    public Profile addInterestToProfile(@PathVariable Long profileId, @RequestBody Interest interest) {
        return profileService.addInterestToProfile(profileId, interest);
    }

    @PutMapping("/{profileId}/interests/{interestId}")
    public Profile updateInterest(@PathVariable Long profileId, @PathVariable Long interestId, @RequestBody Interest updatedInterest) {
        return profileService.updateInterest(profileId, interestId, updatedInterest);
    }

    @DeleteMapping("/{profileId}/interests/{interestId}")
    public Profile deleteInterest(@PathVariable Long profileId, @PathVariable Long interestId) {
        return profileService.deleteInterest(profileId, interestId);
    }

    @PostMapping("/{profileId}/languages")
    public Profile addInterestToProfile(@PathVariable Long profileId, @RequestBody Language language) {
        return profileService.addLanguageToProfile(profileId, language);
    }

    @PutMapping("/{profileId}/languages/{languageId}")
    public Profile updateLanguage(@PathVariable Long profileId, @PathVariable Long languageId, @RequestBody Language updatedLanguage) {
        return profileService.updateLanguage(profileId, languageId, updatedLanguage);
    }

    @DeleteMapping("/{profileId}/languages/{languageId}")
    public Profile deleteLanguage(@PathVariable Long profileId, @PathVariable Long languageId) {
        return profileService.deleteLanguage(profileId, languageId);
    }

    @PostMapping("/{profileId}/savedPost")
    public Profile addSavedPostToProfile(@PathVariable Long profileId, @RequestBody SavedPost savedPost) {
        return profileService.addSavedPostToProfile(profileId, savedPost);
    }

    @PutMapping("/{profileId}/savedPosts/{postId}")
    public Profile updateSavedPost(@PathVariable Long profileId, @PathVariable Long postId, @RequestBody SavedPost updatedSavedPost) {
        return profileService.updateSavedPost(profileId, postId, updatedSavedPost);
    }

    @DeleteMapping("/{profileId}/savedPosts/{postId}")
    public Profile deleteSavedPost(@PathVariable Long profileId, @PathVariable Long postId) {
        return profileService.deleteSavedPost(profileId, postId);
    }

}
