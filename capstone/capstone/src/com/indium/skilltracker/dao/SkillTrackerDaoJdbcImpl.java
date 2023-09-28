package com.indium.skilltracker.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.indium.skilltrackerapp.model.Associate;
import com.indium.skilltrackerapp.model.Skills;
import com.mysql.cj.jdbc.MysqlDataSource;

public class SkillTrackerDaoJdbcImpl implements SkillTrackerDao {
	Connection conn = null;
	java.sql.Statement stmt = null;
	ResultSet rs = null;

	// Eager Initialization
	public SkillTrackerDaoJdbcImpl() {

	}

	// Lazy Initialization
	public Connection getConnection() {
		try {
			if (conn == null) {
				MysqlDataSource datasource = new MysqlDataSource();
				datasource.setServerName("localhost");
				datasource.setDatabaseName("training");
				datasource.setUser("root");
				datasource.setPassword("1996");

				conn = datasource.getConnection();
				System.out.println("Connection created successfully. " + conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public boolean createAssociate(Associate associate) {
		boolean status = false;
		int associateId = 0;
		int SkillId = 0;

		try {
			conn = getConnection();
			stmt = conn.createStatement();

			String query = "INSERT INTO Associates (Name, Age, BusinessUnit, Email, Location, CreatedTime, UpdatedTime)  values(\""
					+ associate.getName() + "\"," + associate.getAge() + ",\"" + associate.getBu() + "\",\""
					+ associate.getEmail() + "\",\"" + associate.getLocation() + "\",\"" + LocalDate.now() + "\",\""
					+ LocalDate.now() + "\")";

			status = stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				// Get the generated ID
				associateId = generatedKeys.getInt(1);
			}
			SkillId = createSkill(associate.getSkills().get(0), associateId);
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public int createSkill(Skills skills, int associateId) {
		int skillId = 0;
		try {
			conn = getConnection();
			stmt = conn.createStatement();

			String query = "INSERT INTO Skills (Name, Description, Category, Experience) VALUES (\"" + skills.getName()
					+ "\",\"" + skills.getDescription() + "\",\"" + skills.getCategory() + "\","
					+ skills.getExperience() + ")";

			stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				// Get the generated ID
				skillId = generatedKeys.getInt(1);
			}
			insertAssociateSkill(associateId, skillId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return skillId;
	}

	private void insertAssociateSkill(int associateId, int skillId) {
		try {
			conn = getConnection();
			stmt = conn.createStatement();

			String insertAssociateSkillSQL = "INSERT INTO AssociateSkills (AssociateID, SkillID) VALUES (\""
					+ associateId + "\"," + skillId + ")";

			stmt.execute(insertAssociateSkillSQL, Statement.RETURN_GENERATED_KEYS);
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				// Get the generated ID
				skillId = generatedKeys.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean updateAssociate(Associate associate) {
		boolean status = false;

		try {
			conn = getConnection();
			stmt = conn.createStatement();

			String query = "Update associates set name = \"" + associate.getName() + "\",age=" + associate.getAge()
					+ ",location =\"" + associate.getLocation() + "\",businessUnit=\"" + associate.getBu()
					+ "\",email=\"" + associate.getEmail() + "\" where id=" + associate.getId();

			status = stmt.execute(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean updateSkill(Skills skill, int skillId) {
		boolean status = false;

		try {
			conn = getConnection();
			stmt = conn.createStatement();

			String query = "Update skills set name = \"" + skill.getName() + "\",description=\""
					+ skill.getDescription() + "\",category =\"" + skill.getCategory() + "\",experience="
					+ skill.getExperience() + " where id=" + skillId;

			status = stmt.execute(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean deleteAssociate(int id) {
		boolean status = false;

		try {
			conn = getConnection();
			stmt = conn.createStatement();

			String query = "DELETE a_s,a, s\r\n" + "FROM associates a\r\n"
					+ "LEFT JOIN associateskills a_s ON a.id = a_s.associateid\r\n"
					+ "Left JOIN skills s on s.id=a_s.skillid " + "WHERE a.id=" + id;

			status = stmt.execute(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public List<Skills> getSkillList(int assId) {
		ResultSet rs1 = null;
		List<Skills> skillList = new ArrayList<>();
		Skills skill = null;
		try {
			stmt = getConnection().createStatement();
			String query = "SELECT id,name,category,experience,associateId FROM skills s join associateskills a_s on s.id=a_s.SkillId where a_s.associateId="
					+ assId;

			rs1 = stmt.executeQuery(query);

			while (rs1.next()) {
				int id = rs1.getInt("id");
				String name = rs1.getString("name");
				int experience = rs1.getInt("experience");
				String category = rs1.getString("category");
				int associateId = rs1.getInt("associateId");
				skill = new Skills(id, name, category, experience);
				skillList.add(skill);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return skillList;
	}

	@Override
	public Associate getAssociate(int assId) {
		String sqlQuery = "select A.id,A.name,A.age,A.BusinessUnit, A.Email, A.Location  FROM associates A  where A.id="
				+ assId;
		Associate associate = null;
		List<Skills> skills = getSkillList(assId);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sqlQuery);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String bu = rs.getString("BusinessUnit");
				String email = rs.getString("Email");
				String location = rs.getString("Location");
				associate = new Associate(id, name, age, bu, email, location, skills);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return associate;
	}

	@Override
	public List<Associate> getAllAssociate() {
		String sqlQuery = "select A.id,A.name,A.age,A.BusinessUnit, A.Email, A.Location  FROM associates A ";
		Associate associate = null;
		List<Associate> associateList = new ArrayList();
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sqlQuery);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String bu = rs.getString("BusinessUnit");
				String email = rs.getString("Email");
				String location = rs.getString("Location");
				List<Skills> skills = getSkillList(id);
				associate = new Associate(id, name, age, bu, email, location, skills);
				associateList.add(associate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return associateList;
	}

	@Override
	public boolean deleteSkill(int skillId) {
		boolean status = false;

		try {
			conn = getConnection();
			stmt = conn.createStatement();

			String query = "	DELETE a_s, s\r\n" + "	FROM skills s\r\n"
					+ "	LEFT JOIN associateskills a_s ON s.id = a_s.skillid\r\n" + "	 WHERE s.id=\r\n" + skillId;

			status = stmt.execute(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Skills getSkill(Skills skills) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Skills> getAllSkill() {
		ResultSet rs1 = null;

		String query = "SELECT id,name,category,experience FROM skills";
		List<Skills> skillList = new ArrayList<>();
		Skills skill = null;
		try {
			stmt = getConnection().createStatement();
			rs1 = stmt.executeQuery(query);

			while (rs1.next()) {
				int id = rs1.getInt("id");
				String name = rs1.getString("name");
				int experience = rs1.getInt("experience");
				String category = rs1.getString("category");
				skill = new Skills(id, name, category, experience);
				skillList.add(skill);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return skillList;
	}

	@Override
	public int getTotalAssociatesWithSkillsGreaterThan(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalAssociates() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Associate> searchAssociatesByNameOrLocation(String name) {
		String sqlQuery = "select A.id,A.name,A.age,A.BusinessUnit, A.Email, A.Location  FROM associates A where A.name like \"%"
				+ name + "%\" or A.location like\"%" + name + "%\"";
		Associate associate = null;
		List<Associate> associateList = new ArrayList();
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sqlQuery);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name1 = rs.getString("name");
				int age = rs.getInt("age");
				String bu = rs.getString("BusinessUnit");
				String email = rs.getString("Email");
				String location = rs.getString("Location");
				List<Skills> skills = getSkillList(id);
				associate = new Associate(id, name1, age, bu, email, location, skills);
				associateList.add(associate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return associateList;
	}

}
