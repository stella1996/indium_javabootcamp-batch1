package com.indium.bankingapp.dao;

import java.util.List;

import com.indium.bankingapp.model.Account;

public interface AccountDao {
	public boolean create(Account account);

	public boolean update(Account account);

	public boolean delete(int id);

	public Account get(int empId);

	public List<Account> getAll();
}
