package com.indium.bankingapp.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.indium.bankingapp.exception.AccountException;
import com.indium.bankingapp.model.Account;

public interface AccountService {

	public boolean createAccount(Account acc);

	public boolean updateAccount(Account acc);

	public boolean deleteAccount(int accountId) throws AccountException;

	public Account getAccount(int accountId) throws AccountException;

	public Collection getAll();

	public long getAccountsCountBalanceMorethanOneLakh(int i);

	public Map<String, Long> getAccountsCountByAccountType();

	public Map<String, Long> getAccountsCountByAccountTypeOrdered();

	public Map<String, Double> getAverageBalanceByAccountType();

	public Collection getAccountDetailsByGivenName();

	public void bulkExport();

	public void bulkImport();

}
