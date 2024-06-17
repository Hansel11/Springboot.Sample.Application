package com.springboot.sample.application.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ModelMapper mapper = new ModelMapper();

    public User findUserById(Long id){
        return userRepository.findByIdAndDataStatusNot(id, 'D').orElseThrow(
                () -> new RuntimeException("User not found!")
        );
    }
    public User findUserByUsername(String username){
        return userRepository.findByUsernameAndDataStatusNot(username, 'D').orElseThrow(
                () -> new RuntimeException("User not found!")
        );
    }

    public List<UserDto> getAllUser(){

        List<User> users = userRepository.findByDataStatusNot('D');
        return users.stream().map(
                user -> mapper.map(user, UserDto.class)
        ).toList();
    }

    public UserDto getUserById(Long id){
        return mapper.map(findUserById(id), UserDto.class);
    }

    private void validateNewUser(UserDto dto, Long userId) {
        if(userRepository.findByUsernameAndIdNotAndDataStatusNot(dto.getUsername(), userId, 'D').isPresent()) {
            throw new RuntimeException("User already exist");
        }
        if(userRepository.findByEmailAndIdNotAndDataStatusNot(dto.getEmail(), userId, 'D').isPresent()) {
            throw new RuntimeException("Email is already taken");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username);
    }

    public UserDto addUser(UserDto request, String createdBy) {
        validateNewUser(request, (long) -1);
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())

                .createdAt(LocalDate.now())
                .createdBy(createdBy)
                .dataStatus('A')
                .build();
        return mapper.map(userRepository.save(newUser), UserDto.class);
    }

    public UserDto editUser(Long userId, UserDto request, String updatedBy){
        validateNewUser(request, userId);
        User user = findUserById(userId);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());

        user.setUpdatedAt(LocalDate.now());
        user.setUpdatedBy(updatedBy);
        return mapper.map(userRepository.save(user), UserDto.class);
    }

    public String deleteUser(Long userId, String deletedBy){
        User user = findUserById(userId);
        user.setDataStatus('D');
        user.setUpdatedBy(deletedBy);
        user.setUpdatedAt(LocalDate.now());
        userRepository.save(user);
        return "User successfully deleted!";
    }
}
