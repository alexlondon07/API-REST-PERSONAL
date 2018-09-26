package github.io.alexlondon07.api.dao.client;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import github.io.alexlondon07.api.dao.AbstractSession;
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
	public void deleteClient(Long id) {
		
		if(id!=null && id > 0){
			Client client = findById(id);
			getSession().delete(client);
		}
	}

	@Override
	public Client findById(Long id) {
		return getSession().get(Client.class, id);
	}

	@Override
	public Client findByCellphone(String number) {
		return (Client) getSession().createQuery(
			"from Client where cellphone = :cellphone")
		.setParameter("cellphone", number)
		.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findAllClients() {
		return getSession().createQuery("from Client").list();
	}

	@Override
	public boolean isClientExist(Client client) {
		Client clientResponse = findByCellphone(client.getCellphone());
		boolean vBalid = false;
	
		if(clientResponse !=null){	
			if(client.getIdClient() != clientResponse.getIdClient()){
				vBalid =  true;	
			}
		}
		return vBalid;
	}

}
