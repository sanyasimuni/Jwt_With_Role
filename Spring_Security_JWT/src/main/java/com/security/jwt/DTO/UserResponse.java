package com.security.jwt.DTO;

import com.security.jwt.Entity.UserRole;

import lombok.Data;

@Data
public class UserResponse {
	
	private Long id;
	private String email;
	private String password;
	private String name;
	private UserRole userRole;

}
