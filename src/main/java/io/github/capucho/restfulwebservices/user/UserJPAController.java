package io.github.capucho.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.capucho.restfulwebservices.exception.UserNotFoundException;
import io.github.capucho.restfulwebservices.post.Post;

@RestController
public class UserJPAController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path = "/jpa/users")
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@GetMapping(path = "/jpa/users/{id}")
	public Resource<User> retrieveUser(@PathVariable("id") Integer id) {
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			//return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
			throw new UserNotFoundException("Not found");
		}
		
		Resource<User> resource = new Resource<User>(user.get());
		resource.add(linkTo(methodOn(this.getClass()).findAll()).withRel("all-users"));
		return resource;
	}
	
	@PostMapping(path = "/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

		try {
			User savedUser = userRepository.save(user);
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
	
	@DeleteMapping(path = "/jpa/users/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}
	
}
