import java.util.ArrayList;

public class AccountServiceImpl implements AccountService {
	private ArrayList<Account> accounts = new ArrayList<>();

	@Override
	public boolean create(Account account) {
        account.setId(accounts.size()+1);
		return accounts.add(account);
	}

	@Override
	public boolean update(Account account) {
		Account updateaccount = get(account.getId());
		int index = accounts.indexOf(updateaccount);
		System.out.println(index);
		accounts.set(index, account);
		return true;
	}

	@Override
	public boolean delete(int accountId) {
		Account account = get(accountId);
		return accounts.remove(account);

	}

	@Override
	public Account get(int accountId) {
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
	public ArrayList<Account> getAll() {
		for(Account account : accounts) {
		      System.out.println(account);
		    }
		return accounts;
	}

}
