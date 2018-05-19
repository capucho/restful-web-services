package io.github.capucho.restfulwebservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource messageSource;
	
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

//	@GetMapping(path="/good-morning")
//	public String goodMorning(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
//		return messageSource.getMessage("good.morning.message", null, locale);
//	}
	
	@GetMapping(path="/good-morning")
	public String goodMorning() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
}
