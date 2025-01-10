package com.crio.coder_hack.controller;

import com.crio.coder_hack.dto.SignupDto;
import com.crio.coder_hack.dto.UpdateScoreDto;
import com.crio.coder_hack.dto.UserDto;
import com.crio.coder_hack.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody SignupDto signupDto){
        return new ResponseEntity<>(userService.createNewUser(signupDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}/score")
    public ResponseEntity<UserDto> updateScore(@PathVariable String userId, @RequestBody UpdateScoreDto updateScoreDto){
        return ResponseEntity.ok(userService.updateUserScore(userId,updateScoreDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId){
        userService.removeExistingUser(userId);
        return ResponseEntity.noContent().build();
    }
}
