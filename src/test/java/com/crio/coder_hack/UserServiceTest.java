package com.crio.coder_hack;

import com.crio.coder_hack.dto.SignupDto;
import com.crio.coder_hack.dto.UpdateScoreDto;
import com.crio.coder_hack.dto.UserDto;
import com.crio.coder_hack.entity.enums.Badge;
import com.crio.coder_hack.exceptions.InvalidScoreException;
import com.crio.coder_hack.exceptions.ResourceNotFoundException;
import com.crio.coder_hack.exceptions.UsernameAlreadyExistsException;
import com.crio.coder_hack.repository.UserRepository;
import com.crio.coder_hack.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateNewUser() {
        SignupDto signupDto = new SignupDto("newUser");

        UserDto createdUser = userService.createNewUser(signupDto);

        assertNotNull(createdUser);
        assertEquals("newUser", createdUser.getUsername());
        assertEquals(0, createdUser.getScore());
        assertNotNull(createdUser.getBadges());
        assertTrue(createdUser.getBadges().isEmpty());
    }

    @Test
    public void testCreateNewUserUsernameAlreadyExists() {
        SignupDto firstSignupDto = new SignupDto("existingUser");
        userService.createNewUser(firstSignupDto);
        SignupDto secondSignupDto = new SignupDto("existingUser");

        Exception exception = assertThrows(UsernameAlreadyExistsException.class, () -> {
            userService.createNewUser(secondSignupDto);
        });

        String expectedMessage = "User with username existingUser already exists";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testUpdateUserScore() {
        SignupDto signupDto = new SignupDto("scoreUser");
        UserDto createdUser = userService.createNewUser(signupDto);

        UpdateScoreDto updateScoreDto = new UpdateScoreDto(60);
        UserDto updatedUser = userService.updateUserScore(createdUser.getId(), updateScoreDto);

        assertEquals(60, updatedUser.getScore());
        assertTrue(updatedUser.getBadges().contains(Badge.CODE_NINJA));
        assertTrue(updatedUser.getBadges().contains(Badge.CODE_CHAMP));
        assertTrue(updatedUser.getBadges().contains(Badge.CODE_MASTER));
    }

    @Test
    public void testUpdateUserScoreInvalidScore() {
        SignupDto signupDto = new SignupDto("scoreUser");
        UserDto createdUser = userService.createNewUser(signupDto);

        UpdateScoreDto updateScoreDto = new UpdateScoreDto(-10);

        Exception exception = assertThrows(InvalidScoreException.class, () -> {
            userService.updateUserScore(createdUser.getId(), updateScoreDto);
        });

        String expectedMessage = "Invalid score cannot be updated";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testGetUserById() {
        SignupDto signupDto = new SignupDto("singleUser");
        UserDto createdUser = userService.createNewUser(signupDto);

        UserDto fetchedUser = userService.getUserById(createdUser.getId());

        assertNotNull(fetchedUser);
        assertEquals(createdUser.getId(), fetchedUser.getId());
    }

    @Test
    public void testFindAllUsers() {
        SignupDto user1 = new SignupDto("user1");
        SignupDto user2 = new SignupDto("user2");
        userService.createNewUser(user1);
        userService.createNewUser(user2);

        List<UserDto> users = userService.findAllUsers();

        assertNotNull(users);
        assertTrue(users.size() >= 2);
    }

    @Test
    public void testRemoveExistingUser() {
        SignupDto signupDto = new SignupDto("userToRemove");
        UserDto createdUser = userService.createNewUser(signupDto);

        userService.removeExistingUser(createdUser.getId());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(createdUser.getId());
        });

        String expectedMessage = "User with id " + createdUser.getId() + " does not exists";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testRemoveNonExistingUser() {
        String nonExistingUserId = "nonExistingId";

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.removeExistingUser(nonExistingUserId);
        });

        String expectedMessage = "User with id " + nonExistingUserId + " does not exists";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
