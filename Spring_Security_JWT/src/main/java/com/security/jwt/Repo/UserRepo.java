package com.security.jwt.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.jwt.Entity.User;
import com.security.jwt.Entity.UserRole;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	 Optional<User> findByEmail(String email);
	 Optional<User> findByUserRole(UserRole userRole);
	
	
	
	

}
