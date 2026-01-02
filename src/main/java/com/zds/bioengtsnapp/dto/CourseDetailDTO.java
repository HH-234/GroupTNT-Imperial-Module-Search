package com.zds.bioengtsnapp.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 课程详细信息 DTO
 * 包含课程基本信息和课程模块列表
 */
@Data
public class CourseDetailDTO {
    /**
     * 课程ID
     */
    private Integer id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程URL
     */
    private String courseUrl;

    /**
     * 资格认证
     */
    private String qualification;

    /**
     * 持续时间
     */
    private String duration;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * UCAS代码
     */
    private String ucasCode;

    /**
     * 学习模式
     */
    private String studyMode;

    /**
     * 本地费用
     */
    private String feeHome;

    /**
     * 海外费用
     */
    private String feeOverseas;

    /**
     * 授课机构
     */
    private String deliveredBy;

    /**
     * 位置
     */
    private String location;

    /**
     * 申请名额
     */
    private String applicationsPlaces;

    /**
     * A-Level入学要求
     */
    private String entryRequirementAlevel;

    /**
     * IB入学要求
     */
    private String entryRequirementIb;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 课程模块列表
     */
    private List<CourseModuleDTO> courseModules;

    /**
     * 课程模块 DTO
     */
    @Data
    public static class CourseModuleDTO {
        private Integer id;
        private Integer courseId;
        private Integer yearNumber;
        private String moduleType;
        private String moduleName;
    }
}

