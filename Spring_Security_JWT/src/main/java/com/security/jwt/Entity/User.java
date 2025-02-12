package com.security.jwt.Entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.security.jwt.DTO.UserResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String password;
	private String name;
	
	private UserRole userRole;
	
	
	public    UserResponse getUserDto() {
		UserResponse response=new UserResponse();
		response.setId(id);
		response.setEmail(email);
		response.setName(name);
		response.setPassword(password);
		response.setUserRole(userRole);
		return response;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return List.of(new SimpleGrantedAuthority(userRole.name()));
	}

	@Override
	public String getUsername() {
		
		return email;
	}
	

}
