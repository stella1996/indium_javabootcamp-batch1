package com.indium.skilltrackerapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.indium.skilltracker.dao.SkillTrackerDao;
import com.indium.skilltracker.dao.SkillTrackerDaoJdbcImpl;
import com.indium.skilltracker.exception.SkillsException;
import com.indium.skilltrackerapp.model.Associate;
import com.indium.skilltrackerapp.model.Skills;

public class SkillsServiceImpl implements SkillsService {
	Map<Integer, Associate> associateMap = new HashMap<>();
	SkillTrackerDao skillTrackerDao;

	public SkillsServiceImpl() {
		skillTrackerDao = new SkillTrackerDaoJdbcImpl();
	}

	@Override
	public boolean addAssociate(Associate associate) {
		return skillTrackerDao.createAssociate(associate);

	}

	@Override
	public boolean updateAssociateDetails(Associate associate) {
		return skillTrackerDao.updateAssociate(associate);
	}

	@Override
	public boolean deleteAssociate(int id) {
		return skillTrackerDao.deleteAssociate(id);
	}

	@Override
	public Associate viewAssociate(int id) throws SkillsException {
		Associate associate = skillTrackerDao.getAssociate(id);
		return associate;
	}

	public List<Associate> viewAllAssociate() {
		return skillTrackerDao.getAllAssociate();

	}

	public List<Associate> searchAssociate(String searchkey) {
		List<Associate> associates = skillTrackerDao.searchAssociatesByNameOrLocation(searchkey);
		return associates;
	}

	public List<Associate> searchAssociateBySkill(String searchkey) {
		List<Associate> associates = skillTrackerDao.searchAssociatesBySkillName(searchkey);
		return associates;
	}

	@Override
	public boolean addSkill(Skills skill, int associateId) {
		skillTrackerDao.createSkill(skill, associateId);
		return false;
	}

	@Override
	public boolean updateSkill(Skills skill, int skillId) {
		skillTrackerDao.updateSkill(skill, skillId);
		return false;
	}

	@Override
	public boolean deleteSkill(int skillId) {
		skillTrackerDao.deleteSkill(skillId);
		return false;
	}

	public boolean validate(Associate asc, String msg, Predicate<Associate> condition,
			Function<String, Boolean> operation) {
		if (!condition.test(asc)) {
			return operation.apply(msg);
		}
		return true;
	}

	public List<Skills> getSkillsBySearch(String searchKey) {
		return skillTrackerDao.getAllSkill().stream().filter(emp -> emp.getName().contains(searchKey))
				.collect(Collectors.toList());
	}

	public int getTotalAssociatesCount() {
		return skillTrackerDao.getAllAssociate().size();
	}

	public long getTotalAssociatesWithMoreSkillCount() {
		return skillTrackerDao.getAllAssociate().stream().filter(associate -> associate.getSkills().size() > 1).count();
	}

	public List<Integer> associateSkillFilter() {
		return skillTrackerDao.getAllAssociate().stream().filter(associate -> associate.getSkills().size() > 1)
				.map(Associate::getId).collect(Collectors.toList());
	}

	public long associateSkillCount(String searchKey) {

		return skillTrackerDao.getAllAssociate().stream().filter(associate -> associate.getSkills().stream()
				.map(Skills::getName).collect(Collectors.toSet()).contains(searchKey)).count();

	}

	public Map<String, Map<String, Long>> countSkillByCategory() {
		return skillTrackerDao.getAllAssociate().stream()
				.collect(Collectors.toMap(Associate::getName, associate -> countSkills(associate.getSkills())));
	}

	private static Map<String, Long> countSkills(List<Skills> skills) {
		return skills.stream().collect(Collectors.groupingBy(Skills::getCategory, Collectors.counting()));
	}

	public Map<String, Long> getBusinessUnitWiseCount() {
		return skillTrackerDao.getAllAssociate().stream()
				.collect(Collectors.groupingBy(Associate::getBu, Collectors.counting()));
	}

	public Map<String, Double> getSKillWiseAvgExp() {
		List<Skills> skillList = skillTrackerDao.getAllSkill();
		return skillList.stream()
				.collect(Collectors.groupingBy(Skills::getName, Collectors.averagingDouble(Skills::getExperience)));
	}

	public Map<String, Long> getLocationWiseCount() {
		return skillTrackerDao.getAllAssociate().stream()
				.collect(Collectors.groupingBy(Associate::getLocation, Collectors.counting()));
	}

}
