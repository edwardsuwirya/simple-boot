package com.enigmacamp.hellospring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedEntityGraph(name = "CourseType_With_Course_And_Info",
        attributeNodes = {
                @NamedAttributeNode(
                        value = "courseList",
                        subgraph = "info-subgraph"
                )
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "info-subgraph",
                        attributeNodes =
                                {
                                        @NamedAttributeNode("courseInfo")
                                }
                )
        }
)
@Table(name = "mst_course_type")
public class CourseType {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "course_type_id")
    private String courseTypeId;

    @Column(name = "type_name", nullable = false, length = 150, unique = true)
    private String typeName;

    //Default Lazy
//    @OneToMany(mappedBy = "courseType",fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "courseType")
    @JsonIgnoreProperties({"courseType"})
    private List<Course> courseList;

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return "CourseType{" +
                "courseTypeId='" + courseTypeId + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }

}
