package com.tutorial.demo.spring.batch.procesing;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.tutorial.demo.spring.batch.entity.Person;

//procesamiento de datos de person a mayusculas

@Component
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
	
	private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);
	
	private static Map<String, String> deptMap = new HashMap<>();
	
	public PersonItemProcessor() {
		deptMap.put("001", "Administracion");
		deptMap.put("002", "Ventas");
		deptMap.put("003", "Tecnologia");
	}

	@Override
	public Person process(Person item) throws Exception {
		
		String nombre = item.getFirstName().toUpperCase();
		String apellido = item.getLastName().toUpperCase();
		
		String dept = deptMap.get(item.getDept());
		
		Person person = new Person(item.getId(), apellido, nombre, dept, item.getSalary());
		
		log.info("Transformando de Persona {} a Persona transformada {}", item.toString(), person.toString());
		
		
		return person;
	}

}
