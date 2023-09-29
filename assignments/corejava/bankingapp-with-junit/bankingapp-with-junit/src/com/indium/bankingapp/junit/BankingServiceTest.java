package com.indium.bankingapp.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.indium.bankingapp.model.Account;
import com.indium.bankingapp.service.AccountServiceImpl;

public class BankingServiceTest {
	AccountServiceImpl accountService = new AccountServiceImpl();

	@Test
	public void addAccount() {

		Account account = new Account();
		account.setId(10);
		account.setName("Joe");
		account.setType("Savings");
		account.setBalance(20000.0);
		account.setActive(true);
		Boolean created = accountService.createAccount(account);
		assertEquals(true, created);
	}

	@Test
	public void updateAccount() {

		Account account = new Account();
		account.setId(10);
		account.setName("Joe");
		account.setType("Savings");
		account.setBalance(20000.0);
		account.setActive(true);
		Boolean created = accountService.updateAccount(account);
		assertEquals(true, created);
	}
}
