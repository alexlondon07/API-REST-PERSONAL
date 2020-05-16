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
			//getSession().delete(client);
		}

	}

	@Override
	public Client findByUserNameAndPwd(String userName, String pwd) {
		return (Client) getSession().createQuery("from USERS where user = :user and pwd = :pwd ")
				.setParameter("cellphone", userName).
				setParameter("pwd", pwd).uniqueResult();
		
	}
	
	

	@Override
	public List<Users> findAllUser() {
		return getSession().createQuery("from USERS").list();
	}

	@Override
	public boolean isClientExist(Users user) {
		// TODO Auto-generated method stub
		return false;
	}

}
