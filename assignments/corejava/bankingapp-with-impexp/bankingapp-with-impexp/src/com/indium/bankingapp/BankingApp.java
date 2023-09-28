package com.indium.bankingapp;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import com.indium.bankingapp.exception.AccountException;
import com.indium.bankingapp.model.Account;
import com.indium.bankingapp.service.AccountService;
import com.indium.bankingapp.service.AccountServiceHashMapImpl;

public class BankingApp {
	private static Scanner in;
	private static AccountService accountService;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		accountService = new AccountServiceHashMapImpl();
		System.out.print("Welcome to Banking Application!");
		while (true) {
			System.out.println("\n");
			System.out.println("1. Add Accounts");
			System.out.println("2. View All Accounts");
			System.out.println("3. View Account");
			System.out.println("4. Update Account");
			System.out.println("5. Delete Account");
			System.out.println("6. Print Statistics");
			System.out.println("7. Import");
			System.out.println("8. Export");
			System.out.println("9. Exit");
			System.out.print("Enter the option: ");

			// Get option from user
			int option = 0;
			try {
				option = Integer.parseInt(in.next());
			} catch (NumberFormatException e) {
				System.out.println("Invalid option. Please enter valid option.");
				continue;
			}
			try {
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
					Account acc = null;
					try {
						acc = viewAccount(id);
					} catch (InputMismatchException e) {
						System.out.print(e.getMessage());
					}
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
					printStatistics();
					break;
				case 7:
					accountService.bulkImport();
					break;
				case 8:
					accountService.bulkExport();
					break;
				case 9:
					System.out.println("Thank you!!!");
					break;

				default:
					System.out.println("Invalid operator!");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.print("Your selection can only be an integer!");
			}
		}

	}

	// Method helps to print statistics about accounts
	private static void printStatistics() {
		System.out.println("a] No of accounts which has balance more than 1 lac");
		System.out.println("b] Show no of account by account type");
		System.out.println("c] Show no of accounts by account type with sorting");
		System.out.println("d] Show avg balance by account type");
		System.out.println("e] List account ids whose account name contains given name");
		System.out.print("Enter an option: ");
		String option;
		option = in.next();
		switch (option) {
		case "a":
			System.out.println("a] No of accounts which has balance more than 1 lac: "
					+ accountService.getAccountsCountBalanceMorethanOneLakh(100000));
			break;
		case "b":
			System.out.println(
					"b] Show no of account by account type: " + accountService.getAccountsCountByAccountType());
			break;
		case "c":
			System.out.println("c] Show no of accounts by account type with sorting: "
					+ accountService.getAccountsCountByAccountTypeOrdered());
			break;
		case "d":
			System.out
					.println("d] Show avg balance by account type: " + accountService.getAverageBalanceByAccountType());
			break;
		case "e":
			System.out.println("e] List account ids whose account name contains given name: "
					+ accountService.getAccountDetailsByGivenName());
			break;
		}
	}

	// Method helps to delete account
	private static void deleteAccount() {
		try {
			System.out.print("Enter the Account Id to be deleted: ");
			int id = in.nextInt();
			accountService.deleteAccount(id);
		} catch (NumberFormatException e) {
			System.out.print("Your selection can only be an integer!");
		} catch (AccountException e) {
			System.out.println(e.getMessage());
		}
	}

	// Method helps to update account details
	private static void updateAccount() {
		System.out.print("Enter the Account Id to be updated: ");
		int id = in.nextInt();
		Account account = null;
		try {
			account = accountService.getAccount(id);
			captureAccountDetail(account);
			accountService.updateAccount(account);
		} catch (NumberFormatException e) {
			System.out.print("Your selection can only be an integer!");
		} catch (AccountException e) {
			System.out.println(e.getMessage());
		}
	}

	// Method helps to view account details
	private static Account viewAccount(int accountid) {
		Account account = null;
		try {
			account = accountService.getAccount(accountid);
			return account;
		} catch (AccountException e) {
			System.out.println(e.getMessage());
		}
		return account;
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
