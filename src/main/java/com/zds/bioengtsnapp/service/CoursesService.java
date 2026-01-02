package com.zds.bioengtsnapp.service;

import com.zds.bioengtsnapp.domain.Courses;
import com.zds.bioengtsnapp.dto.CourseDetailDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 33882
* @description 针对表【courses】的数据库操作Service
* @createDate 2026-01-02 23:18:31
*/
public interface CoursesService extends IService<Courses> {

    /**
     * 根据课程名称查询课程详细信息（包含课程模块）
     * @param courseName 课程名称
     * @return 课程详细信息列表
     */
    List<CourseDetailDTO> getCourseDetailsByCourseName(String courseName);
}
