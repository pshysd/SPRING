package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/*
 * * Lombok 사용 시 주의사항
 * - userId 필드 -> setUserId() / getUserId()
 * - uId 필드 -> setUId() / getUId() <- 조심
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Member {
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
	
}
