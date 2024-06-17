package com.springboot.sample.application.profile;

import com.springboot.sample.application.user.User;
import com.springboot.sample.application.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final ModelMapper mapper = new ModelMapper();

    public Profile getProfileByUsername(String username){
        User user = userService.findUserByUsername(username);

        // create a profile if it doesn't exist already
        Profile profile = profileRepository.findByUserAndDataStatusNot(user, 'D');
        if(profile == null){
            profile = profileRepository.save(Profile.builder()
                            .user(user)
                            .displayName(username)
                            .createdAt(LocalDate.now())
                            .dataStatus('A')
                            .build()
            );
        }
        return profile;

    }

    public ProfileDto getProfile(String username){
        Profile profile = getProfileByUsername(username);
        return mapper.map(profile, ProfileDto.class);
    }

    public ProfileDto editProfile(String username, ProfileDto request){
        Profile profile = getProfileByUsername(username);
        profile.setDisplayName(request.getDisplayName());
        profile.setBirthdate(request.getBirthdate());
        profile.setBirthplace(request.getBirthplace());
        profile.setUpdatedAt(LocalDate.now());
        profile.setUpdatedBy(username);
        return mapper.map(profileRepository.save(profile), ProfileDto.class);
    }
}
