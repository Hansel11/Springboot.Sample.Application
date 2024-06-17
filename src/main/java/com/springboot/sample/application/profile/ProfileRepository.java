package com.springboot.sample.application.profile;

import com.springboot.sample.application.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByDataStatusNot(Character dataStatus);
    Profile findByUserAndDataStatusNot(User user, Character dataStatus);
    Profile findByIdAndDataStatusNot(Long id, Character dataStatus);
}
