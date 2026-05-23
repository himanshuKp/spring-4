package com.himanshu.springpractice.service;

import com.himanshu.springpractice.entity.Students;
import com.himanshu.springpractice.repository.StudentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentsService {
    private StudentsRepository  studentsRepository;

    public StudentsService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public Students saveStudent(Students student) {
        return studentsRepository.save(student);
    }

    public List<Students> findAllStudents() {
        return studentsRepository.findAll();
    }

    public Optional<Students> findStudentsById(Integer id) {
        return studentsRepository.findById(id);
    }

    public Students updateStudents(Students students) {
        Students students1 = studentsRepository.findById(students.getId()).orElse(null);
        assert students1 != null;
        students1.setName(students.getName());
        students1.setAge(students.getAge());
        students1.setMajor(students.getMajor());
        return studentsRepository.save(students1);
    }

    public void deleteStudentsById(Integer id) {
        studentsRepository.deleteById(id);
    }
}
