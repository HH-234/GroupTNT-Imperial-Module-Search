package com.zds.bioengtsnapp.mapper;

import com.zds.bioengtsnapp.domain.Users;
import com.zds.bioengtsnapp.dto.UserDetailDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 33882
* @description 针对表【users】的数据库操作Mapper
* @createDate 2026-01-02 23:18:37
* @Entity com.zds.bioengtsnapp.domain.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

    /**
     * 根据全名查询用户详细信息（包含手机号码和地址）
     * @param fullName 用户全名
     * @return 用户详细信息列表
     */
    List<UserDetailDTO> getUserDetailsByFullName(@Param("fullName") String fullName);
}




