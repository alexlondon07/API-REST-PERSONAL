
package github.io.alexlondon07.api.services.client;

import java.util.List;

import github.io.alexlondon07.api.models.Client;

public interface ClientService {
	
	void saveClient( Client client);
	
	void updateClient ( Client client );
	
	void deleteClient ( long idClient);
	
	Client findById( long idClient);
	
	Client findByCellphone ( String number);
		
	List<Client> findAllClients();
	
	boolean isClientExist(Client client);
}
