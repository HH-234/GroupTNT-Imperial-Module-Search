package com.zds.bioengtsnapp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.bioengtsnapp.domain.Users;
import com.zds.bioengtsnapp.dto.UserDetailDTO;
import com.zds.bioengtsnapp.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;

    public List<Users> getUsersByFullName(String fullName) {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(fullName)) {
            queryWrapper.like(Users::getFullName, fullName);
        }
        return usersMapper.selectList(queryWrapper);
    }

    public IPage<UserDetailDTO> getUserDetailsByFullNamePage(int page, int size, String fullName) {
        // 使用 MyBatis 查询获取详细信息
        List<UserDetailDTO> allDetails;
        if (StringUtils.hasText(fullName)) {
            allDetails = usersMapper.getUserDetailsByFullName(fullName);
        } else {
            // 如果没有提供名称，返回空列表或所有用户（根据业务需求）
            allDetails = Collections.emptyList();
        }
        
        // 手动分页
        int total = allDetails.size();
        int start = (page - 1) * size;
        int end = Math.min(start + size, total);
        
        List<UserDetailDTO> pagedList = start < total ? allDetails.subList(start, end) : Collections.emptyList();
        
        // 合并相同用户的 phoneNumbers 和 addresses
        Map<Long, UserDetailDTO> userMap = pagedList.stream()
                .collect(Collectors.toMap(
                        UserDetailDTO::getId,
                        dto -> dto,
                        (existing, replacement) -> {
                            // 合并逻辑：如果同一个用户出现多次，合并 phoneNumbers 和 addresses
                            if (existing.getPhoneNumbers() != null && replacement.getPhoneNumbers() != null) {
                                existing.getPhoneNumbers().addAll(replacement.getPhoneNumbers());
                            } else if (replacement.getPhoneNumbers() != null) {
                                existing.setPhoneNumbers(replacement.getPhoneNumbers());
                            }
                            
                            if (existing.getAddresses() != null && replacement.getAddresses() != null) {
                                existing.getAddresses().addAll(replacement.getAddresses());
                            } else if (replacement.getAddresses() != null) {
                                existing.setAddresses(replacement.getAddresses());
                            }
                            return existing;
                        }
                ));
        
        List<UserDetailDTO> mergedList = userMap.values().stream()
                .collect(Collectors.toList());
        
        Page<UserDetailDTO> dtoPage = new Page<>(page, size, total);
        dtoPage.setRecords(mergedList);
        return dtoPage;
    }
}

