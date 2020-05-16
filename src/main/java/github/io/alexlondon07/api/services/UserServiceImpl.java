package github.io.alexlondon07.api.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.alexlondon07.api.dao.UserDao;
import github.io.alexlondon07.api.models.Users;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public void saveUser(Users user) {
		userDao.saveUser(user);

	}

	@Override
	public void updateUser(Users user) {
		userDao.updateUser(user);

	}

	@Override
	public void deleteUser(String deleteUser) {
		userDao.deleteUser(deleteUser);

	}

	@Override
	public List<Users> findAllUser() {
		return userDao.findAllUser();
	}

	@Override
	public boolean isClientExist(Users user) {
		// TODO Auto-generated method stub
		return userDao.isClientExist(user);
	}

	@Override
	public Users findByUserNameAndPwd(String userName, String pwd) {
		// TODO Auto-generated method stub
		return userDao.findByUserNameAndPwd(userName, pwd);
	}

	@Override
	public Users findByUserName(String username) {
		// TODO Auto-generated method stub
		return userDao.findByUserName(username);
	}

}
