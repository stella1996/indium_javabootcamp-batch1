package com.indium.bankingapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.indium.bankingapp.model.Account;
import com.mysql.cj.jdbc.MysqlDataSource;

public class AccountDaoJdbcImpl implements AccountDao {

	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// Eager Initialization
	public AccountDaoJdbcImpl() {

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
	public boolean create(Account account) {
		// INSERT account data
		boolean status = false;
		try {
			String query = "INSERT INTO account(id, name, type, balance, isactive) values(?,?,?,?,?)";
			pstmt = getConnection().prepareStatement(query);
			pstmt.setInt(1, account.getId());
			pstmt.setString(2, account.getName());
			pstmt.setString(3, account.getType());
			pstmt.setDouble(4, account.getBalance());
			pstmt.setBoolean(5, account.isActive());
			status = pstmt.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean update(Account account) {
		// UPDATE employee data
		boolean status = false;
		try {
			String query = "UPDATE account SET name=?, type=?, balance=?, isactive=? WHERE id = ?";
			pstmt = getConnection().prepareStatement(query);
			pstmt.setString(1, account.getName());
			pstmt.setString(2, account.getType());
			pstmt.setDouble(3, account.getBalance());
			pstmt.setBoolean(4, account.isActive());
			pstmt.setInt(5, account.getId());
			status = pstmt.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean delete(int id) {
		// DELETE employee data
		boolean status = false;
		try {
			stmt = getConnection().createStatement();
			String query = "DELETE FROM account WHERE id = " + id;
			status = stmt.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Account get(int accountId) {
		// SELECT account data
		Account acc = null;
		String query = "SELECT * FROM account WHERE id = " + accountId;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String type = rs.getString("type");
				Double balance = rs.getDouble("balance");
				Boolean isactive = rs.getBoolean("isactive");
				acc = new Account(id, name, type, balance, isactive);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return acc;
	}

	@Override
	public List<Account> getAll() {
		// SELECT All accounts
		List<Account> accounts = new ArrayList<>();
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery("SELECT * FROM account");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String type = rs.getString("type");
				Double balance = rs.getDouble("balance");
				Boolean isactive = rs.getBoolean("isactive");
				accounts.add(new Account(id, name, type, balance, isactive));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			;
		}
		return accounts;
	}
}