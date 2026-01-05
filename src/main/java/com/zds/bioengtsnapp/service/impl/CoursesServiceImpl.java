package com.zds.bioengtsnapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zds.bioengtsnapp.domain.Courses;
import com.zds.bioengtsnapp.domain.CourseModules;
import com.zds.bioengtsnapp.dto.CourseDetailDTO;
import com.zds.bioengtsnapp.service.CoursesService;
import com.zds.bioengtsnapp.mapper.CoursesMapper;
import com.zds.bioengtsnapp.mapper.CourseModulesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author 33882
* @description 针对表【courses】的数据库操作Service实现
* @createDate 2026-01-02 23:18:31
*/
@Service
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses>
    implements CoursesService{

    @Autowired
    private CourseModulesMapper courseModulesMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CourseDetailDTO> getCourseDetailsByCourseName(String courseName) {
        // 第一步：通过 course_name 查询 courses 表，获取所有字段
        // PostgreSQL 默认区分大小写，使用 LOWER() 函数确保忽略大小写匹配
        List<Courses> courses = lambdaQuery()
            .apply(courseName != null && !courseName.isEmpty(), "LOWER(course_name) LIKE {0}", "%" + courseName.toLowerCase() + "%")
            .list();
        
        return fillModulesAndConvert(courses);
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<CourseDetailDTO> getCourseDetailsByCourseNamePage(int page, int size, String courseName) {
        // 1. 分页查询 Courses
        Page<Courses> coursesPage = new Page<>(page, size);
        
        // PostgreSQL 默认区分大小写，使用 LOWER() 函数确保忽略大小写匹配
        IPage<Courses> resultPage = lambdaQuery()
                .apply(courseName != null && !courseName.isEmpty(), "LOWER(course_name) LIKE {0}", "%" + courseName.toLowerCase() + "%")
                .page(coursesPage);

        List<Courses> courses = resultPage.getRecords();
        if (courses.isEmpty()) {
            // 如果没有记录，直接返回空的DTO分页对象
            Page<CourseDetailDTO> emptyPage = new Page<>(page, size);
            emptyPage.setTotal(resultPage.getTotal());
            return emptyPage;
        }

        // 2. 填充模块信息并转换
        List<CourseDetailDTO> dtoList = fillModulesAndConvert(courses);

        // 3. 构造返回的分页对象
        Page<CourseDetailDTO> dtoPage = new Page<>(page, size);
        dtoPage.setRecords(dtoList);
        dtoPage.setTotal(resultPage.getTotal());
        dtoPage.setPages(resultPage.getPages());
        
        return dtoPage;
    }

    private List<CourseDetailDTO> fillModulesAndConvert(List<Courses> courses) {
        if (courses.isEmpty()) {
            return new ArrayList<>();
        }

        // 收集所有课程ID
        List<Integer> courseIds = new ArrayList<>();
        for (Courses course : courses) {
            courseIds.add(course.getId());
        }

        // 第二步：根据 courses.id 去 course_modules 表查询所有 course_id 对应的记录
        Map<Integer, List<CourseDetailDTO.CourseModuleDTO>> moduleMap = new HashMap<>();
        if (!courseIds.isEmpty()) {
            List<CourseModules> modules = courseModulesMapper.selectList(
                new LambdaQueryWrapper<CourseModules>().in(CourseModules::getCourseId, courseIds)
            );
            for (CourseModules module : modules) {
                moduleMap.computeIfAbsent(module.getCourseId(), k -> new ArrayList<>())
                    .add(convertToModuleDTO(module));
            }
        }

        // 组装结果
        List<CourseDetailDTO> result = new ArrayList<>();
        for (Courses course : courses) {
            CourseDetailDTO dto = convertToCourseDetailDTO(course);
            dto.setCourseModules(moduleMap.getOrDefault(course.getId(), new ArrayList<>()));
            result.add(dto);
        }
        return result;
    }
    
    /**
     * 转换 Courses 为 CourseDetailDTO（包含 courses 表的所有字段）
     */
    private CourseDetailDTO convertToCourseDetailDTO(Courses course) {
        CourseDetailDTO dto = new CourseDetailDTO();
        dto.setId(course.getId());
        dto.setCourseName(course.getCourseName());
        dto.setCourseUrl(course.getCourseUrl());
        dto.setQualification(course.getQualification());
        dto.setDuration(course.getDuration());
        dto.setStartDate(course.getStartDate());
        dto.setUcasCode(course.getUcasCode());
        dto.setStudyMode(course.getStudyMode());
        dto.setFeeHome(course.getFeeHome());
        dto.setFeeOverseas(course.getFeeOverseas());
        dto.setDeliveredBy(course.getDeliveredBy());
        dto.setLocation(course.getLocation());
        dto.setApplicationsPlaces(course.getApplicationsPlaces());
        dto.setEntryRequirementAlevel(course.getEntryRequirementAlevel());
        dto.setEntryRequirementIb(course.getEntryRequirementIb());
        dto.setDescription(course.getDescription());
        dto.setCreatedAt(course.getCreatedAt());
        dto.setUpdatedAt(course.getUpdatedAt());
        return dto;
    }
    
    /**
     * 转换 CourseModules 为 CourseModuleDTO（包含 course_modules 表的所有字段）
     */
    private CourseDetailDTO.CourseModuleDTO convertToModuleDTO(CourseModules module) {
        CourseDetailDTO.CourseModuleDTO dto = new CourseDetailDTO.CourseModuleDTO();
        dto.setId(module.getId());
        dto.setCourseId(module.getCourseId());
        dto.setYearNumber(module.getYearNumber());
        dto.setModuleType(module.getModuleType());
        dto.setModuleName(module.getModuleName());
        return dto;
    }
}




