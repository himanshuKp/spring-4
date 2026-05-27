package com.himanshu.springpractice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "user_logs")
@Getter
@Setter
public class UserLog {

    @Id
    private String id;

    private Long userId;
    private String action;
    private LocalDateTime timeStamp;
}
