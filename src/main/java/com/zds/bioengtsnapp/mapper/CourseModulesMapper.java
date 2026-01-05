package com.zds.bioengtsnapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.bioengtsnapp.domain.CourseModules;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseModulesMapper extends BaseMapper<CourseModules> {
    List<CourseModules> selectByCourseId(@Param("courseId") Long courseId);
}

