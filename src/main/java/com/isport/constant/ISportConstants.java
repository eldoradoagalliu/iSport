package com.isport.constant;

public class ISportConstants {

    public static final String YEAR = "year";
    public static final String DATE = "date";
    public static final String TIME = "time";

    public static final String ID = "id";
    public static final String USER = "user";
    public static final String CURRENT_USER = "currentUser";
    public static final String USERS = "users";
    public static final String ROLES = "roles";
    public static final String EVENT = "event";
    public static final String EVENTS = "events";
    public static final String FUTURE_EVENTS = "futureEvents";
    public static final String MESSAGE = "message";
    public static final String MESSAGES = "messages";

    public static final String SELECT_ALL_ROLES = "SELECT * FROM `roles`";
    public static final String INSERT_ROLE = "INSERT INTO roles (name) VALUES (?)";

    public static final String INVALID_CREDENTIALS = "Invalid Credentials, Please try again.";
    public static final String SUCCESSFUL_LOGOUT = "Logout Successful!";

    public static final String EMAIL_USED = "This email has been used by another user!";

    public static final String REUSED_OLD_PASSWORD = "You are using the old password! Please, try a new one.";
    public static final String INCORRECT_OLD_PASSWORD = "Your old password is incorrect";
    public static final String SUCCESSFUL_PASSWORD_CHANGE = "Password changed successfully!";
    public static final String REQUIRED_PASSWORDS = "The old password and new password are required!";

    public static final String REQUIRED_EVENT_DATETIME = "Event Date and Time are required!";
    public static final String FUTURE_EVENT_DATETIME = "The event date and time must be a date in the future!";
}
