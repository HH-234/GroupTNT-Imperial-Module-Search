package com.zds.bioengtsnapp.domain;

public class PhoneNumbers {
    private Long id;
    private Long userId;
    private String typeDisplayName;
    private String number;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTypeDisplayName() {
        return typeDisplayName;
    }

    public void setTypeDisplayName(String typeDisplayName) {
        this.typeDisplayName = typeDisplayName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

