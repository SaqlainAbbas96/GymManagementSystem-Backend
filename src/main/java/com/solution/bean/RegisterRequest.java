package com.solution.bean;

import com.solution.config.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String username;

    @Pattern(regexp = Constants.EMAIL_FORMAT, message = "Invalid Email Format Entered")
    private String email;

    private String password;
}
