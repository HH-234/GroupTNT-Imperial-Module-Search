package com.zds.bioengtsnapp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName courses
 */
@TableName(value ="courses")
@Data
public class Courses {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String courseName;

    /**
     * 
     */
    private String courseUrl;

    /**
     * 
     */
    private String qualification;

    /**
     * 
     */
    private String duration;

    /**
     * 
     */
    private String startDate;

    /**
     * 
     */
    private String ucasCode;

    /**
     * 
     */
    private String studyMode;

    /**
     * 
     */
    private String feeHome;

    /**
     * 
     */
    private String feeOverseas;

    /**
     * 
     */
    private String deliveredBy;

    /**
     * 
     */
    private String location;

    /**
     * 
     */
    private String applicationsPlaces;

    /**
     * 
     */
    private String entryRequirementAlevel;

    /**
     * 
     */
    private String entryRequirementIb;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 
     */
    private Date updatedAt;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Courses other = (Courses) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCourseName() == null ? other.getCourseName() == null : this.getCourseName().equals(other.getCourseName()))
            && (this.getCourseUrl() == null ? other.getCourseUrl() == null : this.getCourseUrl().equals(other.getCourseUrl()))
            && (this.getQualification() == null ? other.getQualification() == null : this.getQualification().equals(other.getQualification()))
            && (this.getDuration() == null ? other.getDuration() == null : this.getDuration().equals(other.getDuration()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getUcasCode() == null ? other.getUcasCode() == null : this.getUcasCode().equals(other.getUcasCode()))
            && (this.getStudyMode() == null ? other.getStudyMode() == null : this.getStudyMode().equals(other.getStudyMode()))
            && (this.getFeeHome() == null ? other.getFeeHome() == null : this.getFeeHome().equals(other.getFeeHome()))
            && (this.getFeeOverseas() == null ? other.getFeeOverseas() == null : this.getFeeOverseas().equals(other.getFeeOverseas()))
            && (this.getDeliveredBy() == null ? other.getDeliveredBy() == null : this.getDeliveredBy().equals(other.getDeliveredBy()))
            && (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
            && (this.getApplicationsPlaces() == null ? other.getApplicationsPlaces() == null : this.getApplicationsPlaces().equals(other.getApplicationsPlaces()))
            && (this.getEntryRequirementAlevel() == null ? other.getEntryRequirementAlevel() == null : this.getEntryRequirementAlevel().equals(other.getEntryRequirementAlevel()))
            && (this.getEntryRequirementIb() == null ? other.getEntryRequirementIb() == null : this.getEntryRequirementIb().equals(other.getEntryRequirementIb()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCourseName() == null) ? 0 : getCourseName().hashCode());
        result = prime * result + ((getCourseUrl() == null) ? 0 : getCourseUrl().hashCode());
        result = prime * result + ((getQualification() == null) ? 0 : getQualification().hashCode());
        result = prime * result + ((getDuration() == null) ? 0 : getDuration().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getUcasCode() == null) ? 0 : getUcasCode().hashCode());
        result = prime * result + ((getStudyMode() == null) ? 0 : getStudyMode().hashCode());
        result = prime * result + ((getFeeHome() == null) ? 0 : getFeeHome().hashCode());
        result = prime * result + ((getFeeOverseas() == null) ? 0 : getFeeOverseas().hashCode());
        result = prime * result + ((getDeliveredBy() == null) ? 0 : getDeliveredBy().hashCode());
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getApplicationsPlaces() == null) ? 0 : getApplicationsPlaces().hashCode());
        result = prime * result + ((getEntryRequirementAlevel() == null) ? 0 : getEntryRequirementAlevel().hashCode());
        result = prime * result + ((getEntryRequirementIb() == null) ? 0 : getEntryRequirementIb().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", courseName=").append(courseName);
        sb.append(", courseUrl=").append(courseUrl);
        sb.append(", qualification=").append(qualification);
        sb.append(", duration=").append(duration);
        sb.append(", startDate=").append(startDate);
        sb.append(", ucasCode=").append(ucasCode);
        sb.append(", studyMode=").append(studyMode);
        sb.append(", feeHome=").append(feeHome);
        sb.append(", feeOverseas=").append(feeOverseas);
        sb.append(", deliveredBy=").append(deliveredBy);
        sb.append(", location=").append(location);
        sb.append(", applicationsPlaces=").append(applicationsPlaces);
        sb.append(", entryRequirementAlevel=").append(entryRequirementAlevel);
        sb.append(", entryRequirementIb=").append(entryRequirementIb);
        sb.append(", description=").append(description);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}