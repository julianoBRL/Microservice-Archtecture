package io.github.julianobrl.archtecture.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.julianobrl.archtecture.model.Users;
import io.github.julianobrl.archtecture.services.UsersService;

@RestController
@RequestMapping("/register")
public class Authorization {
	
	@Autowired
    private UsersService service;
	
	@PostMapping
	private void post(@RequestBody Users user) {
		service.encryptAndSave(user);
	}
	
}
