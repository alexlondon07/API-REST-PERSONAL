package github.io.alexlondon07.api.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.models.Users;

@Service("UserService")
@Transactional

public class UserServiceImpl implements UserService {

	@Override
	public void saveUser(Users user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(Users user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String userName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Client findByUserNameAndPwd(String userName, String pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> findAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClientExist(Users user) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
