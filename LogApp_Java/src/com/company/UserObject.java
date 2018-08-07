package com.company;

public class UserObject {

    private long timestamp;
    private long userId;
    private String message;

    public UserObject(long timestamp, long userId, String message) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
