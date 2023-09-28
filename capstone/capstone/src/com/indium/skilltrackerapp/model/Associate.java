package com.indium.skilltrackerapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class Associate {
private int id;
private String name;
private int age;
private String bu;
private String email;
private String location;
private List<Skills> skills;

private LocalDateTime createdDate;
private LocalDateTime updatedDate;


public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public String getBu() {
	return bu;
}
public void setBu(String bu) {
	this.bu = bu;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public List<Skills> getSkills() {
	return skills;
}
public void setSkills(List<Skills> skill) {
	this.skills = skill;
}
public LocalDateTime getcreatedDate() {
	return createdDate;
}
public void setCreatedDate(LocalDateTime cDate) {
	this.createdDate = cDate;
}
public LocalDateTime getUpdatedDate() {
	return updatedDate;
}
public void setUpdatedDate(LocalDateTime uDate) {
	this.updatedDate = uDate;
}

public Associate() {
	

	
}

public Associate(int id, String name, int age, String bu, String email, String location, List<Skills> skills,
		LocalDateTime createdDate, LocalDateTime updatedDate) {
	super();
	this.id = id;
	this.name = name;
	this.age = age;
	this.bu = bu;
	this.email = email;
	this.location = location;
	this.skills = skills;
	this.createdDate = createdDate;
	this.updatedDate = updatedDate;
}

public Associate(int id, String name, int age, String bu, String email, String location, List<Skills> skillsList) {
	super();
	this.id = id;
	this.name = name;
	this.age = age;
	this.bu = bu;
	this.email = email;
	this.location = location;
	this.skills=skillsList;
	
}
public String toString() {
	return String.format("Id:%d, Name:%s Age:%s BU%s Email:%s Locatin:%s Skills:%s", this.getId(), 
			this.getName(),this.getAge(),this.getBu(),this.getEmail(),this.getLocation(),this.getSkills());
}

}
