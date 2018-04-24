package io.github.capucho.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.capucho.restfulwebservices.exception.UserNotFoundException;

@RestController
public class UserController {

	@Autowired
	private UserDao userDao;

	@GetMapping(path = "/users")
	public List<User> findAll() {
		return userDao.findAll();
	}

	@GetMapping(path = "/users/{id}")
	public ResponseEntity<User> retrieveUser(@PathVariable("id") Integer id) {
		User user = userDao.findUser(id);
		if(user == null) {
			//return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
			throw new UserNotFoundException("Not found");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PostMapping(path = "/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {

		try {
			User savedUser = userDao.save(user);
			URI uri = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();
			return ResponseEntity.created(uri).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
