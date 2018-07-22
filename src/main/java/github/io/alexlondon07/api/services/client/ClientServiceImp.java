package github.io.alexlondon07.api.services.client;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.alexlondon07.api.dao.client.ClientDao;
import github.io.alexlondon07.api.models.Client;

@Service("clientService")
@Transactional
public class ClientServiceImp implements ClientService {

	@Autowired
	private ClientDao clienteDao;
	
	@Override
	public void saveClient(Client client) {
		clienteDao.saveClient(client);
	}

	@Override
	public void updateClient(Client client) {
		clienteDao.updateClient(client);
	}

	@Override
	public void deleteClient(long idClient) {
		clienteDao.deleteClient(idClient);
	}

	@Override
	public Client findById(long idClient) {
		return clienteDao.findById(idClient);
	}

	@Override
	public Client findByCellphone(String number) {
		return clienteDao.findByCellphone(number);
	}

	@Override
	public List<Client> findAllClients() {
		return clienteDao.findAllClients();
	}

	@Override
	public boolean isClientExist(Client client) {
		return clienteDao.isClientExist(client);
	}
}
