package github.io.alexlondon07.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import github.io.alexlondon07.api.models.User;

@Repository
@Transactional
public class UserDaoImpl extends AbstractSession implements UserDao {

	@Override
	public void saveUser(User user) {
		getSession().persist(user);
	}

	@Override
	public void updateUser(User user) {
		getSession().update(user);
	}

	@Override
	public void deleteUser(Long id) {
		if (id > 0) {
			User user = findById(id);
			getSession().delete(user);
		}
	}

	@Override
	public User findByUserNameAndPwd(String userName, String pwd) {
		return (User) getSession().createQuery("from User where user = :user and pwd = :pwd ")
				.setParameter("user", userName).setParameter("pwd", pwd).uniqueResult();

	}

	@Override
	public List<User> findAllUser() {
		return getSession().createQuery("from User").list();
	}

	@Override
	public boolean isUserExist(User user) {
		User userResponse = findByUserName(user.getName());

		boolean vBalid = false;
		if (userResponse != null) {
			vBalid = user.getUser() != userResponse.getUser() ? true : false;
		}
		return vBalid;
	}

	@Override
	public User findByUserName(String username) {
		return (User) getSession().createQuery("from User where user = :user").setParameter("user", username)
				.uniqueResult();
	}

	@Override
	public User findById(long id) {
		return getSession().get(User.class, id);
	}
}
