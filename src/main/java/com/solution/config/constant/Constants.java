package com.solution.config.constant;

public class Constants {

    // Email Format
    public static final String EMAIL_FORMAT = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

    // Regex For Validations
    public static final String ALPHABETS = "[a-zA-Z ]*";
    public static final String CELL_FORMAT = "[+][0-9]{11,14}$";
    public static final String DATE_FORMAT = "^([0-9]{4})(/)((0)[1-9]|(1)[0-2])(/)((3)[0-1]|(0)[1-9]|[1-2][0-9])$";

    //Error Messages
    public static final String INVALID_DATE_FORMAT = "Date format should be in YYYY/MM/DD";

    // Following are the URL Path used in all controllers request & Test Cases
    public static final String GET_ALL_URL = "/get/all";
    public static final String SAVE_URL = "/save";
    public static final String UPDATE_URL = "/update/{id}";
    public static final String FIND_URL = "/find/{id}";
    public static final String DELETE_URL = "/delete/{id}";
}
