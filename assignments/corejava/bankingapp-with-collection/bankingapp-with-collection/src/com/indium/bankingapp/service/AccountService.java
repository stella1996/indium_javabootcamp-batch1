package com.indium.bankingapp.service;

import java.util.Collection;

import com.indium.bankingapp.model.Account;

public interface AccountService {

	public boolean createAccount(Account acc);

	public boolean updateAccount(Account acc);

	public boolean deleteAccount(int accountId);

	public Account getAccount(int accountId);

	public Collection getAll();

}
