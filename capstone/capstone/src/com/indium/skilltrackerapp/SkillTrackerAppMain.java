package com.indium.skilltrackerapp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import com.indium.skilltracker.exception.SkillsException;
import com.indium.skilltrackerapp.model.*;
import com.indium.skilltrackerapp.service.SkillsServiceImpl;

public class SkillTrackerAppMain {
	private static Scanner in;
	private static SkillsServiceImpl service;

	public static void main(String[] args) throws SkillsException {

		in = new Scanner(System.in);
		service = new SkillsServiceImpl();
		ExecutorService executor = Executors.newCachedThreadPool();

		System.out.print("Welcome to Skills Tracker App!");

		while (true) {

			System.out.println("\n");
			System.out.println("1. Add an Associate");
			System.out.println("2. View an Associate");
			System.out.println("3. Update an Associate");
			System.out.println("4. Delete an Associate");
			System.out.println("5. Add Skills");
			System.out.println("6. Edit Skill");
			System.out.println("7. Delete Skill");
			System.out.println("8. Search an Associate");
			System.out.println("9. Print Statistics");
			System.out.println("10. View All Associates");
			System.out.println("11. Exit");

			System.out.print("Enter the option: ");
			int option = 0;
			// Get option from user
			try {
				option = Integer.parseInt(in.next());
			} catch (NumberFormatException e) {
				System.out.println("Invalid option. Please enter valid option.");
				continue;
			}
			try {
				Associate associate = null;
				switch (option) {
				case 1:
					addAssociate();
					break;
				case 2:
					System.out.print("Enter associate id: ");
					try {
						associate = service.viewAssociate(in.nextInt());
						printHeader();
						printDetail(associate);
					} catch (SkillsException e) {
						System.out.println(e.getMessage());
						break;
					}
					break;
				case 3:
					System.out.print("Enter associate id to update: ");
					associate = new Associate();
					associate.setId(in.nextInt());
					Associate associateToUpdate = captureAssociateDetails(associate);
					boolean updated = service.updateAssociateDetails(associateToUpdate);
					if (updated) {
						System.out.println("Associate has been updated successfully!");
					} else {
						System.out.println("Associate not updated properly");
					}
					break;
				case 4:
					System.out.print("Enter associate id to delete: ");
					service.deleteAssociate(in.nextInt());
					System.out.println("Deleted Successfully");
					break;
				case 5:
					System.out.print("Enter the associate id to add skill: ");
					int id = in.nextInt();
					service.addSkill(addSkills(), id);
					System.out.println("Skill added successfully");
					break;
				case 6:
					System.out.print("Enter the associate id to Edit skill: ");
					try {
						int id1 = in.nextInt();
						associate = service.viewAssociate(id1);
						System.out.print("Skills associated to the ID");
						printSkillHeader();
						printSkillDetail(associate.getSkills());
						System.out.print("\nEnter the skill id 	to Edit skill: ");
						int skillId = in.nextInt();
						service.updateSkill(addSkills(), skillId);
						System.out.println("Skill updated successfully");
					} catch (SkillsException e) {
						e.printStackTrace();
					}
					break;
				case 7:
					System.out.print("Enter associate id to delete a skill: ");
					int id1 = in.nextInt();
					associate = service.viewAssociate(id1);
					System.out.println("Skills associated to the ID");
					printSkillHeader();
					printSkillDetail(associate.getSkills());
					System.out.print("\nEnter the skill id to delete: ");
					int skillId = in.nextInt();
					service.deleteSkill(skillId);
					System.out.println("Deleted Successfully");
					break;
				case 8:
					searchAssociate();
					break;
				case 9:
					printStatistics();
					break;
				case 10:
					List<Associate> associateList = null;
					associateList = service.viewAllAssociate();
					printHeader();
					for (Associate ass : associateList) {
						printDetail(ass);
					}
					break;
				case 11:
					System.out.println("Thank you!!!");
					executor.shutdown();
					in.close();
					System.exit(0);
					break;
				default:
					break;

				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter valid input.");
			}
		}

	}

	private static void printStatistics() {
		int id = 0;
		List<Associate> associateList = null;
		List<Skills> skillList = null;
		Associate associate = null;
		String searchKey;
		System.out.println("a.Total No of associates");
		System.out.println("b.Total No of associates who has more than 1 skill");
		System.out.println("c.List of Associate IDs who has more than N skills");
		System.out.println("d.Total No of Associates who has given set of skills");
		System.out.println("e.Skill count based on Associates");
		System.out.println("f.Associate Count based on Business Unit");
		System.out.println("g.Associate Average experience based on Skills ");
		System.out.println("h.Skills Count based on Location");
		System.out.print("Enter an option: ");
		String option = in.next();
		switch (option) {
		case "a":
			System.out.println("Total assoicates count is : " + service.getTotalAssociatesCount());
			break;
		case "b":
			System.out.println("Total assoicates who has more than 1 skill is : "
					+ service.getTotalAssociatesWithMoreSkillCount());
			break;
		case "c":
			List<Integer> associates = service.associateSkillFilter();
			System.out.println("List of associate id's who has more than 1 skill");
			associates.forEach(System.out::println);
			break;
		case "d":
			System.out.println("Enter the skill name to find the associates: ");
			searchKey = in.next();
			System.out.println("Total No of associates with given skill: " + service.associateSkillCount(searchKey));
			break;
		case "e":
			Map<String, Map<String, Long>> skillCounts = service.countSkillByCategory();
			skillCounts.forEach((associateName, counts) -> {
				System.out.println("Associate: " + associateName);
				System.out.println("Primary Skills: " + counts.getOrDefault("Primary", 0L));
				System.out.println("Secondary Skills: " + counts.getOrDefault("Secondary", 0L));
				System.out.println();
			});
			break;
		case "f":
			Map<String, Long> buAssociateCount = service.getBusinessUnitWiseCount();
			buAssociateCount.forEach((bu, count) -> {
				System.out.println("Business Unit: " + bu);
				System.out.println("Associate Count: " + count);
				System.out.println();
			});
			break;
		case "g":
			Map<String, Double> skillAvgExperience = service.getSKillWiseAvgExp();
			skillAvgExperience.forEach((skill, avgExperience) -> {
				System.out.println("Skill: " + skill);
				System.out.println("Average Experience: " + avgExperience);
				System.out.println();
				List<Associate> associatesWithSkill = service.viewAllAssociate().stream()
						.filter(a -> a.getSkills().stream().anyMatch(skillObj -> skillObj.getName().equals(skill)))
						.collect(Collectors.toList());
				List<String> skills = new ArrayList<>();
				associatesWithSkill.forEach(a -> {
					if (skills.contains(skill)) {
						System.out.printf("\n%5s %30s ", a.getName(), calculateExperience(a, skill));
						System.out.println();
					} else {
						skills.add(skill);
						System.out.println("Associates with this skill:");
						System.out.format("\n%5s %25s", "Associate Name", "Experience");
						System.out.printf("\n%5s %30s ", a.getName(), calculateExperience(a, skill));
						System.out.println();
					}

				});

				System.out.println();

			});
			break;
		case "h":
			Map<String, Long> locationAssociateCount = service.getLocationWiseCount();
			locationAssociateCount.forEach((loc, count) -> {
				System.out.println("Location : " + loc);
				System.out.println("Associate Count: " + count);
				System.out.println();
			});
			break;
		default:
			break;
		}

	}

	private static Object calculateAssociateExperience(Associate associate, String skill) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void searchAssociate() {
		int id = 0;
		List<Associate> associateList = null;
		List<Skills> skillList = null;
		Associate associate = null;
		String searchKey;
		System.out.println("a.Search by associte id");
		System.out.println("b.Search by associte name / location");
		System.out.println("c.Search by skill name");
		System.out.print("Chosse the search criteria: ");
		String option = in.next();
		switch (option) {
		case "a":
			System.out.print("Enter associate id: ");
			id = in.nextInt();
			try {
				associate = service.viewAssociate(id);
			} catch (SkillsException e) {
				e.printStackTrace();
			}
			printHeader();
			printDetail(associate);

			break;
		case "b":
			System.out.print("Enter associate name / location: ");
			searchKey = in.next();
			associateList = service.searchAssociate(searchKey);
			printHeader();
			printDetailList(associateList);
			break;
		case "c":
			System.out.print("Enter skill: ");
			searchKey = in.next();
			associateList = service.searchAssociateBySkill(searchKey);
			printHeader();
			printDetailListBasedSkill(associateList, searchKey);
			break;
		}
	}

	private static void addAssociate() throws NumberFormatException {
		Associate associate = new Associate();
		captureAssociateDetails(associate);
		List<Skills> skillList = new ArrayList<>();
		skillList.add(addSkills());
		associate.setSkills(skillList);
		associate.setCreatedDate(LocalDateTime.now());
		boolean added = service.addAssociate(associate);
		if (added) {
			System.out.println("Associate has been added successfully!");
		} else {
			System.out.println("Associate not added");
		}
	}

	private static Associate captureAssociateDetails(Associate associate) {
		System.out.print("Enter associate Name: ");
		associate.setName(in.next());

		try {
			boolean val = true;
			do {
				System.out.print("Enter associate Age: ");
				String errorMsg = "Invalid Age. Age should be between 18 to 60.";
				associate.setAge(Integer.parseInt(in.next()));
				val = service.validate(associate, errorMsg, e -> e.getAge() >= 18 && e.getAge() <= 60, m -> {
					System.out.println(m);
					return false;
				});
			} while (!val);
		} catch (NumberFormatException e) {
			throw e;
		}

		System.out.print("Enter Business Unit: ");
		associate.setBu(in.next());

		System.out.print("Enter Email: ");
		associate.setEmail(in.next());

		System.out.print("Enter Location: ");
		associate.setLocation(in.next());

		return associate;
	}

	private static Skills addSkills() {

		Skills skill = new Skills();

		System.out.print("Enter Skill Name: ");
		skill.setName(in.next());

		System.out.print("Enter skill Description: ");
		skill.setDescription(in.next());

		System.out.print("Enter skill category: ");
		skill.setCategory(in.next());

		System.out.print("Enter skill Experience in months: ");
		skill.setExperience(in.nextInt());
		return skill;
	}

	private static void printHeader() {
		System.out.format("\n%15s %15s %15s %15s %15s  %15s  %15s ", "AssociateId", "Name", "Age", "BU", "Skill Name",
				"Skill Category", "Experience");
	}

	private static void printDetail(Associate assc) {
		if (assc == null) {
			return;
		}
		if (assc.getSkills() != null) {
			for (Skills s1 : assc.getSkills()) {
				System.out.printf("\n%15s %15s %15s %15s %15s %15s  %15s", assc.getId(), assc.getName(), assc.getAge(),
						assc.getBu(), s1.getName(), s1.getCategory(), s1.getExperience());

			}
		} else {
			System.out.printf("\n%15s %15s %15s %15s %15s %15s  %15s  %15s", assc.getId(), assc.getName(),
					assc.getAge(), assc.getBu(), null, null, null, null);
		}
	}

	private static void printDetailList(List<Associate> assc) {
		if (assc == null) {
			return;
		}
		for (Associate ass : assc) {
			if (ass.getSkills() != null && ass.getSkills().size() > 0) {
				for (Skills s1 : ass.getSkills()) {
					System.out.printf("\n%15s %15s %15s %15s %15s %15s  %15s", ass.getId(), ass.getName(), ass.getAge(),
							ass.getBu(), s1.getName(), s1.getCategory(), s1.getExperience());

				}
			} else {
				System.out.printf("\n%15s %15s %15s %15s %15s %15s  %15s", ass.getId(), ass.getName(), ass.getAge(),
						ass.getBu(), null, null, null);
			}
		}
	}

	private static void printDetailListBasedSkill(List<Associate> assc, String name) {
		if (assc == null) {
			return;
		}
		for (Associate ass : assc) {
			if (ass.getSkills() != null && ass.getSkills().size() > 0) {
				for (Skills s1 : ass.getSkills()) {
					if (s1.getName().equals(name)) {
						System.out.printf("\n%15s %15s %15s %15s %15s %15s  %15s", ass.getId(), ass.getName(),
								ass.getAge(), ass.getBu(), s1.getName(), s1.getCategory(), s1.getExperience());
					}
				}
			} else {
				System.out.printf("\n%15s %15s %15s %15s %15s %15s  %15s", ass.getId(), ass.getName(), ass.getAge(),
						ass.getBu(), null, null, null);
			}
		}
	}

	private static void printSkillHeader() {
		System.out.format("\n%15s %15s %15s  %15s  %15s ", "SkillId", "Skill Name", "Skill Description",
				"Skill Category", "Experience");
	}

	private static void printSkillDetail(List<Skills> skills) {

		for (Skills s1 : skills) {
			System.out.printf("\n%15s %15s %15s  %15s  %15s", s1.getId(), s1.getName(), s1.getDescription(),
					s1.getCategory(), s1.getExperience());

		}
	}

	private static int calculateExperience(Associate associate, String skill) {

		return associate.getSkills().stream().filter(skillObj -> skillObj.getName().equals(skill))

				.mapToInt(Skills::getExperience).sum();

	}
}
