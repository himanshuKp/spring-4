package com.himanshu.springpractice.repository;

import com.himanshu.springpractice.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends JpaRepository<Students, Integer> {
}
