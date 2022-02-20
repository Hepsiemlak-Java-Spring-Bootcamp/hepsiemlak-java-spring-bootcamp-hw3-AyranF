package emlakburada.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import emlakburada.dto.request.AddUserAdvertRequest;
import emlakburada.dto.request.UserRequest;
import emlakburada.dto.response.AdvertResponse;
import emlakburada.dto.response.UserResponse;
import emlakburada.model.User;
import emlakburada.service.AdvertService;
import emlakburada.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "/users")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@PostMapping(value = "/users")
	public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
		return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
	}
	
	@PatchMapping("/users/{userId}")
	public ResponseEntity<UserResponse> updateAdvert(@RequestBody AddUserAdvertRequest request, @PathVariable(value = "userId") int userId) {
		return new ResponseEntity<>(userService.addAdvert(request, userId), HttpStatus.OK);
	}

	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable(required = false) int userId) {
		return new ResponseEntity<>(userService.getResponseUserById(userId), HttpStatus.OK);
	}

	@GetMapping(value = "/users/{userId}/favorites")
	public ResponseEntity<List<AdvertResponse>> getUsersFavorites(@PathVariable(required = false) int userId) {
		return new ResponseEntity<>(userService.getFavoriteAdvertById(userId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/{userId}/adverts")
	public ResponseEntity<List<AdvertResponse>> getUsersAdverts(@PathVariable(required = false) int userId) {
		return new ResponseEntity<>(userService.getPublishledAdverts(userId), HttpStatus.OK);
	}
}
