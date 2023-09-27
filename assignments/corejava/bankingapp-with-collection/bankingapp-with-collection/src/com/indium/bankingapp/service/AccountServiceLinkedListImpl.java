package com.indium.bankingapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import com.indium.bankingapp.model.Account;

public class AccountServiceLinkedListImpl implements AccountService {
	private LinkedList<Account> accounts = new LinkedList<>();

	// Method helps to create new account in LinkedList
	@Override
	public boolean createAccount(Account account) {
		account.setId(accounts.size() + 1);
		return accounts.add(account);
	}

	// Method helps to update account in LinkedList
	@Override
	public boolean updateAccount(Account account) {
		Account updateaccount = getAccount(account.getId());
		int index = accounts.indexOf(updateaccount);
		System.out.println(index);
		accounts.set(index, account);
		return true;
	}

	// Method helps to delete account in LinkedList
	@Override
	public boolean deleteAccount(int accountId) {
		Account account = getAccount(accountId);
		return accounts.remove(account);
	}

	// Method helps to view account in LinkedList
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

	// Method helps to print accounts
	@Override
	public Collection getAll() {
		return accounts;
	}

}
