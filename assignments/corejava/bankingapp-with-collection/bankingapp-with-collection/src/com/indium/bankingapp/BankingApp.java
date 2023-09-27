package com.indium.bankingapp;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.indium.bankingapp.model.Account;
import com.indium.bankingapp.service.AccountService;
import com.indium.bankingapp.service.AccountServiceArrListImpl;
import com.indium.bankingapp.service.AccountServiceHasSetImpl;
import com.indium.bankingapp.service.AccountServiceHashMapImpl;
import com.indium.bankingapp.service.AccountServiceLinkedListImpl;
import com.indium.bankingapp.service.AccountServiceTreeMapImpl;
import com.indium.bankingapp.service.AccountServiceTreeSetImpl;

public class BankingApp {
	private static Scanner in;
	private static AccountService accountService;

	public static void main(String[] args) {
		in = new Scanner(System.in);
//		accountService = new AccountServiceArrListImpl();
//		accountService = new AccountServiceHashMapImpl();
//		accountService = new AccountServiceHasSetImpl();
//		accountService = new AccountServiceLinkedListImpl();
		accountService = new AccountServiceTreeMapImpl();
//		accountService = new AccountServiceTreeSetImpl();
		System.out.print("Welcome to Banking Application!");
		while (true) {
			System.out.println("\n");
			System.out.println("1. Add Accounts");
			System.out.println("2. View All Accounts");
			System.out.println("3. View Account");
			System.out.println("4. Update Account");
			System.out.println("5. Delete Account");
			System.out.println("6. Exit");
			System.out.print("Enter the option: ");

			int option = 0;
			// Get option from user
			option = Integer.parseInt(in.next());
			switch (option) {

			case 1:
				addAccount();
				System.out.println("Account has been added successfully!");
				break;
			case 2:
				viewAllAccounts();
				break;
			case 3:
				System.out.print("Enter the Account Id: ");
				int id = in.nextInt();
				Account acc = viewAccount(id);
				printHeader();
				printDetail(acc);
				break;
			case 4:
				updateAccount();
				System.out.println("Account has been updated successfully!");
				break;
			case 5:
				deleteAccount();
				System.out.println("Account has been deleted successfully!");
				break;
			case 6:
				System.out.println("Thank you!!!");
				break;

			default:
				System.out.println("Invalid operator!");
				break;
			}
		}

	}

	// Method helps to delete account
	private static void deleteAccount() {
		System.out.print("Enter the Account Id to be deleted: ");
		int id = in.nextInt();
		accountService.deleteAccount(id);
	}

	// Method helps to update account details
	private static void updateAccount() {
		System.out.print("Enter the Account Id to be updated: ");
		int id = in.nextInt();
		Account acc = accountService.getAccount(id);
		captureAccountDetail(acc);
		accountService.updateAccount(acc);
	}

	// Method helps to view account details
	private static Account viewAccount(int accountid) {

		return accountService.getAccount(accountid);
	}

	// Method helps to view all accounts
	private static void viewAllAccounts() {

		Collection accounts = accountService.getAll();
		printHeader();
		Iterator<Account> iterator = accounts.iterator();
		// while loop
		while (iterator.hasNext()) {
			printDetail(iterator.next());
		}
	}

	// Method helps to create account
	private static void addAccount() {
		Account account = new Account();

		captureAccountDetail(account);

		accountService.createAccount(account);

	}

	// Method helps to capture new account information
	private static void captureAccountDetail(Account account) {
		System.out.print("Enter Account Name: ");
		account.setName(in.next());
		System.out.print("Enter Account Type: ");
		account.setType(in.next());
		System.out.print("Enter Account Balance: ");
		account.setBalance(in.nextDouble());
		System.out.print("Is Account Active(Enter true or false): ");
		account.setActive(in.nextBoolean());

	}

	// Method helps to print the headers(Print the properities of accounts in table
	// format)
	private static void printHeader() {
		System.out.format("\n%5s %15s %5s %15s %15s", "Id", "Name", "Type", "Balance", "Is Active");
	}

	// Method helps to print the account information
	private static void printDetail(Account acc) {
		if (acc == null) {
			return;
		}
		System.out.format("\n%5s %15s %5s %12s %12s", acc.getId(), acc.getName(), acc.getType(), acc.getBalance(),
				acc.isActive());
	}
}
