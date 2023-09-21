
public class AccountServiceImpl implements AccountService {
	private Account[] accounts = new Account[10];

	@Override
	public boolean create(Account acc) {

		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i] == null) {
				acc.setId(i+1);
				accounts[i] = acc;
				break;
			}
		}
		return true;
	}

	@Override
	public boolean update(Account account) {
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getId() == account.getId()) {
				accounts[i] = account;
				break;
			}
		}
		return true;
	}

	@Override
	public boolean delete(int accountId) {
		int index = 0;
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getId() == accountId) {
				index = i;
				break;
			}
		}
		for(int i = index; i < accounts.length -1; i++){
			accounts[i] = accounts[i + 1];
	      }
		return true;
	}

	@Override
	public Account get(int accountId) {
		Account account = null;
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getId() == accountId) {
				account = accounts[i];
				break;
			}
		}

		return account;
	}

	@Override
	public void getAll() {
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i] != null) {
				System.out.println(accounts[i].toString());
			}
		}
	}

}
