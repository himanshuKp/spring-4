package com.himanshu.springpractice.entity;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@ToString
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Integer id;

    @Column(name = "courseName", nullable = false, length = 100)
    private String courseName;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "credits")
    private Integer credits;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Enrollment> enrollment;

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Set<Enrollment> getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Set<Enrollment> enrollment) {
        this.enrollment = enrollment;
    }
}