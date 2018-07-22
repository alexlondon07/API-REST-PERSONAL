package github.io.alexlondon07.api.dao.client;

import java.util.List;

import github.io.alexlondon07.api.models.Client;

public interface ClientDao {
	
	void saveClient( Client client);
	
	void updateClient ( Client client );
	
	void deleteClient ( Long idClient);
	
	Client findById( Long idClient);
	
	Client findByCellphone ( String cellPhone);
	
	List<Client> findAllClients();
	
	boolean isClientExist(Client client);
}
