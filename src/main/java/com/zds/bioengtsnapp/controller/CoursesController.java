package com.zds.bioengtsnapp.controller;

import com.zds.bioengtsnapp.dto.CourseDetailDTO;
import com.zds.bioengtsnapp.service.CoursesService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoursesController {

    private final CoursesService coursesService;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping("/courses/search")
    public IPage<CourseDetailDTO> searchByCourseName(
            @RequestParam(required = false, defaultValue = "") String courseName,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return coursesService.getCourseDetailsByCourseNamePage(page, size, courseName);
    }
}

