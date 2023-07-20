package com.example.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // 아무것도 없는 기본 생성자
@ToString
public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private LocalDateTime regdate; // 원래는 날짜 type으로 읽어온 후 문자열로 변환
}