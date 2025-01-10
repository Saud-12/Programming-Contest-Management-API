package com.crio.coder_hack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    @NotBlank(message = "Username cannot be blank or null")
    @Size(min=4,max=10)
    private String username;
}
