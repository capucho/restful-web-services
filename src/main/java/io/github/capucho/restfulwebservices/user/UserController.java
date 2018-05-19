package io.github.capucho.restfulwebservices.user;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public Resource<User> retrieveUser(@PathVariable("id") Integer id) {
		User user = userDao.findUser(id);
		if(user == null) {
			//return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
			throw new UserNotFoundException("Not found");
		}
		
		Resource<User> resource = new Resource<User>(user);
		resource.add(linkTo(methodOn(this.getClass()).findAll()).withRel("all-users"));
		return resource;
	}

	@PostMapping(path = "/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

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
	
	@DeleteMapping(path = "/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
		User user = userDao.deleteById(id);
		
		if(user == null) {
			throw new UserNotFoundException("id [" + id + "] ");
		}
		
		return ResponseEntity.noContent().build();
	}

}
