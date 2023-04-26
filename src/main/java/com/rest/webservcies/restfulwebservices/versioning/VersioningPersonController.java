package com.rest.webservcies.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
	
	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Ravi Kumawat");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Ravi","Kumawat"));
	}
	
	@GetMapping(path="/person",params="version=1")
	public PersonV1 getFirstVersionOfPerson_requestParameter() {
		return new PersonV1("Ravi Kumawat");
	}
	
	@GetMapping(path="/person",params="version=2")
	public PersonV2 getSecondVersionOfPerson_requestParameter() {
		return new PersonV2(new Name("Ravi","Kumawat"));
	}
	
	@GetMapping(path="/person",headers="X-API-VERSION=1")
	public PersonV1 getFirstVersionOfPerson_headers() {
		return new PersonV1("Ravi Kumawat");
	}
	
	@GetMapping(path="/person",headers="X-API-VERSION=2")
	public PersonV2 getSecondVersionOfPerson_headers() {
		return new PersonV2(new Name("Ravi","Kumawat"));
	}
	
	@GetMapping(path="/person",produces="application/vnd.company.app-v1+json")
	public PersonV1 getFirstVersionOfPerson__mediaType() {
		return new PersonV1("Ravi Kumawat");
	}
	
	@GetMapping(path="/person",produces="application/vnd.company.app-v2+json")
	public PersonV2 getSecondVersionOfPerson_mediaType() {
		return new PersonV2(new Name("Ravi","Kumawat"));
	}
}
