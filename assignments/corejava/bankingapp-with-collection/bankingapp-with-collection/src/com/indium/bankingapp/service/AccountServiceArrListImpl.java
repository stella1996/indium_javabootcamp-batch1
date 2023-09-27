package com.indium.bankingapp.service;

import java.util.ArrayList;
import java.util.Collection;

import com.indium.bankingapp.model.Account;

public class AccountServiceArrListImpl implements AccountService {
	private ArrayList<Account> accounts = new ArrayList<>();

	// Method helps to add new account in Arraylist
	@Override
	public boolean createAccount(Account account) {
		account.setId(accounts.size() + 1);
		return accounts.add(account);
	}

	// Method helps to update new account in Arraylist
	@Override
	public boolean updateAccount(Account account) {
		Account updateaccount = getAccount(account.getId());
		int index = accounts.indexOf(updateaccount);
		System.out.println(index);
		accounts.set(index, account);
		return true;
	}

	// Method helps to delete new account in Arraylist
	@Override
	public boolean deleteAccount(int accountId) {
		Account account = getAccount(accountId);
		return accounts.remove(account);
	}

	// Method helps to get account details
	@Override
	public Account getAccount(int accountId) {
		Account account = null;
		for (Account keyaccount : accounts) {
			if (keyaccount.getId() == accountId) {
				account = keyaccount;
				break;
			}
		}
		return account;
	}

	// Method helps to print all accounts
	@Override
	public Collection getAll() {
		return accounts;
	}

}
