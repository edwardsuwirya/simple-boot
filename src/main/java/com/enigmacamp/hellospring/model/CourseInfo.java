package com.enigmacamp.hellospring.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "mst_course_info")
public class CourseInfo {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "course_info_id")
    private String courseInfoId;

    @Column(name = "duration", nullable = false, length = 50)
    private String duration;

    @Column(name = "level", nullable = false, length = 10)
    private String level;

    @OneToOne(mappedBy = "courseInfo")
    private Course mstCourses;

    public Course getMstCourses() {
        return mstCourses;
    }

    public void setMstCourses(Course mstCourses) {
        this.mstCourses = mstCourses;
    }


    public String getCourseInfoId() {
        return courseInfoId;
    }

    public void setCourseInfoId(String courseInfoId) {
        this.courseInfoId = courseInfoId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "courseInfoId='" + courseInfoId + '\'' +
                ", duration='" + duration + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
