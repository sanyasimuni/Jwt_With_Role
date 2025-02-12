package com.security.jwt.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/customer")
public class CustomerController {
	
	@GetMapping("/custom")
	public String Customer()
	{
		return "customer page Redirect";
	}

}
