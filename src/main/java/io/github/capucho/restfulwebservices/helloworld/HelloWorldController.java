package io.github.capucho.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping(path = "/hello-world/{name}")
	public String helloWorld(@PathVariable String name) {
		return "Hello World " + name;
	}
	
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
}
