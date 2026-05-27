package com.himanshu.springpractice.repository;

import com.himanshu.springpractice.entity.UserLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserLogRepository extends MongoRepository<UserLog, String> {
}
