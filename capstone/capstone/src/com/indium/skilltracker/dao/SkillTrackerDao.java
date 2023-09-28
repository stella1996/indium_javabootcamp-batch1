package com.indium.skilltracker.dao;

import java.util.List;

import com.indium.skilltrackerapp.model.Associate;
import com.indium.skilltrackerapp.model.Skills;

public interface SkillTrackerDao {
	public boolean createAssociate(Associate associate);


	public boolean updateAssociate(Associate associate);

	public boolean updateSkill(Skills skills,int skillId);

	public boolean deleteAssociate(int id);

	public Associate getAssociate(int assId);

	public List<Associate> getAllAssociate();

	public boolean deleteSkill(int skillId);

	public Skills getSkill(Skills skills);

	public int getTotalAssociatesWithSkillsGreaterThan(int n);

	public int getTotalAssociates();

	public List<Associate> searchAssociatesByNameOrLocation(String name);

	List<Skills> getAllSkill();

	int createSkill(Skills skills, int associateId);

}
