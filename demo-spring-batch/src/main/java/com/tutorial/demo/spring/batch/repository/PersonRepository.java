package com.tutorial.demo.spring.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.demo.spring.batch.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
