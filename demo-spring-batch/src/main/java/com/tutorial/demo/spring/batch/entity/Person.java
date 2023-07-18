package com.tutorial.demo.spring.batch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person {
	@Id
	private Long id;
	private String lastName;
	private String firstName;
	private String dept;
	private Integer salary;
	
	public Person() {
		super();
	}

	public Person(Long id, String lastName, String firstName, String dept, Integer salary) {
		
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.dept = dept;
		this.salary = salary;
	}

	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", dept=" + dept
				+ ", salary=" + salary + "]";
	}
	

}
