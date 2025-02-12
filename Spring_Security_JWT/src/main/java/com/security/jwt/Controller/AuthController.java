package com.security.jwt.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwt.DTO.AuthenticationRequest;
import com.security.jwt.DTO.AuthenticationResponse;
import com.security.jwt.DTO.SignUpRequest;
import com.security.jwt.DTO.UserResponse;
import com.security.jwt.Entity.User;
import com.security.jwt.JwtUtil.JwtUtils;
import com.security.jwt.Repo.UserRepo;
import com.security.jwt.Service.AuthService;
import com.security.jwt.Service.UserService;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

	
	
	
	@Autowired
	private AuthService authService;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private UserRepo userRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	
	
	//register The request
	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
	    // Check if a user with the provided email already exists
	    if (authService.hasUserWithEmail(signUpRequest.getEmail())) {
	        // If user exists, return a 406 (Not Acceptable) response
	        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User Already Exists");
	    }

	    // Proceed with sign-up if user doesn't exist
	    UserResponse response = this.authService.signUp(signUpRequest);

	    // Check if sign-up was successful (response is not null)
	    if (response == null) {
	        // Return a 400 (Bad Request) response if sign-up fails
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }

	    // Return a 201 (Created) response with the user response
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	
	
	
	//login 
	@PostMapping("/login")
	public AuthenticationResponse Login(@RequestBody AuthenticationRequest  authenticationRequest) {
		try {
			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
		
		} catch (BadCredentialsException e) {
			
			throw new BadCredentialsException("Incorrect UserName and Password");
		}
		
		
		     final UserDetails userDetails= userService.loadUserByUsername(authenticationRequest.getEmail());
		     
		       Optional<User>optUser=userRepo.findByEmail(authenticationRequest.getEmail());
		       final String jwt=jwtUtils.generateToken(userDetails);
		       
		       AuthenticationResponse response=new AuthenticationResponse();
		       if(optUser.isPresent()) {
		    	   response.setUserId(optUser.get().getId());
		    	   response.setJwt(jwt);
		    	   response.setUserRole(optUser.get().getUserRole());
		    	   
		    	   
		       }
			return response;
		       
		
	}
	
	
	

	@GetMapping("/prod")
	public String ProductHome() {
		return "Product Add Success !!";
	}

	
	
	
	
}
