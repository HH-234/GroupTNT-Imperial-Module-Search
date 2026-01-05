package com.zds.bioengtsnapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.bioengtsnapp.domain.Users;
import com.zds.bioengtsnapp.dto.UserDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    List<Users> selectByFullName(@Param("fullName") String fullName);
    List<UserDetailDTO> getUserDetailsByFullName(@Param("fullName") String fullName);
}

