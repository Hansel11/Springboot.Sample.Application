package com.springboot.sample.application.profile;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileDto {
    private String username;
    private String displayName;
    private Date birthdate;
    private String birthplace;
}
