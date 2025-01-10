package com.crio.coder_hack.entity;


import com.crio.coder_hack.entity.enums.Badge;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="contest_user")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    @Field(name="username")
    private String username;
    private Integer score;

    private Set<Badge> badges;
}
