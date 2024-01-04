package com.ooad.dormitory.interceptor;

import java.sql.Timestamp;

@Deprecated
public class Token {
    private String token;
    private Timestamp dueTime;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Timestamp getDueTime() {
        return dueTime;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setDueTime(Timestamp dueTime) {
        this.dueTime = dueTime;
    }
}
