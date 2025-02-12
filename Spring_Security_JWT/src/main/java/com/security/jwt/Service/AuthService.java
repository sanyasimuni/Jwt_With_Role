package com.security.jwt.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.jwt.DTO.SignUpRequest;
import com.security.jwt.DTO.UserResponse;
import com.security.jwt.Entity.User;
import com.security.jwt.Entity.UserRole;
import com.security.jwt.Repo.UserRepo;

import jakarta.annotation.PostConstruct;

@Service
public class AuthService {
	
	@Autowired
	private UserRepo  userRepo;
	
	@PostConstruct
	public void createAdminAccount() {
		
     Optional<User>admin=userRepo.findByUserRole(UserRole.ADMIN);
     if(admin.isEmpty()) {
    	 
    	 User user=new User();
    	 user.setName("admin");
    	 user.setEmail("sanyasimuni@gmail.com");
    	 user.setPassword(new BCryptPasswordEncoder().encode("admin"));
    	 user.setUserRole(UserRole.ADMIN);
    	  userRepo.save(user);
    	  System.out.println("Admin Account Created ");
    	  
    	  
     }else {
   	  System.out.println("Admin Account Already Exist.");

    	 
     }
		
	}
	
	//has email
	public boolean hasUserWithEmail(String email) {
		
		return userRepo.findByEmail(email).isPresent();
		
	}
	
	
	//sign Up 
	public UserResponse signUp(SignUpRequest signUpRequest) {
		
		User user=new User();
		user.setEmail(signUpRequest.getEmail());
		user.setName(signUpRequest.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
		user.setUserRole(UserRole.USER);
		
		return userRepo.save(user).getUserDto();	
			
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
