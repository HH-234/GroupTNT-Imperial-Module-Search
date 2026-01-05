package com.zds.bioengtsnapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.bioengtsnapp.domain.Users;
import com.zds.bioengtsnapp.domain.PhoneNumbers;
import com.zds.bioengtsnapp.domain.Addresses;
import com.zds.bioengtsnapp.dto.UserDetailDTO;
import com.zds.bioengtsnapp.service.UsersService;
import com.zds.bioengtsnapp.mapper.UsersMapper;
import com.zds.bioengtsnapp.mapper.PhoneNumbersMapper;
import com.zds.bioengtsnapp.mapper.AddressesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author 33882
* @description 针对表【users】的数据库操作Service实现
* @createDate 2026-01-02 23:18:37
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService {

    @Autowired
    private PhoneNumbersMapper phoneNumbersMapper;

    @Autowired
    private AddressesMapper addressesMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Users> getUsersByFullName(String fullName) {
        return lambdaQuery().like(Users::getFullName, "%" + fullName.toLowerCase() + "%").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDetailDTO> getUserDetailsByFullName(String fullName) {
        // 先查询用户基本信息
        List<Users> users = getUsersByFullName(fullName);
        
        if (users.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 收集所有用户ID
        List<Integer> userIds = new ArrayList<>();
        for (Users user : users) {
            userIds.add(user.getId());
        }
        
        // 批量查询手机号码
        Map<Integer, List<UserDetailDTO.PhoneNumberDTO>> phoneMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<PhoneNumbers> phones = phoneNumbersMapper.selectList(
                new LambdaQueryWrapper<PhoneNumbers>().in(PhoneNumbers::getUserId, userIds)
            );
            for (PhoneNumbers phone : phones) {
                phoneMap.computeIfAbsent(phone.getUserId(), k -> new ArrayList<>())
                    .add(convertToPhoneDTO(phone));
            }
        }
        
        // 批量查询地址
        Map<Integer, List<UserDetailDTO.AddressDTO>> addressMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<Addresses> addresses = addressesMapper.selectList(
                new LambdaQueryWrapper<Addresses>().in(Addresses::getUserId, userIds)
            );
            for (Addresses address : addresses) {
                addressMap.computeIfAbsent(address.getUserId(), k -> new ArrayList<>())
                    .add(convertToAddressDTO(address));
            }
        }
        
        // 组装结果
        List<UserDetailDTO> result = new ArrayList<>();
        for (Users user : users) {
            UserDetailDTO dto = convertToUserDetailDTO(user);
            dto.setPhoneNumbers(phoneMap.getOrDefault(user.getId(), new ArrayList<>()));
            dto.setAddresses(addressMap.getOrDefault(user.getId(), new ArrayList<>()));
            result.add(dto);
        }
        
        return result;
    }
    
    /**
     * 转换 Users 为 UserDetailDTO
     */
    private UserDetailDTO convertToUserDetailDTO(Users user) {
        UserDetailDTO dto = new UserDetailDTO();
        dto.setId(user.getId());
        
        // 处理 discovery_url_id，确保是完整URL格式
        String discoveryUrlId = user.getDiscoveryUrlId();
        if (discoveryUrlId != null && !discoveryUrlId.isEmpty()) {
            if (discoveryUrlId.startsWith("https://")) {
                dto.setDiscoveryUrlId(discoveryUrlId);
            } else {
                dto.setDiscoveryUrlId("https://profiles.imperial.ac.uk/" + discoveryUrlId);
            }
        } else {
            dto.setDiscoveryUrlId(null);
        }
        
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setFullName(user.getFullName());
        dto.setEmailAddress(user.getEmailAddress());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
    
    /**
     * 转换 PhoneNumbers 为 PhoneNumberDTO
     */
    private UserDetailDTO.PhoneNumberDTO convertToPhoneDTO(PhoneNumbers phone) {
        UserDetailDTO.PhoneNumberDTO dto = new UserDetailDTO.PhoneNumberDTO();
        dto.setId(phone.getId());
        dto.setTypeDisplayName(phone.getTypeDisplayName());
        dto.setNumber(phone.getNumber());
        return dto;
    }
    
    /**
     * 转换 Addresses 为 AddressDTO
     */
    private UserDetailDTO.AddressDTO convertToAddressDTO(Addresses address) {
        UserDetailDTO.AddressDTO dto = new UserDetailDTO.AddressDTO();
        dto.setId(address.getId());
        dto.setCountryCode(address.getCountryCode());
        dto.setSingleLineFormat(address.getSingleLineFormat());
        dto.setStreetAddress(address.getStreetAddress());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());
        return dto;
    }
}




