package com.indium.skilltrackerapp.service;

import java.util.List;

import com.indium.skilltracker.exception.SkillsException;
import com.indium.skilltrackerapp.model.Associate;
import com.indium.skilltrackerapp.model.Skills;

public interface SkillsService {
public  boolean AddAssociate(Associate associate) ;
public boolean UpdateAssociateDetails(Associate associate);
public  boolean DeleteAssociate(int id) ;
public  Associate ViewAssociate(int id) throws SkillsException ;
public List<Associate> ViewAllAssociate();
public  boolean DeleteSkill(int skillId) ;
boolean AddSkill(Skills skill, int associateId);
boolean UpdateSkill(Skills skill, int skillId);
public List<Associate> SearchAssociate(String searchkey);
List<Skills> GetSkillsBySearch(String searchKey);

}
