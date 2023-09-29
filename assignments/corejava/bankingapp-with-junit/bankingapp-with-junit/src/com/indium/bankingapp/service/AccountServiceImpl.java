package com.indium.bankingapp.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import com.indium.bankingapp.dao.AccountDao;
import com.indium.bankingapp.dao.AccountDaoJdbcImpl;
import com.indium.bankingapp.model.Account;

public class AccountServiceImpl {

	AccountDao accountDao;

	public AccountServiceImpl() {
		accountDao = new AccountDaoJdbcImpl();
	}

	public boolean createAccount(Account account) {
		return accountDao.create(account);
	}

	public boolean updateAccount(Account account) {
		return accountDao.update(account);
	}

	public boolean deleteAccount(int accountId) {
		return accountDao.delete(accountId);

	}

	public Account getAccount(int accountId) {
		return accountDao.get(accountId);
	}

	public List<Account> getAll() {
		return accountDao.getAll();
	}

	// Method helps to return the accounts count which is having balance more than 1
	// Lakh
	public long getAccountsCountBalanceMorethanOneLakh(int i) {
		long count = accountDao.getAll().stream().filter(s -> s.getBalance() > 100000).count();
		return count;
	}

	// Method helps to return the accounts count based on accounts type
	public Map<String, Long> getAccountsCountByAccountType() {
		return accountDao.getAll().stream().map(Account::getType)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	// Method helps to return the accounts count based on accounts type sorted
	public Map<String, Long> getAccountsCountByAccountTypeOrdered() {
		return accountDao.getAll().stream().sorted(Comparator.comparing(Account::getType))
				.collect(Collectors.groupingBy(Account::getType, LinkedHashMap::new, Collectors.counting()));
	}

	// Method helps to return the list of account ids based on given name
	public Collection getAccountDetailsByGivenName() {
		System.out.print("Enter the name: ");
		Scanner in = new Scanner(System.in);
		int count = 0;
		String givenName = in.next();
		return accountDao.getAll().stream().filter(acc -> acc.getName().contains(givenName)).map(emp -> emp.getName())
				.collect(Collectors.toList());
	}

	// Method helps to return the average balance from accounts based on account
	// type
	public Map<String, Double> getAverageBalanceByAccountType() {
		return accountDao.getAll().stream().sorted(Comparator.comparing(Account::getType)).collect(Collectors
				.groupingBy(Account::getType, LinkedHashMap::new, Collectors.averagingDouble(Account::getBalance)));
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
	public void bulkExport() {
		try (FileWriter out = new FileWriter("./output/account-output.txt")) {
			accountDao.getAll().stream().map(acc -> acc.getId() + "," + acc.getName() + "," + acc.getType() + ","
					+ acc.getBalance() + "," + acc.isActive() + "\n").forEach(rec -> {
						try {
							out.write(rec);
						} catch (IOException e) {
							System.out
									.println("Error occured while writing employee data into file. " + e.getMessage());
							e.printStackTrace();
						}
					});
			System.out.format("%d Accounts are exported successfully.", accountDao.getAll().size());
		} catch (IOException e) {
			System.out.println("Error occured while exporting account data. " + e.getMessage());
		}
	}

	// Method helps to import accounts from file
	public void bulkImport() {
		int counter = 0;
		try (Scanner in = new Scanner(new FileReader("./input/account-input.txt"))) {
			while (in.hasNextLine()) {
				String emp = in.nextLine();
				Account account = new Account();
				StringTokenizer tokenizer = new StringTokenizer(emp, ",");
				account.setId(Integer.parseInt(tokenizer.nextToken()));
				account.setName(tokenizer.nextToken());
				account.setType(tokenizer.nextToken());
				account.setBalance(Double.parseDouble(tokenizer.nextToken()));
				account.setActive(Boolean.parseBoolean(tokenizer.nextToken()));
				accountDao.create(account);
				counter++;
			}
			System.out.format("%d Accounts are imported successfully.", counter);
		} catch (IOException e) {
			System.out.println("Error occured while importing account data. " + e.getMessage());
		}
	}
}