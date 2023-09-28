package com.indium.bankingapp.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.indium.bankingapp.exception.AccountException;
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
	public Account getAccount(int accountId) throws AccountException {
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
	public long getAccountsCountBalanceMorethanOneLakh(int i) {
		long count = 0;
		count = accounts.values().stream().filter(s -> s.getBalance() > 100000).count();
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
		List<Integer> accountIds = new ArrayList();
		List<Account> accountList = accounts.values().stream().filter(s -> s.getName().contains(givenName))
				.collect(Collectors.toList());
		for (Account account : accountList) {
			accountIds.add(account.getId());
		}
		return accountIds;
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

	// Method helps to export accounts to file
	@Override
	public void bulkExport() {
		System.out.format("%n%s - Export started %n", Thread.currentThread().getName());
		try (FileWriter out = new FileWriter("./output/account-output.txt")) {

			for (Account account : accounts.values()) {
				StringBuilder accrecord = new StringBuilder();
				accrecord.append(account.getId()).append(",").append(account.getName()).append(",")
						.append(account.getType()).append(",").append(account.getBalance()).append(",")
						.append(account.isActive()).append("\n");
				out.write(accrecord.toString());
			}
			System.out.format("%s - %d Accounts are exported successfully.", Thread.currentThread().getName(),
					accounts.values().size());
		} catch (IOException e) {
			System.out.println("Error occured while exporting account data. " + e.getMessage());
		}
	}

	// Method helps to import accounts from file
	@Override
	public void bulkImport() {
		System.out.format("%n%s - Import started %n", Thread.currentThread().getName());
		int counter = 0;
		try (Scanner in = new Scanner(new FileReader("./input/account-input.txt"))) {
			System.out.println("Implorting file...");
			while (in.hasNextLine()) {
				String acc = in.nextLine();
				Account account = new Account();
				StringTokenizer tokenizer = new StringTokenizer(acc, ",");
				account.setId(Integer.parseInt(tokenizer.nextToken()));
				account.setName(tokenizer.nextToken().toString());
				account.setType(tokenizer.nextToken());
				account.setBalance(Double.parseDouble(tokenizer.nextToken()));
				account.setActive(Boolean.parseBoolean(tokenizer.nextToken()));
				this.createAccount(account);
				counter++;
			}
			System.out.format("%s - %d Accounts are imported successfully.", Thread.currentThread().getName(), counter);
		} catch (Exception e) {
			System.out.println("Error occured while importing account data. " + e.getMessage());
		}
	}
}