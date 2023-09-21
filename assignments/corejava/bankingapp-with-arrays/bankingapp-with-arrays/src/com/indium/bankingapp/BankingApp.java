package com.indium.bankingapp;

import java.util.Scanner;
import com.indium.bankingapp.model.Account;
import com.indium.bankingapp.service.AccountService;
import com.indium.bankingapp.service.AccountServiceImpl;

public class BankingApp {
	private static Scanner in;
	private static AccountService accountService;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		accountService = new AccountServiceImpl();

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

	private static void deleteAccount() {
		System.out.print("Enter the Account Id to be deleted: ");
		int id = in.nextInt();
		accountService.delete(id);
	}

	private static void updateAccount() {
		System.out.print("Enter the Account Id to be updated: ");
		int id = in.nextInt();
		Account acc = accountService.get(id);
		captureAccountDetail(acc);
		accountService.update(acc);
	}

	private static Account viewAccount(int accountid) {

		return accountService.get(accountid);
	}

	private static void viewAllAccounts() {
		accountService.getAll();
	}

	private static void addAccount() {
		Account account = new Account();

		captureAccountDetail(account);

		accountService.create(account);

	}

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

	private static void printHeader() {
		System.out.format("\n%5s %15s %5s %15s %15s", "Id", "Name", "Type", "Balance", "Is Active");
	}

	private static void printDetail(Account acc) {
		if (acc == null) {
			return;
		}

		System.out.format("\n%5s %15s %5s %12s %12s", acc.getId(), acc.getName(), acc.getType(), acc.getBalance(),
				acc.isActive());

	}
}
