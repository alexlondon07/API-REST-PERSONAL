package github.io.alexlondon07.api.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.alexlondon07.api.dao.UserDao;
import github.io.alexlondon07.api.models.User;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public List<User> findAllUser() {
		return userDao.findAllUser();
	}

	@Override
	public boolean isUserExist(User user) {
		return userDao.isUserExist(user);
	}

	@Override
	public User findByUserNameAndPwd(String userName, String pwd) {
		return userDao.findByUserNameAndPwd(userName, pwd);
	}

	@Override
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	public User findById(long id) {
		return userDao.findById(id);
	}

	@Override
	public void deleteUser(long id) {
		userDao.deleteUser(id);
	}

}
