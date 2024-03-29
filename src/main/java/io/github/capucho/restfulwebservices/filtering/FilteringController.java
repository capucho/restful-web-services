package io.github.capucho.restfulwebservices.filtering;

import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.collect.Lists;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue retrieveSomeBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		
		// Create filter
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		
		// Create filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		
		// Create Jackson Mapping
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		
		// Add filter
		mapping.setFilters(filters);
		
		return mapping;
	}

	@GetMapping("/filtering-list")
	public List<SomeBean> retrieveSomeBeanList() {
		return Lists.newArrayList(new SomeBean("value1", "value2", "value3"), new SomeBean("value11", "value22", "value33"));
	}

}
