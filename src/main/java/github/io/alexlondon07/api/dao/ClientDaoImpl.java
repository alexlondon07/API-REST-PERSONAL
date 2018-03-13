package github.io.alexlondon07.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import github.io.alexlondon07.api.models.Client;

@Repository
@Transactional
public class ClientDaoImpl extends AbstractSession implements ClientDao {

	public ClientDaoImpl() {
	}

	@Override
	public void saveClient(Client client) {
		getSession().persist(client);
	}

	@Override
	public void updateClient(Client client) {
		getSession().update(client);
	}

	@Override
	public void deleteClient(Long idClient) {
		
		if(idClient!=null){
			Client client = findById(idClient);
			getSession().delete(client);
		}
	}

	@Override
	public Client findById(Long idClient) {
		return getSession().get(Client.class, idClient);
	}

	@Override
	public Client findByCellphone(String number) {
		return (Client) getSession().createQuery(
			"from Client where cellphone = :cellphone")
		.setParameter("cellphone", number)
		.uniqueResult();
	}

	@Override
	public List<Client> findAllClients() {
		return getSession().createQuery("from Client").list();
	}

}
