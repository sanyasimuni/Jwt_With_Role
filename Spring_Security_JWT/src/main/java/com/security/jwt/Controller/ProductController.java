package com.security.jwt.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	
	
	@GetMapping("/p")
	public String ProductHome() {
		return "Product Add Success !!";
	}

}
