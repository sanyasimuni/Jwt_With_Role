package com.security.jwt.DTO;

import com.security.jwt.Entity.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	
	private String jwt;
	private Long userId;
	private UserRole userRole;
	

}
