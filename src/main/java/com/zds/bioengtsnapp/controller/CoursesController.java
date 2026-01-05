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

    @GetMapping("/courses/search")
    public List<CourseDetailDTO> searchByCourseName(@RequestParam String courseName) {
        return coursesService.getCourseDetailsByCourseName(courseName);
    }
}

