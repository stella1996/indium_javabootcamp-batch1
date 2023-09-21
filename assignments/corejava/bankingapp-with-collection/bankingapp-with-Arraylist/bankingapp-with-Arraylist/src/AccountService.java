import java.util.ArrayList;

public interface AccountService {

	public boolean create(Account acc);

	public boolean update(Account acc);

	public boolean delete(int accountId);

	public Account get(int accountId);

	public ArrayList < Account > getAll();

}
