package com.indium.bankingapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.indium.bankingapp.model.Account;

public class AccountServiceHashMapImpl implements AccountService {
	Map<Integer, Account> accounts = new HashMap<>();

	// Method helps to add new account in Hashmap
	@Override
	public boolean createAccount(Account account) {
		account.setId(accounts.size() + 1);
		return accounts.put(account.getId(), account) != null ? true : false;
	}

	// Method helps to update account in Hashmap
	@Override
	public boolean updateAccount(Account account) {
		return accounts.put(account.getId(), account) != null ? true : false;
	}

	// Method helps to delete account in Hashmap
	@Override
	public boolean deleteAccount(int accountId) {
		return accounts.remove(accountId) != null ? true : false;

	}

	// Method helps to get account details from Hashmap
	@Override
	public Account getAccount(int accountId) {
		Account account = accounts.get(accountId);
		return account;
	}

	// Method helps to list all accounts from Hashmap
	@Override
	public List<Account> getAll() {
		ArrayList<Account> accountlist = new ArrayList<Account>(accounts.values());
		return accountlist;
	}

}
