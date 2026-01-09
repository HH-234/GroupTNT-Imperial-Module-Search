package com.zds.bioengtsnapp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.bioengtsnapp.domain.Courses;
import com.zds.bioengtsnapp.domain.CourseModules;
import com.zds.bioengtsnapp.dto.CourseDetailDTO;
import com.zds.bioengtsnapp.mapper.CoursesMapper;
import com.zds.bioengtsnapp.mapper.CourseModulesMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursesService {

    @Autowired
    private CoursesMapper coursesMapper;

    @Autowired
    private CourseModulesMapper courseModulesMapper;

    public IPage<CourseDetailDTO> getCourseDetailsByCourseNamePage(int page, int size, String courseName) {
        Page<Courses> coursesPage = new Page<>(page, size);
        
        LambdaQueryWrapper<Courses> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(courseName)) {
            queryWrapper.like(Courses::getCourseName, courseName);
        }
        queryWrapper.orderByDesc(Courses::getCreatedAt);
        
        IPage<Courses> coursesIPage = coursesMapper.selectPage(coursesPage, queryWrapper);
        
        // Convert to CourseDetailDTO
        Page<CourseDetailDTO> dtoPage = new Page<>(page, size);
        BeanUtils.copyProperties(coursesIPage, dtoPage);
        
        List<CourseDetailDTO> dtoList = coursesIPage.getRecords().stream()
                .map(course -> {
                    CourseDetailDTO dto = new CourseDetailDTO();
                    BeanUtils.copyProperties(course, dto);
                    
                    // Query modules
                    List<CourseModules> modules = courseModulesMapper.selectByCourseId(course.getId());
                    List<CourseDetailDTO.ModuleDTO> moduleDTOs = modules.stream()
                            .map(module -> {
                                CourseDetailDTO.ModuleDTO moduleDTO = new CourseDetailDTO.ModuleDTO();
                                BeanUtils.copyProperties(module, moduleDTO);
                                return moduleDTO;
                            })
                            .collect(Collectors.toList());
                    dto.setModules(moduleDTOs);
                    
                    return dto;
                })
                .collect(Collectors.toList());
        
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }
}

