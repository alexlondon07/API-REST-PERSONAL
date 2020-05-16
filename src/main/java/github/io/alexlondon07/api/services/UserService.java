package github.io.alexlondon07.api.services;

import java.util.List;

import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.models.Users;

public interface UserService {
	
	void saveUser(Users user);

	void updateUser(Users user);

	void deleteUser(String userName);

	Client findByUserNameAndPwd(String userName, String pwd);

	
	List<Users> findAllUser();

	boolean isClientExist(Users user);

}
