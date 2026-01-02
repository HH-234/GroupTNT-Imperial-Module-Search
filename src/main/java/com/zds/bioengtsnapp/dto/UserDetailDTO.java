package com.zds.bioengtsnapp.dto;

import lombok.Data;
import java.util.List;

/**
 * 用户详细信息 DTO
 * 包含用户基本信息、手机号码列表和地址列表
 */
@Data
public class UserDetailDTO {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * Discovery URL ID（完整URL格式：https://profiles.imperial.ac.uk/...）
     */
    private String discoveryUrlId;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 名字
     */
    private String firstName;

    /**
     * 姓氏
     */
    private String lastName;

    /**
     * 全名
     */
    private String fullName;

    /**
     * 邮箱地址
     */
    private String emailAddress;

    /**
     * 手机号码列表
     */
    private List<PhoneNumberDTO> phoneNumbers;

    /**
     * 地址列表
     */
    private List<AddressDTO> addresses;
    
    /**
     * 手机号码列表的 setter 方法
     */
    public void setPhoneNumbers(List<PhoneNumberDTO> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    
    /**
     * 手机号码列表的 getter 方法
     */
    public List<PhoneNumberDTO> getPhoneNumbers() {
        return this.phoneNumbers;
    }
    
    /**
     * 地址列表的 setter 方法
     */
    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
    
    /**
     * 地址列表的 getter 方法
     */
    public List<AddressDTO> getAddresses() {
        return this.addresses;
    }

    /**
     * 创建时间
     */
    private java.util.Date createdAt;

    /**
     * 更新时间
     */
    private java.util.Date updatedAt;

    /**
     * 手机号码 DTO
     */
    @Data
    public static class PhoneNumberDTO {
        private Integer id;
        private String typeDisplayName;
        private String number;
    }

    /**
     * 地址 DTO
     */
    @Data
    public static class AddressDTO {
        private Integer id;
        private String countryCode;
        private String singleLineFormat;
        private String streetAddress;
        private String city;
        private String state;
        private String country;
    }
}

