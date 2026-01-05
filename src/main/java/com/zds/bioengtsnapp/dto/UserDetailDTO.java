package com.zds.bioengtsnapp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserDetailDTO {
    private Long id;
    private String discoveryUrlId;
    private String avatarUrl;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PhoneNumberDTO> phoneNumbers;
    private List<AddressDTO> addresses;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscoveryUrlId() {
        return discoveryUrlId;
    }

    public void setDiscoveryUrlId(String discoveryUrlId) {
        this.discoveryUrlId = discoveryUrlId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<PhoneNumberDTO> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumberDTO> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public static class PhoneNumberDTO {
        private Long id;
        private String typeDisplayName;
        private String number;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

    public static class AddressDTO {
        private Long id;
        private String countryCode;
        private String singleLineFormat;
        private String streetAddress;
        private String city;
        private String state;
        private String country;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getSingleLineFormat() {
            return singleLineFormat;
        }

        public void setSingleLineFormat(String singleLineFormat) {
            this.singleLineFormat = singleLineFormat;
        }

        public String getStreetAddress() {
            return streetAddress;
        }

        public void setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}

