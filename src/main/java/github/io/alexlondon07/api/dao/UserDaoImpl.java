package github.io.alexlondon07.api.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import github.io.alexlondon07.api.models.User;

@Repository
@Transactional
public class UserDaoImpl extends AbstractSession implements UserDao {

	public static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Override
	public void saveUser(User user) {
		writeFile(user, "Create User ");
		getSession().persist(user);
	}

	@Override
	public void updateUser(User user) {
		writeFile(user, "Update User ");
		getSession().update(user);
	}

	@Override
	public void deleteUser(Long id) {
		if (id > 0) {
			User user = findById(id);
			writeFile(user, "Delete User ");
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

	public void writeFile(User user, String action) {

		try {
			String content = action + " Name: " + user.getName() + " Last Name: " + user.getLastName() + " Role: "
					+ user.getRole() + " User " + user.getUser() + "\n";
			File file = new File("src/main/resources/static/crud.txt");

			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch (IOException e) {
			logger.error("Error IOException" + e.getMessage());
		}

	}
}
