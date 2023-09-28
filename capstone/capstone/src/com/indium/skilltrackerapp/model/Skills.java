package com.indium.skilltrackerapp.model;

public class Skills {
	private int id;
	private String name;
	private String description;
	private String category;
	private int experience;
	private int associateId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public void setAssociateId(int associateId)
	{
		this.associateId=associateId;
	}
	public int getAssociateId()
	{
		return associateId;
	}
	public Skills()
	{
		
	}
	public Skills(int id,String name, String description, String category, int experience) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.experience = experience;
    }
	public Skills(int id,String name, String category, int experience) {
        this.id=id;
		this.name = name;
        this.category = category;
        this.experience = experience;
    }
	@Override
	public String toString() {
		return "Skills [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", experience=" + experience + "]";
	}
	
	
}
