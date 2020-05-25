package github.io.alexlondon07.api.services;

import java.util.List;

import github.io.alexlondon07.api.models.User;

public interface UserService {

	void saveUser(User user);

	void updateUser(User user);

	void deleteUser(long id);

	User findByUserNameAndPwd(String userName, String pwd);

	User findByUserName(String username);

	User findById(long id);

	List<User> findAllUser();

	boolean isUserExist(User user);
}
