package com.security.jwt.DTO;

import com.security.jwt.Entity.UserRole;

import lombok.Data;

@Data
public class SignUpRequest {
	
	private String email;
	private String name;
	private String password;
	private UserRole userRole;
	

}
