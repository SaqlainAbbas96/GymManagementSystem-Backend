package com.solution.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum Gender {

    Male("Male"),
    Female("Female"),
    Other("Other");

    private String title;
    public static final Map<Integer, String> keyValues = new HashMap<>();

    static {
        for(Gender type : Gender.values()){
            keyValues.put(type.ordinal(), type.title);
        }
    }
}
