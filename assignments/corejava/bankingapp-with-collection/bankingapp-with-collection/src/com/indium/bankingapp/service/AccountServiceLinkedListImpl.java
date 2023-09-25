package com.indium.bankingapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import com.indium.bankingapp.model.Account;

public class AccountServiceLinkedListImpl implements AccountService {
	private LinkedList<Account> accounts = new LinkedList<>();

	@Override
	public boolean createAccount(Account account) {
		account.setId(accounts.size() + 1);
		return accounts.add(account);
	}

	@Override
	public boolean updateAccount(Account account) {
		Account updateaccount = getAccount(account.getId());
		int index = accounts.indexOf(updateaccount);
		System.out.println(index);
		accounts.set(index, account);
		return true;
	}

	@Override
	public boolean deleteAccount(int accountId) {
		Account account = getAccount(accountId);
		return accounts.remove(account);
	}

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

	@Override
	public Collection getAll() {
		return accounts;
	}

}
