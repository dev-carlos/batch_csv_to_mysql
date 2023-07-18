package com.tutorial.demo.spring.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorial.demo.spring.batch.entity.Person;
import com.tutorial.demo.spring.batch.repository.PersonRepository;

@Component
public class DBWriter implements ItemWriter<Person> {
	
	private static final Logger log = LoggerFactory.getLogger(DBWriter.class);
	
	@Autowired
	private PersonRepository personRepository;

	@Override
	public void write(Chunk<? extends Person> chunk) throws Exception {
		log.info("Escribiendo datos de personas en BBDD...{}", chunk.getItems());
		personRepository.saveAll(chunk.getItems());
	}

	

}
