package com.zds.bioengtsnapp.controller;

import com.zds.bioengtsnapp.dto.CourseDetailDTO;
import com.zds.bioengtsnapp.service.CoursesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoursesController {

    private final CoursesService coursesService;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    /**
     * 根据课程名称查询课程详细信息（包含课程模块）
     * @param courseName 课程名称
     * @return 课程详细信息列表（包含课程和课程模块的所有数据）
     */
    @GetMapping("/courses/search")
    public List<CourseDetailDTO> searchByCourseName(@RequestParam String courseName) {
        return coursesService.getCourseDetailsByCourseName(courseName);
    }
}

