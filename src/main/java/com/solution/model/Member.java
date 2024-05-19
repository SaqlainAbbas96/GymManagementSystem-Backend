package com.solution.model;

import com.solution.config.constant.Constants;
import com.solution.model.enums.Gender;
import com.solution.model.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Pattern;

@Table(name = "tblmember")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = Constants.ALPHABETS, message = "Name should contain only alphabets")
    private String name;

    @Pattern(regexp = Constants.EMAIL_FORMAT, message = "Invalid Email Format Entered")
    private String email;

    @Pattern(regexp="("+Constants.CELL_FORMAT+")?", message = "Phone Number should be at-least 11 digits and at-most 14 digits long")
    private String mobile;

    private String cardNo;

    private String address;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Gender gender;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private MaritalStatus maritalStatus;

    @Pattern(regexp = Constants.DATE_FORMAT, message = Constants.INVALID_DATE_FORMAT)
    private String joiningDate;

    private Boolean status;
}