package com.zds.bioengtsnapp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CourseDetailDTO {
    private Long id;
    private String courseName;
    private String courseUrl;
    private String qualification;
    private String duration;
    private LocalDateTime startDate;
    private String ucasCode;
    private String studyMode;
    private String feeHome;
    private String feeOverseas;
    private String deliveredBy;
    private String location;
    private String applicationsPlaces;
    private String entryRequirementAlevel;
    private String entryRequirementIb;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ModuleDTO> modules;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getUcasCode() {
        return ucasCode;
    }

    public void setUcasCode(String ucasCode) {
        this.ucasCode = ucasCode;
    }

    public String getStudyMode() {
        return studyMode;
    }

    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }

    public String getFeeHome() {
        return feeHome;
    }

    public void setFeeHome(String feeHome) {
        this.feeHome = feeHome;
    }

    public String getFeeOverseas() {
        return feeOverseas;
    }

    public void setFeeOverseas(String feeOverseas) {
        this.feeOverseas = feeOverseas;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getApplicationsPlaces() {
        return applicationsPlaces;
    }

    public void setApplicationsPlaces(String applicationsPlaces) {
        this.applicationsPlaces = applicationsPlaces;
    }

    public String getEntryRequirementAlevel() {
        return entryRequirementAlevel;
    }

    public void setEntryRequirementAlevel(String entryRequirementAlevel) {
        this.entryRequirementAlevel = entryRequirementAlevel;
    }

    public String getEntryRequirementIb() {
        return entryRequirementIb;
    }

    public void setEntryRequirementIb(String entryRequirementIb) {
        this.entryRequirementIb = entryRequirementIb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ModuleDTO> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDTO> modules) {
        this.modules = modules;
    }

    public static class ModuleDTO {
        private Long id;
        private Integer yearNumber;
        private String moduleType;
        private String moduleName;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getYearNumber() {
            return yearNumber;
        }

        public void setYearNumber(Integer yearNumber) {
            this.yearNumber = yearNumber;
        }

        public String getModuleType() {
            return moduleType;
        }

        public void setModuleType(String moduleType) {
            this.moduleType = moduleType;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }
    }
}

