package com.tutorial.demo.spring.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorial.demo.spring.batch.repository.PersonRepository;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener{

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	
	@Autowired
	private PersonRepository personRepository;

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			
			log.info("!!! JOB FINALIZADO ¡¡¡ Hora de verificar el resultado");
			
			personRepository.findAll().forEach(person -> log.info("Encontrado <{{}}> en BBDD", person.toString()));
		}
		
	}
	
	
}
