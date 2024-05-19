package com.solution.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum MaritalStatus {

    Married("Married"),
    Single("Single"),
    Divorced("Divorced"),
    Widowed("Widowed");

    private String title;
    public static final Map<Integer, String> keyValues = new HashMap<>();

    static {
        for(MaritalStatus type : MaritalStatus.values()){
            keyValues.put(type.ordinal(), type.title);
        }
    }
}
