package com.springboot.sample.application.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserById(@RequestParam @NotNull Long userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody @Valid UserDto user){
        return ResponseEntity.ok(userService.addUser(user, userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<UserDto> editUser(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestParam @NotNull Long userId,
                                         @RequestBody @Valid UserDto user){
        return ResponseEntity.ok(userService.editUser(userId, user, userDetails.getUsername()));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCustomer(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestParam @NotNull Long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId, userDetails.getUsername()));
    }


}
