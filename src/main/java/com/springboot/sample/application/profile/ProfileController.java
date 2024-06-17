package com.springboot.sample.application.profile;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileDto> getSelfProfile(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(profileService.getProfile(userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<ProfileDto> editProfile(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody @NotNull ProfileDto profileDto){
        return ResponseEntity.ok(profileService.editProfile(userDetails.getUsername(), profileDto));
    }


}
