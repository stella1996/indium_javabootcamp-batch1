package com.indium.skilltrackerapp.service;

import java.util.List;

import com.indium.skilltracker.exception.SkillsException;
import com.indium.skilltrackerapp.model.Associate;
import com.indium.skilltrackerapp.model.Skills;

public interface SkillsService {
	public boolean addAssociate(Associate associate);

	public boolean updateAssociateDetails(Associate associate);

	public boolean deleteAssociate(int id);

	public Associate viewAssociate(int id) throws SkillsException;

	public List<Associate> viewAllAssociate();

	public boolean deleteSkill(int skillId);

	boolean addSkill(Skills skill, int associateId);

	boolean updateSkill(Skills skill, int skillId);

	public List<Associate> searchAssociate(String searchkey);

	public List<Associate> searchAssociateBySkill(String searchkey);

	List<Skills> getSkillsBySearch(String searchKey);

}
