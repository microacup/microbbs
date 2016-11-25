package me.micro.bbs.user.support;

import me.micro.bbs.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * Created by microacup on 2016/11/22.
 */
@Service
@Transactional
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    // TODO Cachable
    public UserProfile profile(Long userId) {
        UserProfile profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            profile = new UserProfile();
            profile.setUserId(userId);
            profileRepository.save(profile);
        }

        return profile;
    }


    // 更新PostCount
    public int addPostCount(Long userId) {
        return profileRepository.addPostCount(userId);
    }

    public int addReplyCount(Long userId) {
        return profileRepository.addReplyCount(userId);
    }
}
