package github.io.alexlondon07.api.dao;

import java.util.List;

import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.models.Users;

public class UserDaoImpl implements UserDao {

	@Override
	public void saveUser(Users user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUser(Users user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String userName) {
		// TODO Auto-generated method stub

	}

	@Override
	public Client findByUserNameAndPwd(String userName, String pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> findAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClientExist(Users user) {
		// TODO Auto-generated method stub
		return false;
	}

}
