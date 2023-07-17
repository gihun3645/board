package com.example.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor // 아무것도 없는 기본 생성자
public class User {
    private int userId;
    private String email;
    private String name;
    private String password;
    private String reddate; // 원래는 날짜 type으로 읽어온 후 문자열로 변환
}


/*
user_id,int,NO,PRI,,auto_increment
email,varchar(255),NO,"",,""
name,varchar(50),NO,"",,""
password,varchar(500),NO,"",,""
regdate,timestamp,NO,"",CURRENT_TIMESTAMP,DEFAULT_GENERATED
 */