package com.indium.bankingapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
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

	// Method helps to return the accounts count which is having balance more than 1
	// Lakh
	@Override
	public int getAccountsCountBalanceMorethanOneLakh(int i) {
		int count = 0;
		for (Account acc : accounts.values()) {
			if (acc.getBalance() > i) {
				count++;
			}
		}
		return count;
	}

	// Method helps to return the accounts count based on accounts type
	@Override
	public Map<String, Long> getAccountsCountByAccountType() {
		Map<String, Long> accountCountByAccountType = new HashMap<>();
		for (Account acc : accounts.values()) {
			if (accountCountByAccountType.containsKey(acc.getType())) {
				long count = accountCountByAccountType.get(acc.getType());
				accountCountByAccountType.put(acc.getType(), ++count);
			} else {
				accountCountByAccountType.put(acc.getType(), 1L);
			}
		}
		return accountCountByAccountType;
	}

	// Method helps to return the accounts count based on accounts type sorted
	@Override
	public Map<String, Long> getAccountsCountByAccountTypeOrdered() {
		Map<String, Long> accountCountByAccountType = new TreeMap<>();
		for (Account acc : accounts.values()) {
			if (accountCountByAccountType.containsKey(acc.getType())) {
				long count = accountCountByAccountType.get(acc.getType());
				accountCountByAccountType.put(acc.getType(), ++count);
			} else {
				accountCountByAccountType.put(acc.getType(), 1L);
			}
		}
		return accountCountByAccountType;
	}

	// Method helps to return the list of account ids based on given name
	@Override
	public Collection getAccountDetailsByGivenName() {
		System.out.print("Enter the name: ");
		Scanner in = new Scanner(System.in);
		int count = 0;
		String givenName = in.next();
		List<Integer> accountList = new ArrayList<>();
		Set<Entry<Integer, Account>> set = accounts.entrySet();
		accounts.entrySet().forEach(entry -> {
			if (entry.getValue().getName().contains(givenName)) {
				accountList.add(entry.getValue().getId());
			}
		});
		return accountList;
	}

	// Method helps to return the average balance from accounts based on account
	// type
	@Override
	public Map<String, Double> getAverageBalanceByAccountType() {
		Map<String, List<Double>> averageBalanceByAccountType = new HashMap<>();
		for (Account acc : accounts.values()) {
			if (averageBalanceByAccountType.containsKey(acc.getType())) {
				averageBalanceByAccountType.get(acc.getType()).add(acc.getBalance());
			} else {
				List<Double> balances = new ArrayList<>();
				balances.add(acc.getBalance());
				averageBalanceByAccountType.put(acc.getType(), balances);
			}
		}
		Map<String, Double> averagebalances = new HashMap<>();
		for (Map.Entry<String, List<Double>> entry : averageBalanceByAccountType.entrySet()) {
			Double average = calcAverage(entry.getValue());
			averagebalances.put(entry.getKey(), average);
		}
		return averagebalances;
	}

	// Method helps to calculate the average balance from the given balances
	private double calcAverage(List<Double> balances) {
		double result = 0;
		for (Double value : balances) {
			result += value;
		}
		return result / balances.size();
	}

}
