package github.io.alexlondon07.api.dao;

import java.util.List;

import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.models.Users;

public class UserDaoImpl extends AbstractSession implements UserDao {

	@Override
	public void saveUser(Users user) {
		getSession().persist(user);
	}

	@Override
	public void updateUser(Users user) {
		getSession().update(user);

	}

	@Override
	public void deleteUser(String userName) {
		if (userName != null && !userName.isEmpty()) {
			Users user = findByUserName(userName);
			getSession().delete(user);
		}

	}

	@Override
	public Users findByUserNameAndPwd(String userName, String pwd) {
		return (Users) getSession().createQuery("from USERS where user = :user and pwd = :pwd ")
				.setParameter("user", userName).setParameter("pwd", pwd).uniqueResult();

	}

	@Override
	public List<Users> findAllUser() {
		return getSession().createQuery("from USERS").list();
	}

	@Override
	public boolean isClientExist(Users user) {
		Users userResponse = findByUserName(user.getUser());

		boolean vBalid = false;
		if (userResponse != null) {
			vBalid = user.getUser() != userResponse.getUser() ? true : false;
		}
		return vBalid;
	}

	@Override
	public Users findByUserName(String username) {
		return (Users) getSession().createQuery("from USERS where user = :user").setParameter("user", username)
				.uniqueResult();
	}
}
