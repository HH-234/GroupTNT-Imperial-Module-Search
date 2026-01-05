package com.zds.bioengtsnapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.bioengtsnapp.domain.Courses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoursesMapper extends BaseMapper<Courses> {
    List<Courses> selectByCourseName(@Param("courseName") String courseName);
}

