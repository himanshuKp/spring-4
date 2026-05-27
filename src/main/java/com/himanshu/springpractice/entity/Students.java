package com.himanshu.springpractice.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@ToString
@Table(name = "students")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "student_id")
public class Students {
    @Id
    @Column(name = "student_id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "major", length = 50)
    private String major;
    @Column(name = "gpa", precision = 3, scale = 2)
    private BigDecimal gpa;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Enrollment> enrollment;

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getGpa() {
        return gpa;
    }

    public void setGpa(BigDecimal gpa) {
        this.gpa = gpa;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

}
