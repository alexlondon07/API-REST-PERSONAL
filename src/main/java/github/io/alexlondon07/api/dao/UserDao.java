package github.io.alexlondon07.api.dao;

import java.util.List;

import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.models.Users;

public interface UserDao {
	void saveUser(Users user);

	void updateUser(Users user);

	void deleteUser(String userName);

	Users findByUserNameAndPwd(String userName, String pwd);
	
	Users findByUserName(String username);

	
	List<Users> findAllUser();

	boolean isClientExist(Users user);


}
