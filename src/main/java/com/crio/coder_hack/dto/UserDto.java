package com.crio.coder_hack.dto;

import com.crio.coder_hack.entity.enums.Badge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private Integer score;
    private Set<Badge> badges;
}
