package com.tutorial.demo.spring.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.tutorial.demo.spring.batch.entity.Person;
import com.tutorial.demo.spring.batch.procesing.PersonItemProcessor;

@Configuration
//@EnableBatchProcessing
public class BatchConfig {
	
	@Value("${person.csv}")
	private String personCsv;
	
	@Bean
	public FlatFileItemReader<Person> reader(){
		
		return new FlatFileItemReaderBuilder<Person>()
				.name("personItemReader")
				.resource(new ClassPathResource(personCsv))
				.linesToSkip(1)
				.delimited()
				.names(new String[] {"id", "firstName", "lastName", "dept", "salary"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
					setTargetType(Person.class);
				}})
				//.lineMapper(lineMapper())
				.build();
		
	}
	
	public LineMapper<Person> lineMapper(){
		DefaultLineMapper<Person> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"id", "firstName", "lastName", "dept", "salary"});
		BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Person.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
	}
	
	@Bean
	public PersonItemProcessor procesor() {
		return new PersonItemProcessor();
	}
	
//	@Bean
//	public JdbcBatchItemWriter<Person> writer(DataSource dataSource){
//		return new JdbcBatchItemWriterBuilder<Person>()
//				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//				.sql("INSERT INTO people (firstName, lastName) VALUES (:firstName, :lastName)")
//				.dataSource(dataSource)
//				.build();
//	}
	
	@Bean
	public Job importUserJob (JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
		return new JobBuilder("importUserJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}
	
	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager manager, ItemWriter<Person> writer) {
		return new StepBuilder("step1", jobRepository)
				.<Person, Person> chunk(10, manager)
				.reader(reader())
				.processor(procesor())
				.writer(writer)
				.build();
	}
	
}
