package com.zds.bioengtsnapp.service;

import com.zds.bioengtsnapp.domain.Users;
import com.zds.bioengtsnapp.dto.UserDetailDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
* @author 33882
* @description 针对表【users】的数据库操作Service
* @createDate 2026-01-02 23:18:37
*/
public interface UsersService extends IService<Users> {

    /**
     * 根据全名查询用户基本信息列表
     * @param fullName 用户全名
     * @return 用户列表
     */
    List<Users> getUsersByFullName(String fullName);

    /**
     * 根据全名查询用户详细信息（包含手机号码和地址）
     * @param fullName 用户全名
     * @return 用户详细信息列表
     */
    List<UserDetailDTO> getUserDetailsByFullName(String fullName);

    /**
     * 分页查询用户详细信息
     * @param page 当前页
     * @param size 每页大小
     * @param fullName 用户全名
     * @return 分页结果
     */
    IPage<UserDetailDTO> getUserDetailsByFullNamePage(int page, int size, String fullName);
}
