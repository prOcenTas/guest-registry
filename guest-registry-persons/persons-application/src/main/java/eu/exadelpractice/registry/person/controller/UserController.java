package eu.exadelpractice.registry.person.controller;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.person.exception.UserNotFoundException;
import eu.exadelpractice.registry.person.model.User;
import eu.exadelpractice.registry.person.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping
	public List<User> findAllUsers() {
		return userService.findAll();
	}

	@GetMapping("{id}")
	public User getUserById(@PathVariable String id) throws UserNotFoundException {
		return userService.getUserById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public String createUser(@RequestBody User user) throws ObjectNotFoundException {
		return userService.createUser(user);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping
	public void updateUser(@RequestBody User user) throws ObjectNotFoundException {
		userService.updateUser(user);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deleteUser(@PathVariable String id) throws UserNotFoundException {
		userService.deleteUserById(id);
	}
}
