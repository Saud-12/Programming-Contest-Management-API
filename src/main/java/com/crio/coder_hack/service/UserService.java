package com.crio.coder_hack.service;

import com.crio.coder_hack.dto.SignupDto;
import com.crio.coder_hack.dto.UpdateScoreDto;
import com.crio.coder_hack.dto.UserDto;
import com.crio.coder_hack.entity.User;
import com.crio.coder_hack.entity.enums.Badge;
import com.crio.coder_hack.exceptions.InvalidBadgesException;
import com.crio.coder_hack.exceptions.InvalidScoreException;
import com.crio.coder_hack.exceptions.ResourceNotFoundException;
import com.crio.coder_hack.exceptions.UsernameAlreadyExistsException;
import com.crio.coder_hack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private static final Integer MAX_BADGES=3;

    public UserDto createNewUser(SignupDto signupDto){
        String username=signupDto.getUsername();
        if(userRepository.existsByUsername(username)){
            throw new UsernameAlreadyExistsException("User with username "+username+" already exists");
        }
        User user= User.builder()
                .username(username)
                .score(0)
                .badges(new HashSet<>())
                .build();

        user=userRepository.save(user);
        return modelMapper.map(user,UserDto.class);
    }

    public UserDto updateUserScore(String id, UpdateScoreDto updateScoreDto){
        int score=updateScoreDto.getScore();
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id "+id+" does not exists"));
        if(!(score>=0 && score<=100)){
            throw new InvalidScoreException("Invalid score cannot be updated");
        }
        user.setScore(score);
        Set<Badge> badges=user.getBadges();
        badges.clear();
        if(score>=1){
            badges.add(Badge.CODE_NINJA);
        }if(score>=30){
            badges.add(Badge.CODE_CHAMP);
        }if(score>=60){
            badges.add(Badge.CODE_MASTER);
        }
        if(badges.size()>MAX_BADGES){
            throw new InvalidBadgesException("Exceeded the Maximum number of badges a user can have");
        }
        user=userRepository.save(user);
        return modelMapper.map(user,UserDto.class);
    }
    public List<UserDto> findAllUsers(){
        return userRepository.findAll().stream()
                .sorted((user1,user2)->user2.getScore().compareTo(user1.getScore()))
                .map(user->modelMapper.map(user,UserDto.class))
                .collect(Collectors.toList());
    }

    public void removeExistingUser(String id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User with id "+id+" does not exists");
        }
        userRepository.deleteById(id);
    }
    public UserDto getUserById(String id){
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id "+id+" does not exists"));
        return modelMapper.map(user,UserDto.class);
    }
}
