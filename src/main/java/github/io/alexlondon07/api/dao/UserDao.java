package github.io.alexlondon07.api.dao;

import java.util.List;

import github.io.alexlondon07.api.models.User;

public interface UserDao {

	void saveUser(User user);

	void updateUser(User user);

	void deleteUser(Long id);

	User findByUserNameAndPwd(String userName, String pwd);

	User findByUserName(String username);

	User findById(long id);

	List<User> findAllUser();

	boolean isUserExist(User user);
}
