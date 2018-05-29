package io.github.capucho.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

	
	@GetMapping(value="/v1/person")
	public PersonV1 personV1() {
		return new PersonV1("Teste Capucho");
	}
	
	@GetMapping(value="/v2/person")
	public PersonV2 personV2() {
		return new PersonV2(new Name("Teste", "Capucho"));
	}
	
	@GetMapping(value="/person/param", params="version=1")
	public PersonV1 personV1Params() {
		return new PersonV1("Teste Capucho");
	}
	
	@GetMapping(value="/person/param", params="version=2")
	public PersonV2 personV2Params() {
		return new PersonV2(new Name("Teste", "Capucho"));
	}
	
	@GetMapping(value="/person/header", headers="X-API-VERSION=1")
	public PersonV1 personV1Header() {
		return new PersonV1("Teste Capucho");
	}
	
	@GetMapping(value="/person/header", headers="X-API-VERSION=2")
	public PersonV2 personV2Header() {
		return new PersonV2(new Name("Teste", "Capucho"));
	}
	
	@GetMapping(value="/person/produces", produces="application/vnd.capucho.app-v1+json")
	public PersonV1 personV1Accept() {
		return new PersonV1("Teste Capucho");
	}
	
	@GetMapping(value="/person/produces", produces="application/vnd.capucho.app-v2+json")
	public PersonV2 personV2Accept() {
		return new PersonV2(new Name("Teste", "Capucho"));
	}
	
}
